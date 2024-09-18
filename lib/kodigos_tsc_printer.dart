import 'kodigos_tsc_printer_platform_interface.dart';

class KodigosTscPrinter {
  Future<String?> getPlatformVersion() {
    return KodigosTscPrinterPlatform.instance.getPlatformVersion();
  }

  Future<String?> printer(String ip, int port, String zpl) {
    return KodigosTscPrinterPlatform.instance.printer(ip, port, zpl);
  }
}
