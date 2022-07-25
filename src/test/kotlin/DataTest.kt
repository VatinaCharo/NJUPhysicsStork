import nju.eur3ka.Type.GraduateSchool
import nju.eur3ka.Type.PhysicsSchool
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController
import com.gargoylesoftware.htmlunit.WebClient
import nju.eur3ka.GraNotice
import nju.eur3ka.PhyNotice
import nju.eur3ka.getNoticeList
import kotlin.test.Test

class DataTest {
    @Test
    fun getNewNoticeTest() {
        val web = WebClient(BrowserVersion.CHROME).apply {
            options.apply {
                isCssEnabled = false
                isJavaScriptEnabled = true
            }
            ajaxController = NicelyResynchronizingAjaxController()
        }
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
        GraNotice(
            web.getNoticeList(GraduateSchool.NOTICE_URL, GraduateSchool)
        )
    }
}