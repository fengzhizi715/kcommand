package cn.netdiscovery.command

import cn.netdiscovery.command.rxjava3.getObservableWithSync

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestGetObservableWithSync
 * @author: Tony Shen
 * @date: 2020-10-07 16:27
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    CommandExecutor.getObservableWithSync(cmd, appender = object : Appender {
        override fun appendStdText(text: String) {
        }

        override fun appendErrText(text: String) {
        }
    })
   .subscribe {
       println(it)
   }
}