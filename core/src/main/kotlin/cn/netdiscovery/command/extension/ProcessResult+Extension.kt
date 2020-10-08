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
 * @since: V1.0  ProcessResult 的扩展函数
 */
fun ProcessResult.asCompletableFuture() = CompletableFuture<ExecutionResult>().apply {
    this.complete(getExecutionResult())
}