package nju.eur3ka

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController
import com.gargoylesoftware.htmlunit.WebClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.jvm.reloadPluginConfig
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.MessageChainBuilder
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import kotlin.io.path.pathString

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
    private val web = WebClient(BrowserVersion.CHROME).apply {
        options.apply {
            isCssEnabled = false
            isJavaScriptEnabled = true
        }
    }
    private lateinit var phyNotice: PhyNotice
    private lateinit var graNotice: GraNotice
    private val json = Json { prettyPrint = true }
    private lateinit var bot: Bot
    private var isTaskRun = true
    private val scope = CoroutineScope(this.coroutineContext)

    override fun onEnable() {
        // 加载配置文件
        reloadPluginConfig(Config)
        // 检查缓存是否存在，不存在则创建
        val cachePath = Paths.get("./cache/NJUPhysicsStork")
        if (!cachePath.toFile().exists()) {
            cachePath.toFile().mkdirs()
        }
        val phyCacheFile = File("${cachePath.pathString}/phy.json")
        val graCacheFile = File("${cachePath.pathString}/gra.json")
        phyNotice = if (phyCacheFile.exists()) {
            json.decodeFromString(phyCacheFile.readText(StandardCharsets.UTF_8))
        } else {
            Utils.getPhyNotice(web)
        }
        graNotice = if (graCacheFile.exists()) {
            json.decodeFromString(graCacheFile.readText(StandardCharsets.UTF_8))
        } else {
            Utils.getGraNotice(web)
        }
        // 保存缓存
        phyCacheFile.writeText(json.encodeToString(Utils.getPhyNotice(web)))
        graCacheFile.writeText(json.encodeToString(Utils.getGraNotice(web)))
        // 获取到机器人实例并添加定时任务
        globalEventChannel().subscribeAlways<BotOnlineEvent> { event ->
            PhyStork.bot = event.bot
            // 启动一个定时任务的协程
            scope.launch {
                logger.info("启动定时任务")
                while (isTaskRun) {
                    delay(Config.period)
                    println("Task[Get New Notice] in $this")
                    val mcb = MessageChainBuilder()
                    Utils.getNewNotice(phyNotice, web)
                        .plus(Utils.getNewNotice(graNotice, web))
                        .map {
                            "[${it.time}] ${it.title} /// ${it.url}\n"
                        }.forEach {
                            mcb.add(it)
                        }
                    val msg = mcb.asMessageChain()
                    Config.groupIdList.forEach {
                        PhyStork.bot.getGroup(it)?.sendMessage(msg)
                    }
                    // 更新缓存
                    phyCacheFile.writeText(json.encodeToString(Utils.getPhyNotice(web)))
                    graCacheFile.writeText(json.encodeToString(Utils.getGraNotice(web)))
                }
            }
        }
    }

    override fun onDisable() {
        isTaskRun = false
        scope.cancel()
        super.onDisable()
    }
}

//功能测试
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