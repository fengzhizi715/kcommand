package cn.netdiscovery.command.extension

import cn.netdiscovery.command.ExecutionResult
import cn.netdiscovery.command.ProcessResult
import java.util.concurrent.CompletableFuture

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.extension.`ProcessResult+Extension`
 * @author: Tony Shen
 * @date: 2020-06-05 13:59
 * @version: V1.0 <描述当前版本功能>
 */
fun ProcessResult.asCompletableFuture(): CompletableFuture<ExecutionResult> {

    val future = CompletableFuture<ExecutionResult>()
    future.complete(this.getExecutionResult())
    return future
}