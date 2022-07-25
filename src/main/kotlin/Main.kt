package nju.eur3ka

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info

object PhyStork : KotlinPlugin(
    JvmPluginDescription(
        id = "nju.eur3ka.phystork",
        name = "PhyStork",
        version = "0.1.0",
    ) {
        author("Eur3ka")
        info("""xxx""")
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }
    }
}