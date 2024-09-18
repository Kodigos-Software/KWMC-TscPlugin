import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:kodigos_tsc_printer/kodigos_tsc_printer_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelKodigosTscPrinter platform = MethodChannelKodigosTscPrinter();
  const MethodChannel channel = MethodChannel('kodigos_tsc_printer');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
