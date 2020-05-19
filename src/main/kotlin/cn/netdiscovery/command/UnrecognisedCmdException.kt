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
class UnrecognisedCmdException(cmd: String) : IOException() {

    private val cmd: String

    /**
     * the public constructors that requires the command that caused the error.
     *
     * @param cmd
     */
    init {
        this.cmd = cmd
    }

    override fun toString(): String {
        return "could not recognise $cmd"
    }

}