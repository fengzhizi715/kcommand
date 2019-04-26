package cn.netdiscovery.command

import cn.netdiscovery.command.domain.CommandResult
import com.safframework.tony.common.utils.IOUtils
import com.safframework.tony.common.utils.Preconditions
import java.io.*
import java.util.concurrent.TimeUnit

/**
 * Created by tony on 2019-04-26.
 */
abstract class Command {

    abstract fun exec(commands: String): CommandResult

    abstract fun exec(commands: String, callback: OutputCallback): CommandResult

    abstract fun exec(commands: String, dir: String): CommandResult

    abstract fun exec(commands: String, dir: String, callback: OutputCallback): CommandResult

    abstract fun exec(commands: String, timeoutInSeconds: Int): CommandResult

    abstract fun exec(commands: String, timeoutInSeconds: Int, callback: OutputCallback): CommandResult

    abstract fun exec(commands: String, dir: String, timeoutInSeconds: Int): CommandResult

    abstract fun exec(commands: String, dir: String, timeoutInSeconds: Int, callback: OutputCallback): CommandResult

    @Throws(IOException::class)
    private fun getString(stream: InputStream, commandResult: CommandResult?, outputCallback: OutputCallback?): String {

        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(stream))
            var line: String? = null
            val stringBuilder = StringBuilder()
            val lineSeparator = System.getProperty("line.separator")
            while ({ line = reader.readLine(); line }() != null) {
               stringBuilder
                    .append(line)
                    .append(lineSeparator)
                if (commandResult != null && outputCallback != null) {
                    commandResult.output = stringBuilder.toString()
                    outputCallback.onOutput(commandResult)
                }
            }

            return stringBuilder.toString()
        } finally {
            IOUtils.closeQuietly(reader)
        }
    }

    /**
     * @param rootCommand      win:cmd, linux /bin/bash
     * @param commands         command to execute
     * @param directory        work direction
     * @param timeoutInSeconds timeout in seconds, -1 no time no timeout
     * @return CommandResult
     */
    internal fun execute(
        rootCommand: String,
        commands: String,
        directory: String?,
        timeoutInSeconds: Int,
        callback: OutputCallback?
    ): CommandResult {

        if (Preconditions.isBlank(commands)) {
            return CommandResult.empty()
        }

        lateinit var commandResult: CommandResult
        lateinit var process: Process
        lateinit var out: PrintWriter
        var code: Int
        try {
            val processBuilder = ProcessBuilder(rootCommand)
            if (directory != null && !directory.isEmpty()) {
                processBuilder.directory(File(directory))
            }
            processBuilder.redirectErrorStream(true)
            process = processBuilder.start()
            out = PrintWriter(BufferedWriter(OutputStreamWriter(process.outputStream)), true)
            out.println(commands)
            out.println("exit")
            commandResult = CommandResult()
            commandResult.isRunning = true
            val output = getString(process.inputStream, commandResult, callback)
            commandResult.output = output
            if (timeoutInSeconds < 0) {
                code = process.waitFor()
            } else {
                code = if (process.waitFor(timeoutInSeconds.toLong(), TimeUnit.SECONDS)) 0 else 1
            }
            commandResult.isRunning = false
            callback?.onOutput(commandResult)
        } catch (e: Exception) {
            e.printStackTrace()
            code = 1
            if (commandResult == null) {
                commandResult = CommandResult()
            }
            commandResult.exception = e.message
        } finally {
            IOUtils.closeQuietly(out)
            process.destroy()
        }
        commandResult.code = code
        commandResult.isRunning = false
        return commandResult
    }

    companion object {

        private lateinit var INSTANCE: Command

        val instance: Command
            get() {
                if (INSTANCE == null) {
                    synchronized(Command::class.java) {
                        if (INSTANCE == null) {
                            val os = System.getProperty("os.name")
                            if (os.toLowerCase().startsWith("win")) {
                                INSTANCE = WindowsCommand()
                            } else {
                                INSTANCE = LinuxCommand()
                            }
                        }
                    }
                }
                return INSTANCE
            }
    }

}