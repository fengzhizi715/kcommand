package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestAdb
 * @author: Tony Shen
 * @date: 2020-05-20 21:02
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder("adb").addArg("devices").build()

    CommandExecutor.execute(cmd).getExecutionResult().let {
        val commandLine = cmd.string()
        val exitCode = it.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
}