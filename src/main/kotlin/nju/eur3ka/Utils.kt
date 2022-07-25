package nju.eur3ka

import com.gargoylesoftware.htmlunit.WebClient

object Utils {
    fun getPhyNotice(web: WebClient) = PhyNotice(
        web.getNoticeList(Type.PhysicsSchool.CONFERENCE_URL, Type.PhysicsSchool),
        web.getNoticeList(Type.PhysicsSchool.REPORT_URL, Type.PhysicsSchool),
        web.getNoticeList(Type.PhysicsSchool.SALON_URL, Type.PhysicsSchool),
        web.getNoticeList(Type.PhysicsSchool.NEWS_URL, Type.PhysicsSchool),
        web.getNoticeList(Type.PhysicsSchool.ANNOUNCEMENT_URL, Type.PhysicsSchool),
        web.getNoticeList(Type.PhysicsSchool.BRIEFING_URL, Type.PhysicsSchool),
        web.getNoticeList(Type.PhysicsSchool.PUBLICITY_AREA_URL, Type.PhysicsSchool),
        web.getNoticeList(Type.PhysicsSchool.CAMPUS_PUBLICITY_AREA_URL, Type.PhysicsSchool)
    )

    fun getGraNotice(web: WebClient) = GraNotice(
        web.getNoticeList(Type.GraduateSchool.NOTICE_URL, Type.GraduateSchool)
    )

    fun getNewNotice(oldPhyNotice: PhyNotice, web: WebClient): List<Notice> {
        val newPhyNoticeList = getPhyNotice(web).getNoticeList()
        val oldPhyNoticeUrlList = oldPhyNotice.getNoticeList().map { it.url }
        return getNewNotice(oldPhyNoticeUrlList, newPhyNoticeList)

    }

    fun getNewNotice(oldGraNotice: GraNotice, web: WebClient): List<Notice> {
        val newGraNoticeList = getGraNotice(web).noticeList
        val oldGraNoticeUrlList = oldGraNotice.noticeList.map { it.url }
        return getNewNotice(oldGraNoticeUrlList, newGraNoticeList)
    }

    private fun getNewNotice(oldUrlList: List<String>, newNoticeList: List<Notice>) =
        newNoticeList.filter { !oldUrlList.contains(it.url) }.sortedByDescending { it.time }
}