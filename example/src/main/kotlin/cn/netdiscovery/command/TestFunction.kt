package cn.netdiscovery.command

import cn.netdiscovery.command.function.get

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.Test
 * @author: Tony Shen
 * @date: 2020-05-19 20:03
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = getPsCmd()

    try {
        val result = CommandExecutor.execute(cmd, null, object : Appender {

            override fun appendStdText(text: String) {
                println(text)
            }

            override fun appendErrText(text: String) {
                System.err.println(text)
            }
        }).getResult().get()

        if (result is ExecutionResult) {

            val commandLine = cmd.string()
            val exitCode = result.exitValue()
            println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
        }
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
}