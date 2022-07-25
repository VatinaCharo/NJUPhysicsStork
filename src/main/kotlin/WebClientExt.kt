import Type.GraduateSchool
import Type.PhysicsSchool
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import org.jsoup.Jsoup

fun WebClient.getNoticeList(url: String, type: Type): List<Notice> {
    val page = this.getPage<HtmlPage>(url) ?: throw NetException("Invalid url: $url")
    val doc = Jsoup.parse(page.asXml())
    when (type) {
        PhysicsSchool -> {
            val data = doc.getElementsByClass("mn-list-item") ?: throw NetException("Can't find notice in $url")
            try {
                return data.map {
                    val date = it.getElementsByClass("date").first()!!.run {
                        "${getElementsByClass("year").first()!!.text()}-${getElementsByClass("day").first()!!.text()}"
                    }
                    val urlBean = it.getElementsByClass("info").first()!!
                    Notice(
                        date,
                        urlBean.attr("title"),
                        urlBean.attr("href")
                    )
                }
            } catch (e: NullPointerException) {
                throw NetException("Can't parse notice in $url")
            }
        }
        GraduateSchool -> {
            val data = doc.getElementsByClass("list_item") ?: throw NetException("Can't find notice in $url")
            try {
                return data.map {
                    val urlBean = it.getElementsByTag("a").first()!!
                    Notice(
                        it.getElementsByClass("Article_PublishDate").first()!!.text(),
                        urlBean.attr("title"),
                        GraduateSchool.GRADUATE_URL_PREFIX + urlBean.attr("href")
                    )
                }
            } catch (e: NullPointerException) {
                throw NetException("Can't parse notice in $url")
            }
        }
    }
}
