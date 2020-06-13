package cn.netdiscovery.command

import cn.netdiscovery.command.extension.asCompletableFuture

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.TestCompletableFuture
 * @author: Tony Shen
 * @date: 2020-06-05 14:54
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = getPsCmd()

    try {
        val executionResult = CommandExecutor.execute(cmd, null).asCompletableFuture().get()

        val commandLine = cmd.string()
        val exitCode = executionResult.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
}