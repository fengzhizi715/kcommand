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

    fun getResult():Result<ExecutionResult,Exception> = resultFrom {
        futureResult.get()
    }
}