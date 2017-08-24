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
  private static final String RES_ACTION = "android.intent.action.SCANRESULT";//iData
  private static final String YAN_HUA = "df.scanservice.result";//研华
  private static final String FSK = "scannerdata";//富士康
  private static final String FSK_ACTION  = "com.android.server.scannerservice.broadcast";//富士康
  private CallbackContext mCallbackContext;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    //iData插件
     if(action.equals("Broadcast")){
       mCallbackContext = callbackContext;
       intentFilter = new IntentFilter(RES_ACTION);
       scanReceiver = new ScannerResultReceiver();
       cordova.getActivity().registerReceiver(scanReceiver,intentFilter);
       return true;
     }

    //研华插件
    if(action.equals("yanHua")){
      mCallbackContext = callbackContext;
      Intent sendIntent = new Intent("df.scanservice.toapp");
      cordova.getActivity().sendBroadcast(sendIntent);

      intentFilter = new IntentFilter();
      intentFilter.addAction(YAN_HUA);
      scanReceiver = new ScannerResultReceiver();
      cordova.getActivity().registerReceiver(scanReceiver,intentFilter);
      return true;
    }

    //富士康插件
    if(action.equals("FSK")){
      mCallbackContext = callbackContext;
      Intent fskSendIntent = new Intent(FSK_ACTION);
      cordova.getActivity().sendBroadcast(fskSendIntent);

      intentFilter = new IntentFilter(FSK_ACTION);
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
      //iData
      if(intent.getAction().equals(RES_ACTION)){
        /*获取扫描的结果*/
        String scanResult = intent.getStringExtra("value");
        mCallbackContext.success(scanResult);
      }

      //研华
      if(intent.getAction().equalsIgnoreCase(YAN_HUA)){
        /*获取扫描的结果*/
        String YanhuaScanResult = intent.getStringExtra("result");
        YanhuaScanResult.trim();
        mCallbackContext.success(YanhuaScanResult);
      }

      //富士康
      if(intent.getAction().equalsIgnoreCase(FSK_ACTION)){
        /*获取扫描的结果*/
        String FSKScanResult = intent.getStringExtra(FSK);
        FSKScanResult.trim();
        mCallbackContext.success(FSKScanResult);
      }



      cordova.getActivity().unregisterReceiver(scanReceiver);

    }
  }



}
