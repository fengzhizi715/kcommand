package cn.netdiscovery.command

import cn.netdiscovery.command.domain.CommandResult

/**
 * Created by tony on 2019-04-26.
 */
interface Command {

    fun exec(commands: String): CommandResult

    fun exec(commands: String, callback: OutputCallback): CommandResult

    fun exec(commands: String, dir: String): CommandResult

    fun exec(commands: String, dir: String, callback: OutputCallback): CommandResult

    fun exec(commands: String, timeoutInSeconds: Int): CommandResult

    fun exec(commands: String, timeoutInSeconds: Int, callback: OutputCallback): CommandResult

    fun exec(commands: String, dir: String, timeoutInSeconds: Int): CommandResult

    fun exec(commands: String, dir: String, timeoutInSeconds: Int, callback: OutputCallback): CommandResult
}
