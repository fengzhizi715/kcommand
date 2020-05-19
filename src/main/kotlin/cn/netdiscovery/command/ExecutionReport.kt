package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.ExecutionReport
 * @author: Tony Shen
 * @date: 2020-05-19 16:54
 * @version: V1.0 <描述当前版本功能>
 */
interface ExecutionReport {

    fun command(): Command

    fun exitValue(): Int

    companion object {

        fun makeReport(cmd: Command, exitValue: Int): ExecutionReport {
            return object : ExecutionReport {

                override fun exitValue(): Int = exitValue

                override fun command(): Command = cmd
            }
        }
    }
}