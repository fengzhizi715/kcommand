package cn.netdiscovery.command

import java.util.*
import java.util.regex.Pattern

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.CommandBuilder
 * @author: Tony Shen
 * @date: 2020-05-19 17:03
 * @version: V1.0 <描述当前版本功能>
 */
class CommandBuilder() {

    private var commandLine: String
    private val cmdArgs: ArrayList<String>
    private val cmdOptions: ArrayList<String>
    private val finalCommand: ArrayList<String>

    init {
        commandLine = EMPTY_STRING
        cmdArgs = ArrayList()
        cmdOptions = ArrayList()
        finalCommand = ArrayList()
    }

    constructor(cmdLine: String) : this() {
        forCommandLine(cmdLine)
    }

    /**
     * side effect: will clear any previously set data if any.
     */
    private fun forCommandLine(line: String): CommandBuilder {
        clearAll()
        commandLine = line
        return this
    }

    fun addOption(option: String): CommandBuilder {
        cmdOptions.add(option)
        return this
    }

    /**
     * side effect: will clear any previously set options if any.
     */
    fun withOptions(vararg params: String): CommandBuilder {
        cmdOptions.clear()
        cmdOptions.addAll(listOf(*params))
        return this
    }

    fun addArg(arg: String): CommandBuilder {
        cmdArgs.add(arg)
        return this
    }

    /**
     * side effect: will clear any previously set arguments if any.
     */
    fun withArgs(vararg args: String): CommandBuilder {
        cmdArgs.clear()
        cmdArgs.addAll(listOf(*args))
        return this
    }

    fun build(): Command {
        var executableCmdLine = finalCmdLine(finalCmdList())
        executableCmdLine = executableCmdLine.substring(0, executableCmdLine.length - 1)
        val executableCmd = splitCmd(executableCmdLine)
        return CommandImpl(executableCmdLine, executableCmd)
    }

    private fun finalCmdList(): ArrayList<String> {
        finalCommand.clear()
        finalCommand.add(commandLine)
        finalCommand.addAll(cmdOptions)
        finalCommand.addAll(cmdArgs)
        return finalCommand
    }

    private fun finalCmdLine(cmdList: ArrayList<String>): String {
        val cmd = StringBuilder()
        for (segment in cmdList) {
            cmd.append(segment)
            cmd.append(' ')
        }
        return cmd.toString()
    }

    private fun clearAll() {
        commandLine = EMPTY_STRING
        cmdOptions.clear()
        cmdArgs.clear()
        finalCommand.clear()
    }

    private class CommandImpl(private val cmdLine: String, private val executableCmd: Array<String>) : Command {

        override fun executable(): Array<String> {
            return executableCmd
        }

        override fun string(): String {
            return cmdLine
        }

        override fun toString(): String {
            return string()
        }
    }

    companion object {

        private const val EMPTY_STRING = ""
        private val QUOTES_PATTERN = Pattern.compile("([^\"|^']\\S*|[\"|'].+?[\"|'])\\s*")

        private fun splitCmd(cmd: String): Array<String> {
            val strings: MutableList<String> = ArrayList()
            val m = QUOTES_PATTERN.matcher(cmd)
            while (m.find()) {
                var token = m.group(1)
                token = if (token.startsWith("'") || token.startsWith("\"")) token.replace(
                    "'",
                    EMPTY_STRING
                ).replace("\"", EMPTY_STRING) else token
                strings.add(token)
            }
            return strings.toTypedArray()
        }

        fun buildRawCommand(cmdLine: String): Command {
            return CommandImpl(cmdLine, splitCmd(cmdLine))
        }

        fun buildRawCommand2(cmds: Array<String>): Command {
            return CommandImpl(cmds[2], cmds)
        }
    }
}