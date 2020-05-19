package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.Command
 * @author: Tony Shen
 * @date: 2020-05-19 16:48
 * @version: V1.0 <描述当前版本功能>
 */
interface Command {

    fun executable(): Array<String>

    fun string(): String
}