package com.project.gta.demo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Chris Todt on 21.11.2016.
 */

public class blueooth_client extends Activity {
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice = null;

    final byte delimiter = 33;
    int readBufferPosition = 0;
    final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //   final TextView myLabel = (TextView) findViewById(R.id.btResult);
        //   final Switch ledSW = (Switch) findViewById(R.id.SWled);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //Implement Switches here
        //Add Switch in onCheckedChanged function below

        //LED Switch
        //ledSW.setOnCheckedChangeListener(this);
/*        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }
*/
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
        (new Thread(new workerThread("on"))).start();
    }

    public void sendBtMsg(String msg2send) {

        /*UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee"); //Standard SerialPortService ID

        try {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            Log.i("","socket created");
        } catch (Exception e) {Log.e("", "Error creating socket");}

        try {
            mmSocket.connect();
            Log.d("", "connected");
        }catch (IOException e){
            Log.e("","failed to connect");*/

        try {
            Log.d("", "trying fallback...");
            mmSocket = (BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(mmDevice, 3);
            Log.e("", "connecting...");
            mmSocket.connect();
            Log.e("", "Connected");
        } catch (Exception e2) {
            Log.e("", "Couldn't establish Bluetooth connection!");
        }

        try {
            String msg = msg2send;
            //msg += "\n";
            OutputStream mmOutputStream = mmSocket.getOutputStream();
            mmOutputStream.write(msg.getBytes());
        } catch (IOException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
            Log.e("", "failed to transmit data!");
        }
    }

    public class workerThread implements Runnable {

        private String btMsg;

        public workerThread(String msg) {
            btMsg = msg;
        }

        public void run()
        {

            sendBtMsg(btMsg);
            while(!Thread.currentThread().isInterrupted())
            {
                int bytesAvailable;
                boolean workDone = false;

                try {
                    final InputStream mmInputStream;
                    mmInputStream = mmSocket.getInputStream();
                    bytesAvailable = mmInputStream.available();
                    if(bytesAvailable > 0)
                    {

                        byte[] packetBytes = new byte[bytesAvailable];
                        Log.e("BT recv bt","bytes available");
                        byte[] readBuffer = new byte[1024];
                        mmInputStream.read(packetBytes);

                        for(int i=0;i<bytesAvailable;i++)
                        {
                            byte b = packetBytes[i];
                            if(b == delimiter)
                            {
                                byte[] encodedBytes = new byte[readBufferPosition];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                final String data = new String(encodedBytes, "US-ASCII");
                                readBufferPosition = 0;

                                //The variable data now contains our full command
                                handler.post(new Runnable()
                                {
                                    public void run()
                                    {
                                     //   myLabel.setText(data);
                                    }
                                });

                                workDone = true;
                                break;
                            }
                            else
                            {readBuffer[readBufferPosition++] = b;}
                        }
                        if (workDone == true){
                            mmSocket.close();
                            break;
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }



    //CheckedChangeListener
//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        switch(buttonView.getId()) {
//            case (R.id.SWled):
//                (new Thread(new workerThread("LEDon"))).start();
//                break;
//
//        }
//
//    }
        //@Override
   // public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
     //   return true;
    }



