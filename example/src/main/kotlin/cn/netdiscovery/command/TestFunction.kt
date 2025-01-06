package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestFunction
 * @author: Tony Shen
 * @date: 2020-05-19 20:03
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    val result = CommandExecutor.execute(cmd).getResult().getOrNull()

    if (result is ExecutionResult) {

        val commandLine = cmd.string()
        val exitCode = result.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
}