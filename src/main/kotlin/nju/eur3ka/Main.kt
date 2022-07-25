package nju.eur3ka

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController
import com.gargoylesoftware.htmlunit.WebClient
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
        info("""定期捕获南京大学物理学院和南京大学研究生院的通知信息，并发送新通知到指定的群""")
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }
    }
}

// 功能测试
fun main() {
    val web = WebClient(BrowserVersion.CHROME).apply {
        options.apply {
            isCssEnabled = false
            isJavaScriptEnabled = true
            ajaxController = NicelyResynchronizingAjaxController()
        }
    }
    val oldPhyNotice = Utils.getPhyNotice(web).run {
        PhyNotice(
            conferenceList.drop(1),
            reportList.drop(1),
            salonList.drop(1),
            newsList.drop(1),
            announcementList.drop(1),
            briefingList.drop(1),
            publicityAreaList.drop(1),
            campusPublicityAreaList.drop(1)
        )
    }
    val oldGraNotice = Utils.getGraNotice(web).run {
        GraNotice(this.noticeList.drop(2))
    }
    println("=== New Notice from Physics School of NJU ===")
    Utils.getNewNotice(oldPhyNotice, web).forEach {
        println(it)
    }
    println("=== New Notice from NJU Graduate School ===")
    Utils.getNewNotice(oldGraNotice, web).forEach {
        println(it)
    }
}