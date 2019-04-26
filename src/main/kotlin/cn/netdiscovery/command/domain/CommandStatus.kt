package cn.netdiscovery.command.domain

/**
 * Created by tony on 2019-04-26.
 */
data class CommandStatus(
    var uuid: String? = null,
    var command: String? = null,
    var result: CommandResult? = null
)