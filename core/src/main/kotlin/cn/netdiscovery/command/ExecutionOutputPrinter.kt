package cn.netdiscovery.command

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.ExecutionOutputPrinter
 * @author: Tony Shen
 * @date: 2020-05-19 17:54
 * @since: V1.0  对执行命令内容的打印
 */
class ExecutionOutputPrinter(private val appender: Appender,private val charsetName:String = "UTF-8") {

    fun getAppender(): Appender = appender

    fun handleStdStream(stdInputStream: InputStream, sb:StringBuilder? = null) {
        formatStream(stdInputStream, false, sb)
    }

    fun handleErrStream(errorStream: InputStream) {
        formatStream(errorStream, true)
    }

    fun handleErrMessage(errorMsg: String) {
        showOutputLine(errorMsg, true)
    }

    private fun formatStream(inputStream: InputStream, isError: Boolean, sb:StringBuilder? = null) {
        try {
            BufferedReader(InputStreamReader(inputStream,charsetName)).use { br ->
                var line: String? = null
                while (br.readLine().also { line = it } != null)
                    showOutputLine(line!!, isError, sb)
            }
        } catch (e: IOException) {
            showOutputLine(e.fillInStackTrace().toString() + CommandExecutor.NEW_LINE, true)
        }
    }

    private fun showOutputLine(line: String, isError: Boolean, sb:StringBuilder? = null) {
        if (isError) {
            appender.appendErrText(line)
        } else {
            sb?.let {
                it.append(line).append("\r\n")
            }

            appender.appendStdText(line)
        }
    }

    companion object {

        /**
         * 默认的 Appender
         */
        val DEFAULT_APPENDER = object : Appender {

            override fun appendStdText(text: String) {
                println(text)
            }

            override fun appendErrText(text: String) {
                System.err.println(text)
            }
        }

        /**
         * 默认输出的 Printer
         */
        val DEFAULT_OUTPUT_PRINTER = ExecutionOutputPrinter(DEFAULT_APPENDER)
    }
}