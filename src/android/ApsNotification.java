package cordova.plugin.aps.notification;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

/**
 * This class echoes a string called from JavaScript.
 */
public class ApsNotification extends CordovaPlugin {
    BroadcastReceiver receiver;
    String payload;


    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        if (this.receiver == null) {
            this.receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Intent i = getIntent();
                    if (i == null) {
                        Log.d("***DEBUG****", "Intent was null");
                    }else{
                        Log.d("**** DEBUG ***", "Intent OK");
                    }
                    ApsNotification.this.payload= i.getStringExtra("payload");
                    if(ApsNotification.this.payload!= null) {
                        Log.d("*** DEBUG", ApsNotification.this.payload);
                    }
                }
            };
            webView.getContext().registerReceiver(this.receiver, intentFilter);
        }
    }

    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("notification")) {
            this.coolMethod(callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(CallbackContext callbackContext) {
            callbackContext.success(this.payload);
    }
}
