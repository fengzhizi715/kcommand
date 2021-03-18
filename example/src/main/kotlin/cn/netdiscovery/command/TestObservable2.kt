package cn.netdiscovery.command

import cn.netdiscovery.command.rxjava3.asObservable

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestObservable2
 * @author: Tony Shen
 * @date: 2021-03-18 17:24
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    val sb = StringBuilder()

    CommandExecutor.executeSync(cmd = cmd,appender = object : Appender{
        override fun appendStdText(text: String) {
            if (text.contains(cmd.string())) {
                sb.append(text)
            }
        }

        override fun appendErrText(text: String) {
            TODO("Not yet implemented")
        }

    }).asObservable {
        sb.toString()
    }.subscribe {
        println(it)
    }
}