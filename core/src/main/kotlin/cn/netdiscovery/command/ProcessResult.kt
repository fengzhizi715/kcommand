package cn.netdiscovery.command

import cn.netdiscovery.command.fuction.Result
import cn.netdiscovery.command.fuction.resultFrom
import java.lang.Exception
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.ProcessMonitor
 * @author: Tony Shen
 * @date: 2020-05-19 18:00
 * @version: V1.0 <描述当前版本功能>
 */
class ProcessResult(
    private val process: Process,
    private val futureResult: Future<ExecutionResult>
) {

    private fun isAlive(): Boolean = process.isAlive

    /**
     * 命令停止执行
     */
    fun abort(): Int {
        if (isAlive()) {
            process.destroyForcibly()
            try {
                process.waitFor()
            } catch (e: InterruptedException) {
                //do nothing.
            }
        }
        return process.exitValue()
    }

    /**
     * 返回 ExecutionResult （会确保命令已经执行完毕）
     * ExecutionResult 的 exitValue() 会返回命令执行成功与否。(0 表示执行成功)
     */
    fun getExecutionResult(): ExecutionResult? {
        var result: ExecutionResult? = null
        try {
            result = futureResult.get()
        } catch (e: InterruptedException) {
            //do nothing.
        } catch (e: ExecutionException) {
        }
        return result
    }

    /**
     * 返回命令的执行结果，使用 Result 进行封装。可以调用 Result.get() 来或者执行的值或者异常
     */
    fun getResult():Result<ExecutionResult,Exception> = resultFrom {
        futureResult.get()
    }
}