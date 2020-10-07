package cn.netdiscovery.command

import cn.netdiscovery.command.coroutines.getFlowWithSync
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestGetFlowWithSync
 * @author: Tony Shen
 * @date: 2020-10-07 22:10
 * @version: V1.0 <描述当前版本功能>
 */
fun main() = runBlocking{

    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    CommandExecutor.getFlowWithSync(cmd, appender = object :Appender{
        override fun appendStdText(text: String) {
        }

        override fun appendErrText(text: String) {
            System.err.println(text)
        }
    })
    .collect{
        println(it)
    }
}