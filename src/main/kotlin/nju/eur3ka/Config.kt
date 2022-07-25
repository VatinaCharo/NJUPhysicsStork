package nju.eur3ka

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit

object Config : AutoSavePluginConfig("config") {
    val groupIdList: List<Long> by value(listOf(1234567890))
    val period: Long by value(1.0.hours.toLong(DurationUnit.MILLISECONDS))
}