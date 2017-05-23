package com.project.gta.demo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WVaskus extends AppCompatActivity {


    private WebView WEBask_us;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!connection()) {
            Toast toast_notifications_enabled = Toast.makeText
                    (getApplicationContext(), "Internet disconnected", Toast.LENGTH_LONG);
            toast_notifications_enabled.show();
        }

        setContentView(R.layout.activity_wvaskus);

        WEBask_us = (WebView) findViewById(R.id.WEBask_us);
        // Enable Javascript
        WebSettings webSettings = WEBask_us.getSettings();
        webSettings.setJavaScriptEnabled(false);

        WEBask_us.loadUrl("http://gtaproject.wordpress.com/contact");

        // Force links and redirects to open in the WebView instead of in a browser
        WEBask_us.setWebViewClient(new WebViewClient());
    }


    // Prevents activity from closing when pressing android back button
    // Also enables going back in WebView
    @Override
    public void onBackPressed() {
        if(WEBask_us.canGoBack()) {
            WEBask_us.goBack();
        } else {
            super.onBackPressed();
        }
    }
    public boolean connection() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
    }

}
