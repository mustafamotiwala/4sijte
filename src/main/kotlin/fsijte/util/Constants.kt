package fsijte.util

import java.time.ZoneId

object Constants {
    private val applicationTimeZone = "Australia/Perth"

    val DEFAULT_APP_TIMEZONE: ZoneId = ZoneId.of(applicationTimeZone)

    val DEFAULT_WEB_PAGE = "index.html"
}