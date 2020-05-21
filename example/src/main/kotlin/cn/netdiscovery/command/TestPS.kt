package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.PS
 * @author: Tony Shen
 * @date: 2020-05-20 20:53
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val list = mutableListOf<String>()
    list.add("sh")
    list.add("-c")

    val psCommand = "ps aux | grep java"

    list.add(psCommand)

    val cmd = CommandBuilder.buildRawCommand(psCommand, list.toTypedArray())

    try {
        val pResult = CommandExecutor.execute(cmd, null, object : Appender {

            override fun appendStdText(text: String) {
                println(text)
            }

            override fun appendErrText(text: String) {
                System.err.println(text)
            }
        })

        pResult.getExecutionResult()?.let {
            val commandLine = cmd.string()
            val exitCode = it.exitValue()
            println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
        }
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
}