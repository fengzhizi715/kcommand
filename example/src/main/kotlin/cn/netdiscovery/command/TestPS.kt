package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestPS
 * @author: Tony Shen
 * @date: 2020-05-20 20:53
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    CommandExecutor.execute {
        val list = mutableListOf<String>()
        list.add("sh")
        list.add("-c")

        val psCommand = "ps aux | grep java"

        list.add(psCommand)

        CommandBuilder.buildRawCommand(psCommand, list.toTypedArray())
    }.getExecutionResult().let {
        val commandLine = it.command().string()
        val exitCode = it.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
}