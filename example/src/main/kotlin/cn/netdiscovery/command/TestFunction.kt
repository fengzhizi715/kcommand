package cn.netdiscovery.command

import cn.netdiscovery.command.fuction.get

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.Test
 * @author: Tony Shen
 * @date: 2020-05-19 20:03
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val list = mutableListOf<String>()
    list.add("sh")
    list.add("-c")
    list.add("ps aux | grep java")

    val cmd = CommandBuilder.buildRawCommand("ps aux | grep java", list.toTypedArray())

    val eop = ExecutionOutputPrinter(object : Appender {

        override fun appendStdText(text: String) {
            println(text)
        }

        override fun appendErrText(text: String) {
            System.err.println(text)
        }
    })

    try {
        val pResult = CommandExecutor.execute(cmd, null, eop)

        val result = pResult.getResult().get()

        if (result is ExecutionResult) {

            val commandLine = cmd.string()
            val exitCode = result.exitValue()
            println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
        }
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
}