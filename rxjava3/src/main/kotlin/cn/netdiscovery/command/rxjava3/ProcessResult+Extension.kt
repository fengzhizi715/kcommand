package cn.netdiscovery.command.rxjava3

import cn.netdiscovery.command.ExecutionResult
import cn.netdiscovery.command.ProcessResult
import io.reactivex.rxjava3.core.*

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.rxjava3.`ProcessResult+Extension`
 * @author: Tony Shen
 * @date: 2020-05-21 00:07
 * @version: V1.0 <描述当前版本功能>
 */
fun ProcessResult.asObservable(): Observable<ExecutionResult> = Observable.create {
    it.onNext(this.getExecutionResult())
}

fun ProcessResult.asFlowable(): Flowable<ExecutionResult> = Flowable.create({
    it.onNext(this.getExecutionResult())
}, BackpressureStrategy.BUFFER)

fun ProcessResult.asSingle(): Single<ExecutionResult> = Single.create {
    it.onSuccess(this.getExecutionResult())
}

fun ProcessResult.asMaybe(): Maybe<ExecutionResult> = Maybe.create {
    it.onSuccess(this.getExecutionResult())
}