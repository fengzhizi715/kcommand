package cn.netdiscovery.command

import cn.netdiscovery.command.domain.CommandResult
import cn.netdiscovery.command.domain.CommandStatus
import java.util.*
import java.util.Map
import java.util.concurrent.Executors
import java.util.stream.Collectors

/**
 * Created by tony on 2019-04-26.
 */
class CommandManagerImpl : CommandManager {

    private val taskScheduler = Executors.newFixedThreadPool(10)
    private val command = Command.instance
    private val commandStatuses = LinkedHashMap<String, CommandStatus>()

    private fun doExec(command: String, sync: Boolean, callback: OutputCallback?=null): Any {
        val result: Any
        val uuid = UUID.randomUUID().toString()
        val commandStatus = CommandStatus()
        commandStatus.uuid = uuid
        commandStatus.command = command
        commandStatuses[uuid] = commandStatus
        if (sync) {
            val commandResult = this.command.exec(commandStatus.command!!, Callback(commandStatus, callback))
            assert(!commandResult.isRunning)
            commandStatus.result = commandResult
            result = commandStatus
        } else {
            taskScheduler.execute(CommandRunnable(commandStatus, callback))
            result = uuid
        }
        return result
    }

    override fun exec(command: String): CommandStatus {
        return doExec(command, true, null) as CommandStatus
    }

    override fun exec(command: String, callback: OutputCallback): String {
        return doExec(command, false, callback) as String
    }

    override fun get(): List<CommandStatus> {
        return ArrayList(commandStatuses.values)
    }

    override fun get(uuid: String): Optional<CommandStatus> {
        return Optional.ofNullable(commandStatuses[uuid])
    }

    override fun get(uuids: Set<String>): List<CommandStatus> {

        return commandStatuses.entries
            .stream()
            .filter { stringCommandStatusEntry -> uuids.contains(stringCommandStatusEntry.key) }
            .map{it.value}
            .collect(Collectors.toList<CommandStatus>());
    }

    private inner class CommandRunnable internal constructor(
        private val commandStatus: CommandStatus,
        private val callback: OutputCallback?=null
    ) : Runnable {

        override fun run() {

            commandStatus.result = command.exec(commandStatus.command!!, Callback(commandStatus, callback))
        }
    }

    private inner class Callback constructor(
        val commandStatus: CommandStatus,
        val callback: OutputCallback? = null
    ) : OutputCallback {

        override fun onOutput(commandResult: CommandResult) {
            commandStatus.result = commandResult
            callback?.onOutput(commandResult)
        }
    }
}