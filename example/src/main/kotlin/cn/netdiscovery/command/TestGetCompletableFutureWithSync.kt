package cn.netdiscovery.command

import cn.netdiscovery.command.extension.getCompletableFutureWithSync

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestGetCompletableFutureWithSync
 * @author: Tony Shen
 * @date: 2020-10-07 22:29
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    val result = CommandExecutor.getCompletableFutureWithSync(cmd, appender = object : Appender {
        override fun appendStdText(text: String) {
        }

        override fun appendErrText(text: String) {
        }
    }).get()

    println(result)
}