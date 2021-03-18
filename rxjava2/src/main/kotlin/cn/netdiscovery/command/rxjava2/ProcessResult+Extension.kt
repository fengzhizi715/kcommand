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
 * @since: V1.1 <描述当前版本功能>
 */
fun ProcessResult.asObservable(): Observable<ExecutionResult> = Observable.create {
    it.onNext(this.getExecutionResult())
}

fun <T> ProcessResult.asObservable(block:()->T): Observable<T> = Observable.create {
    it.onNext(block())
}

fun ProcessResult.asFlowable(): Flowable<ExecutionResult> = Flowable.create({
    it.onNext(this.getExecutionResult())
}, BackpressureStrategy.BUFFER)

fun <T> ProcessResult.asFlowable(block:()->T): Flowable<T> = Flowable.create({
    it.onNext(block())
}, BackpressureStrategy.BUFFER)

fun ProcessResult.asSingle(): Single<ExecutionResult> = Single.create {
    it.onSuccess(this.getExecutionResult())
}

fun <T> ProcessResult.asSingle(block:()->T): Single<T> = Single.create {
    it.onSuccess(block())
}

fun ProcessResult.asMaybe(): Maybe<ExecutionResult> = Maybe.create {
    it.onSuccess(this.getExecutionResult())
}

fun <T> ProcessResult.asMaybe(block:()->T): Maybe<T> = Maybe.create {
    it.onSuccess(block())
}