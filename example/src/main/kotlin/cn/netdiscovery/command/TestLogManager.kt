package cn.netdiscovery.command

import cn.netdiscovery.command.log.KCommandLogManager
import cn.netdiscovery.command.log.LogProxy
import cn.netdiscovery.command.rxjava3.asObservable
import java.util.concurrent.TimeUnit

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestLogManager
 * @author: Tony Shen
 * @date: 2021-03-21 14:07
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    KCommandLogManager.logProxy(object :LogProxy{
        override fun e(tag: String, msg: String) {
        }

        override fun w(tag: String, msg: String) {
        }

        override fun i(tag: String, msg: String) {
            println(msg)
        }

        override fun d(tag: String, msg: String) {
        }

    })

    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    CommandExecutor.execute(cmd)
        .asObservable()
        .subscribe()
}