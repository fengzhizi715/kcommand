package cn.netdiscovery.command

import java.util.*
import java.util.regex.Pattern

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.CommandBuilder
 * @author: Tony Shen
 * @date: 2020-05-19 17:03
 * @since: V1.0  构建命令的 Builder 类
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

    private fun finalCmdList(): ArrayList<String> = finalCommand.apply {

        clear()
        add(commandLine)
        addAll(cmdOptions)
        addAll(cmdArgs)
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

    /**
     * Command 的实现类
     * cmdLine 是表面是执行的命令，实际是执行的是 executableCmd
     */
    private class CommandImpl(private val cmdLine: String, private val executableCmd: Array<String>) : Command {

        override fun executable(): List<String> = executableCmd.asList()

        override fun string(): String = cmdLine

        override fun toString(): String = string()
    }

    companion object {

        private const val EMPTY_STRING = ""
        private val QUOTES_PATTERN = Pattern.compile("([^\"|^']\\S*|[\"|'].+?[\"|'])\\s*")

        private fun splitCmd(cmd: String): Array<String> {
            val strings: MutableList<String> = ArrayList()
            val m = QUOTES_PATTERN.matcher(cmd)
            while (m.find()) {
                var token = m.group(1)
                token = if (token.startsWith("'") || token.startsWith("\"")) token.replace("'", EMPTY_STRING).replace("\"", EMPTY_STRING) else token
                strings.add(token)
            }
            return strings.toTypedArray()
        }

        /**
         * 构建原始的命令
         */
        @JvmStatic
        fun buildRawCommand(cmdLine: String): Command = buildRawCommand(cmdLine, splitCmd(cmdLine))

        /**
         * 构建原始的命令
         */
        @JvmStatic
        fun buildRawCommand(cmd: cmdFunction): Command {
            val cmdLine = cmd.invoke()
            return buildRawCommand(cmdLine, splitCmd(cmdLine))
        }

        /**
         * 构建原始的命令
         */
        @JvmStatic
        fun buildRawCommand(cmdLine: String, cmdArray: Array<String>): Command = CommandImpl(cmdLine, cmdArray)

        /**
         * 构建使用管理员账号执行该命令
         */
        @JvmOverloads
        @JvmStatic
        fun buildSudoCommand(password:String = "", cmdLine:String): Command = buildRawCommand(cmdLine, sudoCommandArray(password,cmdLine))

        /**
         * 构建使用管理员账号执行该命令
         */
        @JvmOverloads
        @JvmStatic
        fun buildSudoCommand(password:String = "",cmd:cmdFunction): Command {
            val cmdLine = cmd.invoke()
            return buildRawCommand(cmdLine, sudoCommandArray(password,cmdLine))
        }

        /**
         * 便于构建一些复杂的 Linux 操作命令，例如使用管道命令
         */
        @JvmStatic
        fun buildCompositeCommand(cmdLine: String): Command = buildRawCommand(cmdLine, compositeCommandArray(cmdLine))

        /**
         * 便于构建一些复杂的 Linux 操作命令，例如使用管道命令
         */
        @JvmStatic
        fun buildCompositeCommand(cmd:cmdFunction): Command {
            val cmdLine = cmd.invoke()
            return buildRawCommand(cmdLine, compositeCommandArray(cmdLine))
        }

        /**
         * 构建 Windows 命令
         */
        @JvmStatic
        fun buildWindowsCommand(cmdLine: String): Command = buildRawCommand(cmdLine, windowsCommandArray(cmdLine))

        /**
         * 构建 Windows 命令
         */
        @JvmStatic
        fun buildWindowsCommand(cmd:cmdFunction): Command {
            val cmdLine = cmd.invoke()
            return buildRawCommand(cmdLine, windowsCommandArray(cmdLine))
        }

        private fun sudoCommandArray(password: String,cmdLine: String) =  mutableListOf<String>().apply {
            add("sh")
            add("-c")
            add("echo $password | sudo -S $cmdLine")
        }.toTypedArray()

        private fun compositeCommandArray(cmdLine: String) =  mutableListOf<String>().apply {
            add("sh")
            add("-c")
            add(cmdLine)
        }.toTypedArray()

        private fun windowsCommandArray(cmdLine: String) =  mutableListOf<String>().apply {
            add("cmd.exe")
            add("/c")
            add(cmdLine)
        }.toTypedArray()
    }
}