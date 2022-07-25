package nju.eur3ka

@kotlinx.serialization.Serializable
data class Notice(
    val time: String,
    val title: String,
    val url: String
)

@kotlinx.serialization.Serializable
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
            .asSequence()
            .plus(reportList)
            .plus(salonList)
            .plus(newsList)
            .plus(announcementList)
            .plus(briefingList)
            .plus(publicityAreaList)
            .plus(campusPublicityAreaList)
            .toList()
}

@kotlinx.serialization.Serializable
data class GraNotice(
    val noticeList: List<Notice>
)