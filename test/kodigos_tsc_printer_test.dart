import 'package:flutter_test/flutter_test.dart';
import 'package:kodigos_tsc_printer/kodigos_tsc_printer.dart';
import 'package:kodigos_tsc_printer/kodigos_tsc_printer_platform_interface.dart';
import 'package:kodigos_tsc_printer/kodigos_tsc_printer_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockKodigosTscPrinterPlatform
    with MockPlatformInterfaceMixin
    implements KodigosTscPrinterPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<String?> printer(String ip, int port, String zpl) =>
      Future.value('Success');
}

void main() {
  final KodigosTscPrinterPlatform initialPlatform =
      KodigosTscPrinterPlatform.instance;

  test('$MethodChannelKodigosTscPrinter is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelKodigosTscPrinter>());
  });

  test('getPlatformVersion', () async {
    KodigosTscPrinter kodigosTscPrinterPlugin = KodigosTscPrinter();
    MockKodigosTscPrinterPlatform fakePlatform =
        MockKodigosTscPrinterPlatform();
    KodigosTscPrinterPlatform.instance = fakePlatform;

    expect(await kodigosTscPrinterPlugin.getPlatformVersion(), '42');
  });

  test('printer', () async {
    KodigosTscPrinter kodigosTscPrinterPlugin = KodigosTscPrinter();
    MockKodigosTscPrinterPlatform fakePlatform =
        MockKodigosTscPrinterPlatform();
    KodigosTscPrinterPlatform.instance = fakePlatform;

    expect(await kodigosTscPrinterPlugin.printer('ip', 0, ''), 'Success');
  });
}
