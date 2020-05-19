package cn.netdiscovery.command

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
class ProcessMonitor(
    private val process: Process,
    private val futureReport: Future<ExecutionReport>
) {

    fun isAlive(): Boolean = process.isAlive

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

    fun getExecutionReport(): ExecutionReport? {
        var report: ExecutionReport? = null
        try {
            report = futureReport.get()
        } catch (e: InterruptedException) {
            //do nothing.
        } catch (e: ExecutionException) {
        }
        return report
    }
}