package cn.netdiscovery.command.domain

/**
 * Created by tony on 2019-04-26.
 */
data class CommandResult(
    var code: Int = 1,
    var output: String?=null,
    var exception: String?=null,
    var isRunning: Boolean = false
) {

    companion object {

        fun empty(): CommandResult {
            return CommandResult()
        }
    }
}