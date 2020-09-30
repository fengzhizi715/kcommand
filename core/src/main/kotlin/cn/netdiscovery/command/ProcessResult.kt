package cn.netdiscovery.command


import cn.netdiscovery.result.Result
import cn.netdiscovery.result.resultFrom
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.ProcessMonitor
 * @author: Tony Shen
 * @date: 2020-05-19 18:00
 * @version: V1.0 <描述当前版本功能>
 */
class ProcessResult(
    private val cmd: Command,
    private val process: Process?=null,
    private val futureResult: Future<ExecutionResult>
) {

    private fun isAlive(): Boolean = process?.isAlive?:false

    /**
     * 命令停止执行
     */
    fun abort(): Int {
        if (process!=null) {
            if (isAlive()) {
                process.destroyForcibly()
                try {
                    process.waitFor()
                } catch (e: InterruptedException) {
                    //do nothing.
                }
            }
            return process.exitValue()
        } else {
            return -1;
        }
    }

    /**
     * 返回 ExecutionResult （会确保命令已经执行完毕）
     * ExecutionResult 的 exitValue() 会返回命令执行成功与否。(0 表示执行成功)
     */
    fun getExecutionResult(): ExecutionResult = try {
        futureResult.get()
    } catch (e: InterruptedException) {
        ExecutionResult.makeReport(cmd,-1)
    } catch (e: ExecutionException) {
        ExecutionResult.makeReport(cmd,-1)
    }

    /**
     * 增加超时机制
     */
    fun getExecutionResult(timeout:Long,unit:TimeUnit): ExecutionResult = try {
        futureResult.get(timeout,unit)
    } catch (e: InterruptedException) {
        ExecutionResult.makeReport(cmd,-1)
    } catch (e: ExecutionException) {
        ExecutionResult.makeReport(cmd,-1)
    } catch (e:TimeoutException) {
        futureResult.cancel(true)
        val exitValue = abort()
        ExecutionResult.makeReport(cmd,exitValue)
    }

    /**
     * 返回命令的执行结果，使用 Result 进行封装。可以调用 Result.get() 来获取执行的值或者异常
     */
    fun getResult():Result<ExecutionResult,Exception> = resultFrom {
        futureResult.get()
    }
}