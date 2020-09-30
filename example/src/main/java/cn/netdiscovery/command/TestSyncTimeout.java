package cn.netdiscovery.command;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @FileName: cn.netdiscovery.command.TestSyncTimeout
 * @author: Tony Shen
 * @date: 2020-09-16 16:31
 * @version: V1.0 <描述当前版本功能>
 */
public class TestSyncTimeout {

    public static void main(String[] args){
        Command cmd = new CommandBuilder("ping").addArg("baidu.com").build();

        CommandExecutor.executeSync(cmd, null, 5L, TimeUnit.SECONDS, new Appender() {
            @Override
            public void appendStdText(@NotNull String text) {

            }

            @Override
            public void appendErrText(@NotNull String text) {

            }
        }).getExecutionResult();
    }
}
