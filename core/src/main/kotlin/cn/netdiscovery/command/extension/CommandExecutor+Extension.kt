package cn.netdiscovery.command.extension

import cn.netdiscovery.command.*
import java.io.File
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.extension.`CommandExecutor+Extension`
 * @author: Tony Shen
 * @date: 2020-10-07 22:23
 * @since: V1.3 <描述当前版本功能>
 */
fun CommandExecutor.getCompletableFutureWithSync(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, charsetName:String = "UTF-8", appender: Appender): CompletableFuture<String>
        = getCompletableFutureWithSyncOutputPrinter(cmd, directory, timeout, unit, ExecutionOutputPrinter(appender,charsetName))

fun CommandExecutor.getCompletableFutureWithSyncOutputPrinter(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, outputPrinter: ExecutionOutputPrinter = ExecutionOutputPrinter.DEFAULT_OUTPUT_PRINTER): CompletableFuture<String> {

    return CompletableFuture<String>().apply {
        val result = getStringWithSyncOutputPrinter(cmd,directory,timeout,unit,outputPrinter)
        this.complete(result)
    }
}