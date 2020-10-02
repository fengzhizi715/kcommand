package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.Appender
 * @author: Tony Shen
 * @date: 2020-05-19 16:50
 * @version: V1.0  执行命令内容的回调
 */
interface Appender {

    fun appendStdText(text: String)

    fun appendErrText(text: String)
}