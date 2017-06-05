package com.project.gta.demo;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Paul on 29.11.2016.
 */
public class BluetoothAdministration implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    //region Variables
    private BluetoothSocket mmSocket ;
    private BluetoothDevice mmDevice = null;
    final private byte delimiter = 33;
    private int readBufferPosition = 0;
    private static BlockingQueue<Runnable> mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();
    private boolean firstConnection =true;
    private final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    // Creates a thread pool manager
    public ThreadPoolExecutor mDecodeThreadPool = new ThreadPoolExecutor(
            NUMBER_OF_CORES,       // Initial pool size
            NUMBER_OF_CORES,       // Max pool size
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            mDecodeWorkQueue);

    private static BluetoothAdministration _instance = null;
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    public BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
    public volatile boolean isconnected = false;
    private Handler handler = new Handler();
    private Context context;
    private ProgressDialog connectDialog;
    private Toast toast;

    private volatile boolean keeprunning;
    //endregion


    //region Singleton
    public static BluetoothAdministration getInstance(Context context_) {
        if (_instance == null)
            _instance = new BluetoothAdministration(context_);
        else _instance.context = context_;
        return _instance;
    }
    private BluetoothAdministration(Context activityContext) {
        context = activityContext;
    }
    //endregion

    public void connect() {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (!pairedDevices.isEmpty()) {
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
             showProgressBar(true);
             Log.e("", "connecting...");
             mmSocket.connect();
             Log.e("", "Connected");
             isconnected = true;
             showProgressBar(false);
             mDecodeThreadPool.execute(new checkConnectionState());
             showToast("Connected");

        }
        catch(Exception e){
                Log.e("", "Couldn't establish Bluetooth connection!: " + e.toString());
                keeprunning = false;
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

    public void dialogTimeOut(long time){

       handler.postDelayed(new Runnable() {
           @Override
            public void run() {

               if(connectDialog.isShowing()){
                   connectDialog.dismiss();
                   showAlertBox(4);
               }
           }
        }, time);


    }

    private void showProgressBar(boolean b){

        if(b){
            keeprunning = true;
            dialogTimeOut(10000);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    connectDialog = new ProgressDialog(context);
                    connectDialog.setMessage("Connecting...");
                    connectDialog.setCancelable(false);
                    connectDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            keeprunning = false;
                        }
                    });
                    connectDialog.show();
                }
            });
        }
        else
        {
            handler.post(new Runnable() {
                @Override
                    public void run() {
                    connectDialog.dismiss();
                }
            });
        }
    }

 //Methode, um alle laufenden Threads zu beenden
    public static void cancelAll() {
        /*
         * Creates an array of Runnables that's the same size as the
         * thread pool work queue
         */
        Runnable[] runnableArray = new Runnable[mDecodeWorkQueue.size()];
        // Populates the array with the Runnables in the queue
        mDecodeWorkQueue.toArray(runnableArray);
        // Stores the array length in order to iterate over the array
        int len = runnableArray.length;
        /*
         * Iterates over the array of Runnables and interrupts each one's Thread.
         */
        synchronized (getInstance(null)) {
            // Iterates over the array of tasks
            for (int runnableIndex = 0; runnableIndex < len; runnableIndex++) {
                // Gets the current thread
                Thread thread = (Thread) runnableArray[runnableIndex];
                // if the Thread exists, post an interrupt to it
                if (null != thread) {
                    thread.interrupt();
                }
            }
        }
    }

    final class workerThread implements Runnable {

        private String btMsg;

        workerThread(String msg) {
            btMsg = msg;
        }
        @Override
        public void run() {
            if (!isconnected) {
                connect();
            }

            if (!(btMsg == null) && keeprunning){
                sendBtMsg(btMsg);
                }

            while (keeprunning) {
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

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (context instanceof SinglePlantMenu)
                                        {
                                            String newString = "";
                                            for(int i = 0; i < data.length(); i++) {
                                                if (Character.isDigit(data.charAt(i))) {
                                                    newString = data.substring(i);
                                                    break;
                                                }
                                            }
                                            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                            writeToFile(currentDateTimeString + "\n" + newString,"CurrentValue.txt", context);
                                            ((SinglePlantMenu) context).readData();
                                        }
                                        if(context instanceof HumidityGraph) {
                                            writeToFile(data,"Values.txt", context);
                                            ((HumidityGraph) context).refreshGraph();
                                            showToast("graph refreshed");
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
                               keeprunning = false;
                         }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    try{
                        mmSocket.close();
                    }
                    catch(Exception ex)
                    {
                        Log.e("", ex.toString());
                    }
                }
            }

        }
    }

    private void writeToFile(String text, String FILENAME, Context c) {
        try (
                FileOutputStream stream = c.openFileOutput(FILENAME, MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(stream)) {
                osw.write(text);
                osw.close();
                stream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void showToast(final String text){

        handler.post(new Runnable() {
            @Override
            public void run() {
                toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public  void onClick(View v){
        switch(v.getId()) {
            case R.id.BTNgetHumidity:
                execute("request");
                break;
            case R.id.BTNrefresh:
                execute("graph");
                break;
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.SWled:

                if(isChecked) {
                    execute("LED on");
                }
                else{
                    execute("LED off");
                }
                break;
            case R.id.SWsounds:
                if(isChecked) {
                    execute("alarm on");
                }
                else {
                    execute("alarm off");
                }
                break;
        }
    }

    public void execute(String command){
        if (!BA.isEnabled())
        {
            showAlertBox(1);
            if (context instanceof SettingsMenu)
            {
                ((SettingsMenu) context).SWled.setChecked(false);
                ((SettingsMenu) context).SWsounds.setChecked(false);
            }
            return;
        }
        else {
            keeprunning = true;
            mDecodeThreadPool.execute(new workerThread(command));
        }
    }

    private void showAlertBox(int i){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
        switch(i) {
            case 0:
                dlgAlert.setMessage("Please establish a Bluetooth connection to the Pi in the settings.");
                dlgAlert.setTitle("No Connection to Pi");
                break;
            case 1:
                dlgAlert.setMessage("Please enable Bluetooth");
                dlgAlert.setTitle("Bluetooth is disabled");
                break;
            case 2:
                dlgAlert.setMessage("Please enable Bluetooth and connect to the Pi.");
                dlgAlert.setTitle("Bluetooth is disabled");
                break;
            case 3:
                dlgAlert.setMessage("You are already connected to th Pi.");
                dlgAlert.setTitle("Error");
                break;
            case 4:
                dlgAlert.setMessage("Possible reasons:\n" +
                        "- there is already a connection\n" +
                        "- the devices are not paired with each other\n" +
                        "- the script on the pi is not running\n" +
                        "If you checked all points above try restarting the app");
                dlgAlert.setTitle("Connection Timeout");
            break;

        }

        dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {return;}
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    final class checkConnectionState implements Runnable {
        @Override
        public void run(){
            while(!Thread.currentThread().isInterrupted())
            {
                try {
                    mmSocket.getInputStream().read();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                catch (IOException ex){
                    Log.e("IOException",ex.toString());
                    isconnected = false;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                           showToast("Connection lost");
                        }
                    });
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

