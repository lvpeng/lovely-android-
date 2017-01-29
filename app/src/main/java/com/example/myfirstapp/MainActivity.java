package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    private WebView mWebView;

    private ProgressBar mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView)findViewById(R.id.wvPortal);

        mLoading = (ProgressBar)findViewById(R.id.pbLoading);

        mWebView.loadUrl("file:///android_asset/www/index.html");

        WebSettings mWebSettings = mWebView.getSettings();

        mWebSettings.setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new BridgetWCClient());

    }

    private class BridgetWCClient extends WebChromeClient {

        @Override

        public boolean onJsPrompt(WebView view, String url, String title, String message, JsPromptResult result) {

            if (title.equals("bridgeKey")){

                JSONObject commandJSON = null;
                try {
//                    Log.i("myTag", message);

                    commandJSON = new JSONObject(message);

                    processCommand(commandJSON);

                }catch (JSONException ex){

                    Log.e(TAG, "Invalid JSON:" + ex.getMessage());

                    result.confirm();

                    return true;
                }

                result.confirm();

                return true;

            }else{

                return false;

            }
        }
    }

    private void processCommand(JSONObject commandJSON)

            throws JSONException{

        String command = commandJSON.getString("method");

        if("login".equals(command)){

            int state = commandJSON.getInt("state");

            if(state == 0){

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override

                    public void run() {

                        mLoading.setVisibility(View.VISIBLE);

                    }

                });

            }

            else if(state == 1){

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override

                    public void run() {

                        mLoading.setVisibility(View.GONE);

                    }

                });

            }

        }

    }

}
