package cn.netdiscovery.command.coroutines

import cn.netdiscovery.command.ExecutionResult
import cn.netdiscovery.command.ProcessResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.coroutines.`ProcessResult+Extension`
 * @author: Tony Shen
 * @date: 2020-06-13 23:04
 * @version: V1.1 <描述当前版本功能>
 */
fun ProcessResult.asFlow():Flow<ExecutionResult> = flowOf(this.getExecutionResult())