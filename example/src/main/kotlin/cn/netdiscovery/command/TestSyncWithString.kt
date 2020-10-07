package cn.netdiscovery.command


/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestSyncWithString
 * @author: Tony Shen
 * @date: 2020-10-07 01:22
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

     val result = CommandExecutor.executeSyncWithString(cmd, appender = object :Appender{
        override fun appendStdText(text: String) {
        }

        override fun appendErrText(text: String) {
            System.err.println(text)
        }
    })

    println(result)
}