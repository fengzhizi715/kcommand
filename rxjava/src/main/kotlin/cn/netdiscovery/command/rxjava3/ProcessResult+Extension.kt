package cn.netdiscovery.command.rxjava3

import cn.netdiscovery.command.ExecutionResult
import cn.netdiscovery.command.ProcessResult
import io.reactivex.rxjava3.core.Observable

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.rxjava3.`ProcessResult+Extension`
 * @author: Tony Shen
 * @date: 2020-05-21 00:07
 * @version: V1.0 <描述当前版本功能>
 */
fun ProcessResult.getObservable():Observable<ExecutionResult> = Observable.create<ExecutionResult> {
    it.onNext(this.getExecutionResult())
}