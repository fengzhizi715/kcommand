package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.Appender
 * @author: Tony Shen
 * @date: 2020-05-19 16:50
 * @version: V1.0 <描述当前版本功能>
 */
interface Appender {

    fun appendStdText(text: String)

    fun appendErrText(text: String)
}