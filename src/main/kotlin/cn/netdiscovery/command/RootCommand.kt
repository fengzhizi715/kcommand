package cn.netdiscovery.command

import cn.netdiscovery.command.domain.CommandResult

/**
 * Created by tony on 2019-04-26.
 */
open class RootCommand(
    private val rootCommand: String,
    private val defaultDirection: String?=null,
    private val defaultTimeout: Int
) : Command() {

    override fun exec(commands: String): CommandResult {
        return execute(rootCommand, commands, defaultDirection, defaultTimeout, null)
    }

    override fun exec(commands: String, callback: OutputCallback): CommandResult {
        return execute(rootCommand, commands, defaultDirection, defaultTimeout, callback)
    }

    override fun exec(commands: String, dir: String): CommandResult {
        return execute(rootCommand, commands, dir, defaultTimeout, null)
    }

    override fun exec(commands: String, dir: String, callback: OutputCallback): CommandResult {
        return execute(rootCommand, commands, dir, defaultTimeout, callback)
    }

    override fun exec(commands: String, timeoutInSeconds: Int): CommandResult {
        return execute(rootCommand, commands, defaultDirection, timeoutInSeconds, null)
    }

    override fun exec(commands: String, timeoutInSeconds: Int, callback: OutputCallback): CommandResult {
        return execute(rootCommand, commands, defaultDirection, timeoutInSeconds, callback)
    }

    override fun exec(commands: String, dir: String, timeoutInSeconds: Int): CommandResult {
        return execute(rootCommand, commands, dir, timeoutInSeconds, null)
    }

    override fun exec(commands: String, dir: String, timeoutInSeconds: Int, callback: OutputCallback): CommandResult {
        return execute(rootCommand, commands, dir, timeoutInSeconds, callback)
    }
}
