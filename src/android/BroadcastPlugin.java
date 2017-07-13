package scan.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by CrazyDong on 2017/7/12.
 */
public class BroadcastPlugin extends CordovaPlugin{
  private IntentFilter intentFilter;
  private BroadcastReceiver scanReceiver;
  private static final String RES_ACTION = "android.intent.action.SCANRESULT";
  private CallbackContext mCallbackContext;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
     if(action.equals("Broadcast")){
       mCallbackContext = callbackContext;
       intentFilter = new IntentFilter(RES_ACTION);
       scanReceiver = new ScannerResultReceiver();
       cordova.getActivity().registerReceiver(scanReceiver,intentFilter);
       return true;
     }

    return false;
  }

  /*扫描结果的广播接受者*/
  private class ScannerResultReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
      if(intent.getAction().equals(RES_ACTION)){
        /*获取扫描的结果*/
        String scanResult = intent.getStringExtra("value");
        mCallbackContext.success(scanResult);
      }
    }
  }



}
