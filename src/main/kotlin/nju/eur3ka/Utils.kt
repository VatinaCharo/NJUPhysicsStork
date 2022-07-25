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

    fun getNewNotice(oldPhyNotice: PhyNotice, web: WebClient) =
        getPhyNotice(web).getNoticeList().minus(oldPhyNotice.getNoticeList().toSet()).sortedByDescending { it.time }

    fun getNewNotice(oldGraNotice: GraNotice, web: WebClient) =
        getGraNotice(web).noticeList.minus(oldGraNotice.noticeList.toSet()).sortedByDescending { it.time }
}