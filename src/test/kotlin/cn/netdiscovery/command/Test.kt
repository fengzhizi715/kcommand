package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.Test
 * @author: Tony Shen
 * @date: 2020-05-19 20:03
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {


//    val cmd = CommandBuilder("ping").addArg("baidu.com").build()
    val list = mutableListOf<String>()
    list.add("sh")
    list.add("-c")
    list.add("ps aux | grep java")

    val cmd = CommandBuilder.buildRawCommand2("ps aux | grep java", list.toTypedArray())

    val eop = ExecutionOutputPrinter(object : Appender {

        override fun appendStdText(text: String) {
            println(text)
        }

        override fun appendErrText(text: String) {
            System.err.println(text)
        }
    })

    try {
        val pMonitor = CommandExecutor.execute(cmd, null, eop) //execute the command, redirect the output to eop.
        val report = pMonitor.getExecutionReport() //blocks until the process finishes or gets aborted.
        val commandLine = cmd.string()
        val exitCode = report!!.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
}