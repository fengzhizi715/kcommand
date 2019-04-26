package cn.netdiscovery.command

/**
 * Created by tony on 2019-04-26.
 */
class LinuxCommand : RootCommand("/bin/bash", "/bin", -1)

class WindowsCommand : RootCommand("cmd", null,-1)
