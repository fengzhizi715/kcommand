package cn.netdiscovery.command.rxjava2

import cn.netdiscovery.command.ExecutionResult
import cn.netdiscovery.command.ProcessResult
import io.reactivex.*

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.rxjava3.`ProcessResult+Extension`
 * @author: Tony Shen
 * @date: 2020-05-21 00:07
 * @version: V1.0 <描述当前版本功能>
 */
fun ProcessResult.asObservable(): Observable<ExecutionResult> = Observable.create {
    this.getExecutionResult()?.run {
        it.onNext(this)
    }
}

fun ProcessResult.asFlowable(): Flowable<ExecutionResult> = Flowable.create({
    this.getExecutionResult()?.run {
        it.onNext(this)
    }
}, BackpressureStrategy.BUFFER)

fun ProcessResult.asSingle(): Single<ExecutionResult> = Single.create {
    this.getExecutionResult()?.run {
        it.onSuccess(this)
    }
}

fun ProcessResult.asMaybe(): Maybe<ExecutionResult> = Maybe.create {
    this.getExecutionResult()?.run {
        it.onSuccess(this)
    }
}