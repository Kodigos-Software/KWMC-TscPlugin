package br.com.kodigos.tsc_printer

import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket

class PrinterTSC {

    private var InStream: InputStream? = null
    private var OutStream: OutputStream? = null
    private var socket: Socket? = null
    private var printerstatus = ""
    private var port_connected = 0

    private val buffer = ByteArray(1024)
    private var readBuf = ByteArray(1024)

     var status = ""

    fun openport(ipaddress: String?, portnumber: Int): String {
        Log.e("openport", Thread.currentThread().toString())

        try {
            this.socket = Socket()
            this.socket!!.connect(InetSocketAddress(ipaddress, portnumber), 2000)
            this.InStream = this.socket!!.getInputStream()
            this.OutStream = this.socket!!.getOutputStream()
            this.port_connected = 1
        } catch (var7: Exception) {
            Log.e("openport_error", var7.toString())
            Log.e("openport_error", var7.stackTraceToString())
            status = var7.toString()
            try {
                this.socket!!.close()
            } catch (var5: IOException) {
                this.port_connected = 0
                return "-2"
            }
            this.port_connected = 0
            return "-1"
        }
        try {
            Thread.sleep(100L)
        } catch (var6: InterruptedException) {
            var6.printStackTrace()
        }
        return "1"
    }

    fun sendcommand(message: String): String {
        return if (this.port_connected == 0) {
            "-1"
        } else {
            val msgBuffer = message.toByteArray()
            try {
                this.OutStream!!.write(msgBuffer)
                "1"
            } catch (var4: IOException) {
                "-1"
            }
        }
    }

    fun printlabel(quantity: Int, copy: Int): String? {
        return if (this.port_connected == 0) {
            "-1"
        } else {
            var message = ""
            message = "PRINT $quantity, $copy\r\n"
            val msgBuffer = message.toByteArray()
            try {
                this.OutStream!!.write(msgBuffer)
                "1"
            } catch (var6: IOException) {
                status = var6.toString()
                "-1"
            }
        }
    }

    fun closeport(): String? {
        try {
            Thread.sleep(1500L)
        } catch (var3: InterruptedException) {
            var3.printStackTrace()
        }
        return if (port_connected == 0) {
            "-1"
        } else {
            try {
                socket!!.close()
                "1"
            } catch (var2: IOException) {
                status = var2.toString()
                "-1"
            }
        }
    }

    fun status(): String {
        return if (port_connected == 0) {
            "-1"
        } else {
            val message = byteArrayOf(27, 33, 83)
            this.readBuf = ByteArray(1024)
            try {
                OutStream!!.write(message)
            } catch (var4: IOException) {
                return "-1"
            }
            try {
                Thread.sleep(1000L)
            } catch (var3: InterruptedException) {
                var3.printStackTrace()
            }
            var tim: Int
            try {
                while (InStream!!.available() > 0) {
                    this.readBuf = ByteArray(1024)
                    tim = InStream!!.read(this.readBuf)
                }
            } catch (var5: IOException) {
                return "-1"
            }
            if (this.readBuf.get(0).toInt() == 2 && this.readBuf.get(5).toInt() == 3) {
                tim = 0
                while (tim <= 7) {
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 64 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 64 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Ready"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 69 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 96 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Head Open"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 64 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 96 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Head Open"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 69 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 72 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Ribbon Jam"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 69 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 68 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Ribbon Empty"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 69 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 65 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "No Paper"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 69 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 66 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Paper Jam"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 69 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 65 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Paper Empty"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 67 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 64 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Cutting"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 75 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 64 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Waiting to Press Print Key"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 76 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 64 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Waiting to Take Label"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 80 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 64 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Printing Batch"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 96 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 64 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Pause"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    if (this.readBuf.get(tim).toInt() == 2 && this.readBuf.get(tim + 1)
                            .toInt() == 69 && this.readBuf.get(tim + 2)
                            .toInt() == 64 && this.readBuf.get(tim + 3)
                            .toInt() == 64 && this.readBuf.get(tim + 4)
                            .toInt() == 64 && this.readBuf.get(tim + 5).toInt() == 3
                    ) {
                        printerstatus = "Pause"
                        this.readBuf = ByteArray(1024)
                        break
                    }
                    ++tim
                }
            }
            printerstatus
        }
    }
}