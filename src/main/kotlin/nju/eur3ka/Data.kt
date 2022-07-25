package nju.eur3ka

data class Notice(
    val time: String,
    val title: String,
    val url: String
)

data class PhyNotice(
    val conferenceList: List<Notice>,
    val reportList: List<Notice>,
    val salonList: List<Notice>,
    val newsList: List<Notice>,
    val announcementList: List<Notice>,
    val briefingList: List<Notice>,
    val publicityAreaList: List<Notice>,
    val campusPublicityAreaList: List<Notice>
) {
    fun getNoticeList() =
        conferenceList
            .plus(reportList)
            .plus(salonList)
            .plus(announcementList)
            .plus(briefingList)
            .plus(publicityAreaList)
            .plus(campusPublicityAreaList)
}

data class GraNotice(
    val noticeList: List<Notice>
)