import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'kodigos_tsc_printer_method_channel.dart';

abstract class KodigosTscPrinterPlatform extends PlatformInterface {
  /// Constructs a KodigosTscPrinterPlatform.
  KodigosTscPrinterPlatform() : super(token: _token);

  static final Object _token = Object();

  static KodigosTscPrinterPlatform _instance = MethodChannelKodigosTscPrinter();

  /// The default instance of [KodigosTscPrinterPlatform] to use.
  ///
  /// Defaults to [MethodChannelKodigosTscPrinter].
  static KodigosTscPrinterPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [KodigosTscPrinterPlatform] when
  /// they register themselves.
  static set instance(KodigosTscPrinterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<String?> printer(String ip, int port, String zpl) {
    throw UnimplementedError('printer() has not been implemented.');
  }
}
