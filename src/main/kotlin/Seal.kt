sealed class Type {
    object PhysicsSchool : Type() {
        private const val PHYSICS_URL_PREFIX = "https://physics.nju.edu.cn/xwgg"
        const val CONFERENCE_URL = "$PHYSICS_URL_PREFIX/xshy/index.html"
        const val REPORT_URL = "$PHYSICS_URL_PREFIX/xsbg/index.html"
        const val SALON_URL = "$PHYSICS_URL_PREFIX/qnjssl/index.html"
        const val NEWS_URL = "$PHYSICS_URL_PREFIX/xw/index.html"
        const val ANNOUNCEMENT_URL = "$PHYSICS_URL_PREFIX/gg/index.html"
        const val BRIEFING_URL = "$PHYSICS_URL_PREFIX/jb/index.html"
        const val PUBLICITY_AREA_URL = "$PHYSICS_URL_PREFIX/gsq/index.html"
        const val CAMPUS_PUBLICITY_AREA_URL = "$PHYSICS_URL_PREFIX/xngsq/index.html"
    }

    object GraduateSchool : Type() {
        const val GRADUATE_URL_PREFIX = "https://grawww.nju.edu.cn"
        const val NOTICE_URL = "$GRADUATE_URL_PREFIX/905/list.htm"
    }
}