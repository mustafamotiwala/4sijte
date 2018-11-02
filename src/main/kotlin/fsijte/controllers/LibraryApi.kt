package fsijte.controllers

import com.typesafe.config.Config
import org.jooby.mvc.GET
import org.jooby.mvc.Path
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Path("/api/library")
class LibraryApi @Inject constructor(private val config: Config) {
    val log = LoggerFactory.getLogger(this.javaClass)

    @GET
    fun getFileDetail():Map<String, Any> {
        log.debug("Command to execute: ${config.getString("app.exif.binary")}")
        return mapOf()
    }

}
