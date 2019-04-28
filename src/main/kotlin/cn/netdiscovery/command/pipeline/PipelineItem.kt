package cn.netdiscovery.command.pipeline

/**
 * Created by tony on 2019-04-28.
 */
class PipelineItem @JvmOverloads constructor(var name: String?, val order: Int, var command: String? = null)