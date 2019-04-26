package cn.netdiscovery.command

import cn.netdiscovery.command.domain.CommandStatus
import java.util.*

/**
 * Created by tony on 2019-04-26.
 */
interface CommandManager {

    /**
     * exec a command synchronously
     *
     * @param command command to exec
     * @return CommandStatus
     */
    fun exec(command: String): CommandStatus

    /**
     * exec a command asynchronously
     *
     * @param command  command to exec
     * @param callback OutputCallback
     * @return uuid for the command
     */
    fun exec(command: String, callback: OutputCallback): String

    /**
     * get all commands
     *
     * @return commands
     */
    fun get(): List<CommandStatus>

    /**
     * get a special command
     *
     * @param uuid uuid for the command
     * @return command
     */
    operator fun get(uuid: String): Optional<CommandStatus>

    /**
     * get special commands
     *
     * @param uuids uuids of commands
     * @return commands
     */
    abstract operator fun get(uuids: Set<String>): List<CommandStatus>
}