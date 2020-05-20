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
 * @version: V1.0 <描述当前版本功能>
 */
class ExecutionOutputPrinter(private val appender: Appender) {

    fun getAppender(): Appender = appender

    fun handleStdStream(stdInputStream: InputStream) {
        formatStream(stdInputStream, false)
    }

    fun handleErrStream(errorStream: InputStream) {
        formatStream(errorStream, true)
    }

    private fun formatStream(inputStream: InputStream, isError: Boolean) {
        try {
            BufferedReader(InputStreamReader(inputStream)).use { br ->
                var line: String? = null
                while (br.readLine().also { line = it } != null) showOutputLine(line!!, isError)
            }
        } catch (e: IOException) {
            showOutputLine(e.fillInStackTrace().toString() + CommandExecutor.NEW_LINE, true)
        }
    }

    private fun showOutputLine(line: String, isError: Boolean) {
        if (isError) appender.appendErrText(line) else appender.appendStdText(line)
    }

    companion object {

        /**
         * will pass the output directly to System.out .
         */
        val DEFAULT_OUTPUT_PRINTER = ExecutionOutputPrinter(object : Appender {

            override fun appendStdText(text: String) {
                println(text)
            }

            override fun appendErrText(text: String) {
                System.err.println(text)
            }
        })
    }

}