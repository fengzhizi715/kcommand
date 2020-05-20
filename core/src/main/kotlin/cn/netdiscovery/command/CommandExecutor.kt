package cn.netdiscovery.command

import java.io.File
import java.io.IOException
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.CommandExecutor
 * @author: Tony Shen
 * @date: 2020-05-19 17:17
 * @version: V1.0 <描述当前版本功能>
 */
object CommandExecutor {

    private val pb = ProcessBuilder()
    private var WORKERS = Executors.newFixedThreadPool(2)
    internal val NEW_LINE = System.getProperty("line.separator")

    @JvmStatic
    fun setExecutors(executorService: ExecutorService):CommandExecutor {
        this.WORKERS = executorService
        return this
    }

    @JvmStatic
    @Throws(UnrecognisedCmdException::class)
    fun execute(cmdLine: String): ProcessResult = execute(CommandBuilder.buildRawCommand(cmdLine), null )

    @JvmStatic
    @Throws(UnrecognisedCmdException::class)
    fun execute(cmdLine: String, appender: Appender): ProcessResult = execute(CommandBuilder.buildRawCommand(cmdLine), null, ExecutionOutputPrinter(appender))

    @JvmStatic
    @Throws(UnrecognisedCmdException::class)
    fun execute(cmd: Command, directory: File?=null, outputPrinter: ExecutionOutputPrinter = ExecutionOutputPrinter.DEFAULT_OUTPUT_PRINTER): ProcessResult {
        val p = executeCommand(cmd, directory)
        recordOutput(p, outputPrinter)
        val futureReport = WORKERS.submit(ExecutionCallable(p, cmd))
        return ProcessResult(p, futureReport)
    }

    @Throws(UnrecognisedCmdException::class)
    private fun executeCommand(cmd: Command, directory: File?): Process {
        synchronized(pb) {
            return try {
                pb.directory(directory)
                pb.command(cmd.executable())
                pb.start()
            } catch (e: IOException) {
                throw UnrecognisedCmdException(cmd.string())
            }
        }
    }

    private fun recordOutput(p: Process, outputPrinter: ExecutionOutputPrinter) {

        WORKERS.execute { outputPrinter.handleStdStream(p.inputStream) }
        WORKERS.execute { outputPrinter.handleErrStream(p.errorStream) }
    }

    private class ExecutionCallable(private val p: Process, private val cmd: Command) : Callable<ExecutionResult> {

        @Throws(Exception::class)
        override fun call(): ExecutionResult {
            try {
                p.waitFor()
            } catch (e: InterruptedException) {
                //nothing.
            }
            return ExecutionResult.makeReport(cmd, p.exitValue())
        }
    }
}