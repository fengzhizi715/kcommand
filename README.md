# kcommand

kcommand 是基于 Kotlin 特性实现的，执行 Linux/Windows 命令的库

[![@Tony沈哲 on weibo](https://img.shields.io/badge/weibo-%40Tony%E6%B2%88%E5%93%B2-blue.svg)](http://www.weibo.com/fengzhizi715)
[![License](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://jitpack.io/v/fengzhizi715/kcommand.svg)](https://jitpack.io/#fengzhizi715/kcommand)

# 功能特点：

* 简洁的命令执行方式
* 支持命令执行的超时机制
* 对 sudo 命令提供额外的支持
* 支持函数式
* 支持 CompletableFuture、RxJava 2、RxJava 3、Kotlin Coroutines


# 最新版本

模块|最新版本
---|:-------------:
kcommand-core|[![](https://jitpack.io/v/fengzhizi715/kcommand.svg)](https://jitpack.io/#fengzhizi715/kcommand)
kcommand-rxjava2|[![](https://jitpack.io/v/fengzhizi715/kcommand.svg)](https://jitpack.io/#fengzhizi715/kcommand)
kcommand-rxjava3|[![](https://jitpack.io/v/fengzhizi715/kcommand.svg)](https://jitpack.io/#fengzhizi715/kcommand)
kcommand-coroutines|[![](https://jitpack.io/v/fengzhizi715/kcommand.svg)](https://jitpack.io/#fengzhizi715/kcommand)


# 下载：

将它添加到项目的 root build.gradle 中：

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```groovy
implementation 'com.github.fengzhizi715.kcommand:core:v1.4.0'
```

```groovy
implementation 'com.github.fengzhizi715.kcommand:rxjava2:v1.4.0'
```

```groovy
implementation 'com.github.fengzhizi715.kcommand:rxjava3:v1.4.0'
```

```groovy
implementation 'com.github.fengzhizi715.kcommand:coroutines:v1.4.0'
```

# kcommand 使用：

### 基本用法

首先，需要通过 CommandBuilder 类构建执行的命令，这些命令支持携带参数。

然后，通过 CommandExecutor.execute() 执行命令。

```kotlin
    CommandExecutor.execute(CommandBuilder("ping").addArg("baidu.com").build())
```

更为简洁的写法：

```kotlin
    CommandExecutor.execute("ping baidu.com")
```

或者：

```kotlin
    CommandExecutor.execute{ "ping baidu.com" }
```

CommandExecutor 的 execute() 会返回 ProcessResult 对象。然后通过 ProcessResult 的 getExecutionResult() 获取命令执行的状态。

例如：

```kotlin
    CommandExecutor.executeCmd {
        val list = mutableListOf<String>()
        list.add("sh")
        list.add("-c")

        val psCommand = "ps aux | grep java"

        list.add(psCommand)

        CommandBuilder.buildRawCommand(psCommand, list.toTypedArray())
    }.getExecutionResult().let {
        val commandLine = it.command().string()
        val exitCode = it.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
```

### 命令执行的回调

开发者可以使用 Append 在`回调`中获取命令执行的内容。

```kotlin
fun getPsCmd():Command {
    val list = mutableListOf<String>()
    list.add("sh")
    list.add("-c")

    val psCommand = "ps aux | grep java"

    list.add(psCommand)

    return CommandBuilder.buildRawCommand(psCommand, list.toTypedArray())
}

fun main() {

    val cmd = getPsCmd()

    CommandExecutor.execute(cmd, null, appender = object : Appender {

        override fun appendStdText(text: String) {
            println(text)
        }

        override fun appendErrText(text: String) {
            System.err.println(text)
        }
    }).getExecutionResult().let {
        val commandLine = cmd.string()
        val exitCode = it.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
}
```

### 使用复合命令

通过使用 CommandBuilder.buildCompositeCommand() 构造所需的复合命令，例如管道命令等

```kotlin
    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    CommandExecutor.execute(cmd).getExecutionResult().let {
        val commandLine = cmd.string()
        val exitCode = it.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
```

### 使用 sudo

支持 sudo 命令执行一些 Linux 管理员使用的命令。

通过使用 buildSudoCommand() 方法构建 Command，需要传递 Linux 管理员的密码和执行的命令。

```kotlin
    val cmd = CommandBuilder.buildSudoCommand("xxx","dmidecode")

    CommandExecutor.execute(cmd).getExecutionResult().let {
        val commandLine = cmd.string()
        val exitCode = it.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
```

### 构建 Windows 命令

通过使用 buildWindowsCommand() 方法构建 Command

```kotlin
    val cmd = CommandBuilder.buildWindowsCommand("dir")
```

### 支持 CompletableFuture

通过 ProcessResult 的扩展函数`asCompletableFuture()`等，返回 CompletableFuture 对象

```kotlin
    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    val executionResult = CommandExecutor.execute(cmd).asCompletableFuture().get()

    val commandLine = cmd.string()
    val exitCode = executionResult.exitValue()
    println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
```

### 支持 RxJava 

通过 ProcessResult 的扩展函数`asObservable()`等，支持 RxJava2、3

```kotlin
    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    CommandExecutor.execute(cmd)
        .asObservable()
        .subscribe {

            val commandLine = cmd.string()
            val exitCode = it.exitValue()
            println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
        }
```

或者

```kotlin
    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    val sb = StringBuilder()

    CommandExecutor.executeSync(cmd = cmd,appender = object : Appender{
        override fun appendStdText(text: String) {
            if (text.contains(cmd.string())) {
                sb.append(text)
            }
        }

        override fun appendErrText(text: String) {
            TODO("Not yet implemented")
        }

    }).asObservable {
        sb.toString()
    }.subscribe {
        println(it)
    }
```

### 支持函数式

通过 ProcessResult 的`getResult()`返回的 [Result](https://github.com/fengzhizi715/Result) 支持函数式，
[Result](https://github.com/fengzhizi715/Result) 可以点击查看。

```kotlin
    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    val result = CommandExecutor.execute(cmd).getResult().get()

    if (result is ExecutionResult) {

        val commandLine = cmd.string()
        val exitCode = result.exitValue()
        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
```


### Kotlin Coroutines

通过 ProcessResult 的扩展函数`asFlow()`，返回 Flow 对象

```kotlin
fun main() = runBlocking{

    val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

    CommandExecutor.execute(cmd)
        .asFlow()
        .collect{
            val commandLine = cmd.string()
            val exitCode = it.exitValue()
            println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
        }
}
```

### 同步执行

使用 CommandExecutor.executeSync() 支持`同步`执行。

> 其实 kcommand 底层使用的是线程池，只是等待线程执行完成后将结果同步返回到 Append 。 

executeSync() 方法还支持超时机制，有2个参数分别表示超时的时间、时间的单位。

```kotlin
    val cmd = CommandBuilder("ping").addArg("baidu.com").build()

    CommandExecutor.executeSync(cmd, null,5, TimeUnit.SECONDS,object :Appender{
        override fun appendStdText(text: String) {
            println(text)
        }

        override fun appendErrText(text: String) {
            System.err.println(text)
        }

    }).getExecutionResult().let {

        val commandLine = it.command().string()
        val exitCode = it.exitValue()

        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
```

### 异步超时

通过 getExecutionResult() 方法进行`异步`超时。

> 该方法在调用 CommandExecutor.executeSync() 会无效。

```kotlin
    val cmd = CommandBuilder("ping").addArg("baidu.com").build()

    CommandExecutor.execute(cmd, null).getExecutionResult(5,TimeUnit.SECONDS).let {

        val commandLine = it.command().string()
        val exitCode = it.exitValue()

        println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
    }
```

### 同步返回命令的执行结果(String 格式)

通过 getStringWithSync() 方法会同步返回命令的执行结果。

```kotlin
     val cmd = CommandBuilder.buildCompositeCommand("ps aux | grep java")

     val result = CommandExecutor.getStringWithSync(cmd, appender = object :Appender{
        override fun appendStdText(text: String) {
        }

        override fun appendErrText(text: String) {
            System.err.println(text)
        }
    })

    println(result)
```

> CommandExecutor 有多个类似 getStringWithSync() 的扩展函数。


TODO List：
==
* 升级 Kotlin 版本
* 升级 gradle 版本

联系方式
===

Wechat：fengzhizi715


> Java与Android技术栈：每周更新推送原创技术文章，欢迎扫描下方的公众号二维码并关注，期待与您的共同成长和进步。

![](https://github.com/fengzhizi715/NetDiscovery/blob/master/images/gzh.jpeg)

License
-------

    Copyright (C) 2018 - present, Tony Shen.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
