package cn.netdiscovery.command

import cn.netdiscovery.command.domain.CommandResult

/**
 * Created by tony on 2019-04-26.
 */
interface OutputCallback {

    /**
     * called when new output come
     *
     * @param commandResult commandResult
     */
    fun onOutput(commandResult: CommandResult)
}