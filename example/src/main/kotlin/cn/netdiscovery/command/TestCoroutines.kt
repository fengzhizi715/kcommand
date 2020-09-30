package cn.netdiscovery.command

import cn.netdiscovery.command.coroutines.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestCoroutines
 * @author: Tony Shen
 * @date: 2020-06-13 23:15
 * @version: V1.0 <描述当前版本功能>
 */
fun main() = runBlocking{

    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    CommandExecutor.execute(cmd)
        .asFlow()
        .collect{
            val commandLine = cmd.string()
            val exitCode = it.exitValue()
            println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
        }
}