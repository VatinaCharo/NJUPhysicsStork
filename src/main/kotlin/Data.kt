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
)

data class GraNotice(
    val noticeList: List<Notice>
)