# kcommand

kcommand 是基于 Kotlin 特性实现的执行 Linux 命令的库

# 功能特点：

* 简洁的命令执行工具
* 支持函数式
* 支持 RxJava 2、RxJava 3、CompletableFuture
* 支持命令执行的超时机制

# 下载：

```groovy
implementation 'cn.netdiscovery.kcommand:kcommand-core:1.1.1'
```

```groovy
implementation 'cn.netdiscovery.kcommand:kcommand-rxjava2:1.1.1'
```

```groovy
implementation 'cn.netdiscovery.kcommand:kcommand-rxjava3:1.1.1'
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

其中 pResult.getResult() 返回的 [Result](https://github.com/fengzhizi715/kcommand/blob/master/core/src/main/kotlin/cn/netdiscovery/command/fuction/Result.kt) 可以点击查看
