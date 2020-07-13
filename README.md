# kcommand

kcommand 是基于 Kotlin 特性实现的执行 Linux 命令的库

[![@Tony沈哲 on weibo](https://img.shields.io/badge/weibo-%40Tony%E6%B2%88%E5%93%B2-blue.svg)](http://www.weibo.com/fengzhizi715)
[![License](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)


# 功能特点：

* 简洁的命令执行工具
* 支持命令执行的超时机制
* 对 sudo 命令提供额外的支持
* 支持函数式
* 支持 RxJava 2、RxJava 3、CompletableFuture、Coroutines


# 最新版本

模块|最新版本
---|:-------------:
kcommand-core|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/kcommand-core/images/download.svg) ](https://bintray.com/fengzhizi715/maven/kcommand-core/_latestVersion)
kcommand-rxjava2|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/kcommand-rxjava2/images/download.svg) ](https://bintray.com/fengzhizi715/maven/kcommand-rxjava2/_latestVersion)
kcommand-rxjava3|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/kcommand-rxjava3/images/download.svg) ](https://bintray.com/fengzhizi715/maven/kcommand-rxjava3/_latestVersion)
kcommand-coroutines|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/kcommand-coroutines/images/download.svg) ](https://bintray.com/fengzhizi715/maven/kcommand-coroutines/_latestVersion)


# 下载：

```groovy
implementation 'cn.netdiscovery.kcommand:kcommand-core:1.2.1'
```

```groovy
implementation 'cn.netdiscovery.kcommand:kcommand-rxjava2:1.2.1'
```

```groovy
implementation 'cn.netdiscovery.kcommand:kcommand-rxjava3:1.2.1'
```

```groovy
implementation 'cn.netdiscovery.kcommand:kcommand-coroutines:1.2.1'
```

# 使用：

### basic

```kotlin
    val cmd = CommandBuilder("ping").addArg("baidu.com").build()

    try {
        CommandExecutor.execute(cmd, null)
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
```

### 返回结果

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

    try {
        CommandExecutor.execute(cmd, null, object : Appender {

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
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
}
```

### 使用 sudo 

```kotlin
    val cmd = CommandBuilder.buildSudoCommand("xxx","dmidecode")

    try {
        CommandExecutor.execute(cmd, null, object : Appender {

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
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
``` 

### 支持 RxJava 

```kotlin
    val list = mutableListOf<String>()
    list.add("sh")
    list.add("-c")

    val psCommand = "ps aux | grep java"

    list.add(psCommand)

    val cmd = CommandBuilder.buildRawCommand(psCommand, list.toTypedArray())

    val eop = ExecutionOutputPrinter(object : Appender {

        override fun appendStdText(text: String) {
            println(text)
        }

        override fun appendErrText(text: String) {
            System.err.println(text)
        }
    })

    try {
        CommandExecutor.execute(cmd, null, eop)
            .asObservable()
            .subscribe {

                val commandLine = cmd.string()
                val exitCode = it.exitValue()
                println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
            }

    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
```

### 支持函数式

```kotlin
    val cmd = getPsCmd()

    try {
        val pResult = CommandExecutor.execute(cmd, null, object : Appender {

            override fun appendStdText(text: String) {
                println(text)
            }

            override fun appendErrText(text: String) {
                System.err.println(text)
            }
        })

        val result = pResult.getResult().get()

        if (result is ExecutionResult) {

            val commandLine = cmd.string()
            val exitCode = result.exitValue()
            println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
        }
    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
```

其中 pResult.getResult() 返回的 [Result](https://github.com/fengzhizi715/kcommand/blob/master/core/src/main/kotlin/cn/netdiscovery/command/function/Result.kt) 可以点击查看


### Coroutines

```kotlin
fun main() = runBlocking{

    val cmd = getPsCmd()

    try {
        CommandExecutor.execute(cmd, null)
            .asFlow()
            .collect{
                val commandLine = cmd.string()
                val exitCode = it.exitValue()
                println("command line: $commandLine\nexecution finished with exit code: $exitCode\n\n")
            }

    } catch (e: UnrecognisedCmdException) {
        System.err.println(e)
    }
}
```

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