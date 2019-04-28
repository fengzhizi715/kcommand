package cn.netdiscovery.command.pipeline

import cn.netdiscovery.command.CommandManager
import cn.netdiscovery.command.CommandManagerImpl
import java.util.*

/**
 * Created by tony on 2019-04-28.
 */
class CommandPipeline @JvmOverloads constructor(val commandManager: CommandManager = CommandManagerImpl()) {

    private val pipelineItems = ArrayList<PipelineItem>()

    fun add(item: PipelineItem): CommandPipeline {
        pipelineItems.add(item)
        return this
    }

    fun exec() {

        val orderedPipelineItems = pipelineItems.groupBy { it.order }
            .entries
            .sortedBy {
                it.key
            }

        // print order
        orderedPipelineItems.forEach {
            val order = it.key
            val output = StringBuilder(">>> Order $order, [")
            for (item in it.value) {
                output.append(item.name).append(", ")
            }
            output.delete(output.length - 2, output.length)
                .append("]\n")
            println(output)
        }

        // execute
        orderedPipelineItems.forEach {
            println(">>>----------------------------------------")
            val order = it.key
            println("executing order: $order")
            it.value.forEach { pipelineItem ->
                val commandStatus = commandManager.exec(pipelineItem.command!!)
                if (commandStatus.command != null) {
                    println("command: " + commandStatus.command)
                    println("output:")
                    println(commandStatus.result?.output)
                }
            }
            println("----------------------------------------<<<")
        }
    }
}