package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestCompositeCommand
 * @author: Tony Shen
 * @date: 2020-07-20 16:03
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    CommandExecutor.execute(cmd).getExecutionResult().let {
        val commandLine = cmd.string()
        val exitCode = it.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
}