package br.com.kodigos.kodigos_tsc_printer

import androidx.annotation.NonNull

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** KodigosTscPrinterPlugin */
class KodigosTscPrinterPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "kodigos_tsc_printer")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } 
    if (call.method.equals("printer")) {
      try {

          val policy = ThreadPolicy.Builder().permitAll().build()
          StrictMode.setThreadPolicy(policy)

          val ipAddress = call.argument<String>("ipAddress");
          val portAddress = call.argument<Int>("portAddress");
          Log.i("Flutter_tsc", "Opening Port")
          val openRsult = TscEthernetDll.openport(ipAddress!!, portAddress!!)
          Log.i("Flutter_tsc", openRsult)

          if(openRsult == "1") {


              val message = call.argument<String>("message");
              Log.i("Flutter_tsc", "Send Command")
              TscEthernetDll.sendcommand(message!!)

              TscEthernetDll.printlabel(2, 1);

              


              TscEthernetDll.closeport();

              result.success("Success printing 1");
          }else{
              result.success("Erro ao conectar impressora -> "+ TscEthernetDll.status() + " "+ TscEthernetDll.status);
          }
      }catch (e: Exception){
          result.success("Erro android: " + e.message!! + "" + e.stackTrace.toString())
      }
    }
    else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
