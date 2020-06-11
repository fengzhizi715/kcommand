package cn.netdiscovery.command

import java.util.concurrent.TimeUnit

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestTimeout
 * @author: Tony Shen
 * @date: 2020-06-11 16:22
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder("ping").addArg("baidu.com").build()

    try {
        val  result = CommandExecutor.execute(cmd, null).getExecutionResult(5,TimeUnit.SECONDS)

        result?.let {

            val commandLine = it.command().string()
            val exitCode = it.exitValue()

            println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
        }
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
}