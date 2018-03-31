package fsijte.controllers

import org.jooby.mvc.GET
import org.jooby.mvc.Path
import org.slf4j.LoggerFactory
import java.io.File

@Path("/api/library")
class LibraryApi {
    val log = LoggerFactory.getLogger(this.javaClass)

    @GET
    fun getFileDetail():Map<String, Any> {
        return mapOf()
    }

}
