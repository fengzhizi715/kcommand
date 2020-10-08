package cn.netdiscovery.command

import java.io.IOException

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.UnrecognisedCmdException
 * @author: Tony Shen
 * @date: 2020-05-19 17:49
 * @since: V1.0 封装的异常类
 */
class UnrecognisedCmdException(private val cmd: String) : IOException() {

    override fun toString(): String = "could not recognise $cmd"
}