package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.ExecutionReport
 * @author: Tony Shen
 * @date: 2020-05-19 16:54
 * @version: V1.0 <描述当前版本功能>
 */
interface ExecutionResult {

    fun command(): Command

    /**
     * 0 表示成功，非 0 表示失败
     */
    fun exitValue(): Int

    companion object {

        fun makeReport(cmd: Command, exitValue: Int)= object : ExecutionResult {

            override fun exitValue(): Int = exitValue

            override fun command(): Command = cmd
        }
    }
}