package cn.netdiscovery.command.coroutines

import cn.netdiscovery.command.Appender
import cn.netdiscovery.command.Command
import cn.netdiscovery.command.CommandExecutor
import cn.netdiscovery.command.ExecutionOutputPrinter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.coroutines.`CommandExecutor+Extension`
 * @author: Tony Shen
 * @date: 2020-10-07 22:00
 * @version: V1.0 <描述当前版本功能>
 */
fun CommandExecutor.getFlowWithSync(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, appender: Appender): Flow<String>
        = getFlowWithSyncOutputPrinter(cmd, directory, timeout, unit, ExecutionOutputPrinter(appender))

fun CommandExecutor.getFlowWithSyncOutputPrinter(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, outputPrinter: ExecutionOutputPrinter = ExecutionOutputPrinter.DEFAULT_OUTPUT_PRINTER): Flow<String> {

    val result = getStringWithSyncOutputPrinter(cmd,directory,timeout,unit,outputPrinter)
    return flowOf( result )
}