package com.project.gta.demo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.project.gta.demo.R.id.textView;

/**
 * Created by Paul on 29.11.2016.
 */

public class BluetoothAdministration extends BluetoothMenu implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private static BluetoothAdministration _instance = null;
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    public BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
    final boolean hasBluetooth = !(BA == null);
    Handler handler = new Handler();
    Context context;


    public static BluetoothAdministration get_instance(Context context_) {
        if (_instance == null)
            _instance = new BluetoothAdministration(context_);
        else _instance.context = context_;
        return _instance;
    }

    private BluetoothAdministration(Context activityContext) {
        context = activityContext;
    }

    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice = null;

    final byte delimiter = 33;
    int readBufferPosition = 0;
    private static BlockingQueue<Runnable> mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();

    private final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    // Creates a thread pool manager
    private ThreadPoolExecutor mDecodeThreadPool = new ThreadPoolExecutor(
            NUMBER_OF_CORES,       // Initial pool size
            NUMBER_OF_CORES,       // Max pool size
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            mDecodeWorkQueue);

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
            //msg += "\n";
            OutputStream mmOutputStream = mmSocket.getOutputStream();
            mmOutputStream.write(msg2send.getBytes());
        } catch (IOException e3) {e3.printStackTrace();
            Log.e("", "failed to transmit data!");
        }

    }
//  Methode, um alle laufenden Threads zu beenden
//    public static void cancelAll() {
//        /*
//         * Creates an array of Runnables that's the same size as the
//         * thread pool work queue
//         */
//        Runnable[] runnableArray = new Runnable[mDecodeWorkQueue.size()];
//        // Populates the array with the Runnables in the queue
//        mDecodeWorkQueue.toArray(runnableArray);
//        // Stores the array length in order to iterate over the array
//        int len = runnableArray.length;
//        /*
//         * Iterates over the array of Runnables and interrupts each one's Thread.
//         */
//        synchronized (get_instance(null)) {
//            // Iterates over the array of tasks
//            for (int runnableIndex = 0; runnableIndex < len; runnableIndex++) {
//                // Gets the current thread
//                Thread thread = (Thread) runnableArray[runnableIndex];
//                // if the Thread exists, post an interrupt to it
//                if (null != thread) {
//                    thread.interrupt();
//                }
//            }
//        }
//    }


    final class workerThread implements Runnable {

        private String btMsg;

        workerThread(){
            btMsg = null;}

        workerThread(String msg) {
            btMsg = msg;
        }
        @Override
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
                        byte[] readBuffer = new byte[1024*1024];
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
                                    @Override
                                    public void run() {
                                        if (context instanceof SinglePlantMenu)
                                        {
                                            int int_data = Integer.parseInt(data);
                                            ((SinglePlantMenu) context).getButton().setText(data + '%');

                                            if (int_data >= 60){
                                                ((SinglePlantMenu) context).getButton().setBackgroundColor(0xFF02a721); //grün
                                            }
                                            else
                                                if (int_data < 60 && int_data > 20 ){
                                                    ((SinglePlantMenu) context).getButton().setBackgroundColor(0xFFFFD700); //gelb
                                                }
                                                else{
                                                    ((SinglePlantMenu) context).getButton().setBackgroundColor(0xFFCD2626); //rot
                                                }
                                        }
                                        if(context instanceof HumidityGraph)
                                        {
                                            String FILENAME = "HumidityValues";
                                            try {
                                                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                                                fos.write(data.getBytes());
                                                fos.close();
                                            }
                                            catch(Exception ex){
                                                Log.e("","Error creating or writing file");
                                            }
                                        }

                                        else
                                        {
                                        Toast toast_bt_disabled = Toast.makeText
                                                (context,data,Toast.LENGTH_LONG);
                                        toast_bt_disabled.show();
                                        }
                                    }
                                });


                                workDone = true;
                                break;
                            } else {
                                readBuffer[readBufferPosition++] = b;
                            }
                        }
                        if (workDone ) {
                               Thread.currentThread().interrupt();
                         }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }


    @Override
    public  void onClick(View v){
        switch(v.getId()) {
            case R.id.BTNconnect_bt:
                mDecodeThreadPool.execute(new workerThread());
                break;
            case R.id.BTNgetHumidity:
                mDecodeThreadPool.execute(new workerThread("request"));
                break;
            case R.id.BTNrefresh:
                mDecodeThreadPool.execute((new workerThread("graph")));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.SWled:
                if(isChecked) {
                    mDecodeThreadPool.execute(new workerThread("LED on"));
                }
                else{
                    mDecodeThreadPool.execute(new workerThread("LED off"));
                }
                break;
            case R.id.SWsounds:
                if(isChecked) {
                    mDecodeThreadPool.execute(new workerThread("sound on"));
                }
                else{
                    mDecodeThreadPool.execute(new workerThread("sound off"));
                }
                break;
        }
    }
}

