package cn.netdiscovery.command.rxjava3

import cn.netdiscovery.command.Appender
import cn.netdiscovery.command.Command
import cn.netdiscovery.command.CommandExecutor
import cn.netdiscovery.command.ExecutionOutputPrinter
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.rxjava3.`CommandExecutor+Extension`
 * @author: Tony Shen
 * @date: 2020-10-07 16:06
 * @version: V1.0 <描述当前版本功能>
 */
fun CommandExecutor.getObservableWithSync(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, appender: Appender): Observable<String> {

    val result = getStingWithSync(cmd,directory,timeout,unit,appender)

    return if (result.isNullOrEmpty()) {
        Observable.empty()
    } else {
        Observable.create { it.onNext(result) }
    }
}

fun CommandExecutor.getObservableWithSyncOutputPrinter(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, outputPrinter: ExecutionOutputPrinter = ExecutionOutputPrinter.DEFAULT_OUTPUT_PRINTER): Observable<String> {

    val result = getStingWithSyncOutputPrinter(cmd,directory,timeout,unit,outputPrinter)

    return if (result.isNullOrEmpty()) {
        Observable.empty()
    } else {
        Observable.create { it.onNext(result) }
    }
}

fun CommandExecutor.getFlowableWithSync(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, appender: Appender): Flowable<String> {

    val result = getStingWithSync(cmd,directory,timeout,unit,appender)

    return if (result.isNullOrEmpty()) {
        Flowable.empty()
    } else {
        Flowable.create({
            it.onNext(result)
        }, BackpressureStrategy.BUFFER)
    }
}

fun CommandExecutor.getFlowableWithSyncOutputPrinter(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, outputPrinter: ExecutionOutputPrinter = ExecutionOutputPrinter.DEFAULT_OUTPUT_PRINTER): Flowable<String> {

    val result = getStingWithSyncOutputPrinter(cmd,directory,timeout,unit,outputPrinter)

    return if (result.isNullOrEmpty()) {
        Flowable.empty()
    } else {
        Flowable.create({
            it.onNext(result)
        }, BackpressureStrategy.BUFFER)
    }
}