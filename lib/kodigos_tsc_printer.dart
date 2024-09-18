import 'package:kodigos_tsc_printer/kodigos_tsc_printer_method_channel.dart';

import 'kodigos_tsc_printer_platform_interface.dart';

class KodigosTscPrinter {
  Future<String?> getPlatformVersion() {
    return MethodChannelKodigosTscPrinter().getPlatformVersion();
  }

  Future<String?> printer(String ip, int port, String zpl) {
    return MethodChannelKodigosTscPrinter().printer(ip, port, zpl);
  }
}
