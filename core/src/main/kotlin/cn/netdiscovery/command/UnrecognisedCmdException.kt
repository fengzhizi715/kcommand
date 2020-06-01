package cn.netdiscovery.command

import java.io.IOException

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.UnrecognisedCmdException
 * @author: Tony Shen
 * @date: 2020-05-19 17:49
 * @version: V1.0 <描述当前版本功能>
 */
class UnrecognisedCmdException(private val cmd: String) : IOException() {

    override fun toString(): String = "could not recognise $cmd"
}