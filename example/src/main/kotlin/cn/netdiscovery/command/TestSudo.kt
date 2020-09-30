package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestSudo
 * @author: Tony Shen
 * @date: 2020-06-19 17:23
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder.buildSudoCommand("xxx","dmidecode")

    CommandExecutor.execute(cmd).getExecutionResult().let {
        val commandLine = cmd.string()
        val exitCode = it.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
}