import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'kodigos_tsc_printer_platform_interface.dart';

/// An implementation of [KodigosTscPrinterPlatform] that uses method channels.
class MethodChannelKodigosTscPrinter extends KodigosTscPrinterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('kodigos_tsc_printer');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> printer(String ip, int port, String zpl) async {
    final String result = await methodChannel.invokeMethod(
        'printer', {'ipAddress': ip, 'portAddress': port, 'message': zpl});
    return result;
  }
}
