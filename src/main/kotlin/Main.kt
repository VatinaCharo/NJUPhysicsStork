import Type.GraduateSchool
import Type.PhysicsSchool
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController
import com.gargoylesoftware.htmlunit.WebClient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.channels.FileChannel
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

fun main() {
    val web = WebClient(BrowserVersion.CHROME).apply {
        options.apply {
            isCssEnabled = false
            isJavaScriptEnabled = true
        }
        ajaxController = NicelyResynchronizingAjaxController()
    }
    val p =
        PhyNotice(
            web.getNoticeList(PhysicsSchool.CONFERENCE_URL, PhysicsSchool),
            web.getNoticeList(PhysicsSchool.REPORT_URL, PhysicsSchool),
            web.getNoticeList(PhysicsSchool.SALON_URL, PhysicsSchool),
            web.getNoticeList(PhysicsSchool.NEWS_URL, PhysicsSchool),
            web.getNoticeList(PhysicsSchool.ANNOUNCEMENT_URL, PhysicsSchool),
            web.getNoticeList(PhysicsSchool.BRIEFING_URL, PhysicsSchool),
            web.getNoticeList(PhysicsSchool.PUBLICITY_AREA_URL, PhysicsSchool),
            web.getNoticeList(PhysicsSchool.CAMPUS_PUBLICITY_AREA_URL, PhysicsSchool)
        )
    val g =
        GraNotice(
            web.getNoticeList(GraduateSchool.NOTICE_URL, GraduateSchool)
        )
    val json = Json { prettyPrint = true }
    FileChannel.open(Paths.get("phy_notice.json"), StandardOpenOption.CREATE, StandardOpenOption.WRITE).use {
        it.write(StandardCharsets.UTF_8.encode(json.encodeToString(p)))
    }
    FileChannel.open(Paths.get("gra_notice.json"), StandardOpenOption.CREATE, StandardOpenOption.WRITE).use {
        it.write(StandardCharsets.UTF_8.encode(json.encodeToString(g)))
    }
}