package cn.netdiscovery.command

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.Ping
 * @author: Tony Shen
 * @date: 2020-05-20 20:52
 * @version: V1.0 <描述当前版本功能>
 */
fun main() {

    val cmd = CommandBuilder("ping").addArg("baidu.com").build()

    try {
        CommandExecutor.execute(cmd, null)
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
}