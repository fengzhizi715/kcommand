package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.Command
 * @author: Tony Shen
 * @date: 2020-05-19 16:48
 * @since: V1.0  执行命令的接口
 */
interface Command {

    fun executable(): List<String>

    fun string(): String
}