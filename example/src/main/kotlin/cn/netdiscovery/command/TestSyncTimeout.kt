package cn.netdiscovery.command

import java.util.concurrent.TimeUnit

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestSyncTimeout
 * @author: Tony Shen
 * @date: 2020-07-15 12:43
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder("ping").addArg("baidu.com").build()

    try {
        CommandExecutor.executeSync(cmd, null,5, TimeUnit.SECONDS,object :Appender{
            override fun appendStdText(text: String) {
            }

            override fun appendErrText(text: String) {
            }

        }).getExecutionResult().let {

            val commandLine = it.command().string()
            val exitCode = it.exitValue()

            println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
        }
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
}