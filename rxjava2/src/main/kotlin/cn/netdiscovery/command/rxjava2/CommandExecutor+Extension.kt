package cn.netdiscovery.command.rxjava2

import cn.netdiscovery.command.Appender
import cn.netdiscovery.command.Command
import cn.netdiscovery.command.CommandExecutor
import cn.netdiscovery.command.ExecutionOutputPrinter
import io.reactivex.*
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.rxjava2.`CommandExecutor+Extension`
 * @author: Tony Shen
 * @date: 2020-10-07 21:54
 * @version: V1.0 <描述当前版本功能>
 */
fun CommandExecutor.getObservableWithSync(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, appender: Appender): Observable<String>
        = getObservableWithSyncOutputPrinter(cmd, directory, timeout, unit, ExecutionOutputPrinter(appender))

fun CommandExecutor.getObservableWithSyncOutputPrinter(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, outputPrinter: ExecutionOutputPrinter = ExecutionOutputPrinter.DEFAULT_OUTPUT_PRINTER): Observable<String> {

    val result = getStingWithSyncOutputPrinter(cmd,directory,timeout,unit,outputPrinter)

    return Observable.create{ it.onNext(result) }
}

fun CommandExecutor.getFlowableWithSync(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, appender: Appender): Flowable<String>
        = getFlowableWithSyncOutputPrinter(cmd, directory, timeout, unit, ExecutionOutputPrinter(appender))

fun CommandExecutor.getFlowableWithSyncOutputPrinter(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, outputPrinter: ExecutionOutputPrinter = ExecutionOutputPrinter.DEFAULT_OUTPUT_PRINTER): Flowable<String> {

    val result = getStingWithSyncOutputPrinter(cmd,directory,timeout,unit,outputPrinter)

    return Flowable.create({
        it.onNext(result)
    }, BackpressureStrategy.BUFFER)
}

fun CommandExecutor.getSingleWithSync(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, appender: Appender): Single<String>
        = getSingleWithSyncOutputPrinter(cmd, directory, timeout, unit, ExecutionOutputPrinter(appender))

fun CommandExecutor.getSingleWithSyncOutputPrinter(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, outputPrinter: ExecutionOutputPrinter = ExecutionOutputPrinter.DEFAULT_OUTPUT_PRINTER): Single<String> {

    val result = getStingWithSyncOutputPrinter(cmd,directory,timeout,unit,outputPrinter)

    return Single.create{ it.onSuccess(result) }
}

fun CommandExecutor.getMaybeWithSync(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, appender: Appender): Maybe<String>
        = getMaybeWithSyncOutputPrinter(cmd, directory, timeout, unit, ExecutionOutputPrinter(appender))

fun CommandExecutor.getMaybeWithSyncOutputPrinter(cmd: Command, directory: File?=null, timeout:Long?=null, unit: TimeUnit?=null, outputPrinter: ExecutionOutputPrinter = ExecutionOutputPrinter.DEFAULT_OUTPUT_PRINTER): Maybe<String> {

    val result = getStingWithSyncOutputPrinter(cmd,directory,timeout,unit,outputPrinter)

    return Maybe.create{ it.onSuccess(result) }
}