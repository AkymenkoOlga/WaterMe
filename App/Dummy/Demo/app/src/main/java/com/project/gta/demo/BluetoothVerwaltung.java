package com.project.gta.demo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Created by Paul on 29.11.2016.
 */

public class BluetoothVerwaltung implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private BluetoothVerwaltung() {}
    private static BluetoothVerwaltung _instance = null;
    public static BluetoothVerwaltung get_instance()
    {
        if (_instance == null)
            _instance = new BluetoothVerwaltung();
        return _instance;
    }

    public BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
    public final Handler handler = new Handler();

    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice = null;

    final byte delimiter = 33;
    int readBufferPosition = 0;


    public void connect() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("raspberrypi")) //Note, you will need to change this to match the name of your device
                {
                    Log.e("raspberry", device.getName());
                    mmDevice = device;
                    break;
                }
            }
        }

        try {
            mmSocket = (BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(mmDevice, 3);
            Log.e("", "connecting...");
            mmSocket.connect();
            Log.e("", "Connected");
        } catch (Exception e2) {
            Log.e("", "Couldn't establish Bluetooth connection!");
        }

    }
    public void sendBtMsg(String msg2send) {
        try {
            String msg = msg2send;
            //msg += "\n";
            OutputStream mmOutputStream = mmSocket.getOutputStream();
            mmOutputStream.write(msg.getBytes());
        } catch (IOException e3) {e3.printStackTrace();
            Log.e("", "failed to transmit data!");
        }

    }



    final class workerThread implements Runnable {

        private String btMsg;
        public workerThread(){btMsg = null;}
        public workerThread(String msg) {
            btMsg = msg;
        }

        public void run() {
            if (btMsg==null)
                connect();
            else
                sendBtMsg(btMsg);

            while (!Thread.currentThread().isInterrupted()) {
                int bytesAvailable;
                boolean workDone = false;

                try {
                    final InputStream mmInputStream;
                    mmInputStream = mmSocket.getInputStream();
                    bytesAvailable = mmInputStream.available();
                    if (bytesAvailable > 0) {
                        byte[] packetBytes = new byte[bytesAvailable];
                        Log.e("BT recv bt", "bytes available");
                        byte[] readBuffer = new byte[1024];
                        mmInputStream.read(packetBytes);


                        for (int i = 0; i < bytesAvailable; i++) {

                            byte b = packetBytes[i];
                            if (b == delimiter) {
                                byte[] encodedBytes = new byte[readBufferPosition];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                final String data = new String(encodedBytes, "US-ASCII");
                                readBufferPosition = 0;


                                //The variable data now contains our full command
                                handler.post(new Runnable() {
                                    public void run() {
                                    }
                                });

                                workDone = true;
                                break;
                            } else {
                                readBuffer[readBufferPosition++] = b;
                            }
                        }
//                            if (workDone == true) {
//                                mmSocket.close();
//                                break;
//                            }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    };

    @Override
    public  void onClick(View v){
        switch(v.getId()) {
            case R.id.BTNconnect_bt:
                (new Thread(new workerThread())).start();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.SWled:
                if(isChecked) {
                    (new Thread(new workerThread("on"))).start();
                }
                else{
                    (new Thread(new workerThread("off"))).start();
                }
                break;
        }
    }
}
