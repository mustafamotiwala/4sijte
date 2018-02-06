package fsijte.controllers

import org.jooby.mvc.GET
import org.jooby.mvc.Path
import org.slf4j.LoggerFactory
import java.io.File
import javax.sound.sampled.AudioSystem

@Path("/api/library")
class LibraryApi {
    val log = LoggerFactory.getLogger(this.javaClass)

    //FIXME:: javax.sound.sampled.UnsupportedAudioFileException: file is not a supported file type
    val audioSystem = AudioSystem.getAudioFileFormat(File("/Music/test.mp3"))

    @GET
    fun getFileDetail():Map<String, Any> {
        return audioSystem.properties()
    }

}
