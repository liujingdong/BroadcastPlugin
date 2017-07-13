/**
 * Created by CrazyDong on 2017/7/12.
 */
var exec = require('cordova/exec');

module.exports = {
    /*调用对比
     * js: cordova.exec(callbackContext.success, callbackContext.error, PluginName, action, args);
     * java: public boolean execute(String action, JSONArray args, CallbackContext callbackContext)
     * */
    broadcast:function(action,success,err){
//        exec(success,err, "IDataScanPlugin", "singleScan", [flagMsg]);
        exec(success,err, "BroadcastPlugin", action, null);
    }
}