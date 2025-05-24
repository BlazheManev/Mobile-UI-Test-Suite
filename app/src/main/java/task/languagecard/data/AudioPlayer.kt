package task.languagecard.data

import android.content.Context
import android.media.MediaPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

const val audiDirectory = "audio"

class AudioPlayer @Inject constructor(@ApplicationContext private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playAudio(fileName: String, secondFileName: String? = null) {
        val file = File(context.filesDir, "$audiDirectory/$fileName.mp3")
        if (file.exists()) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(file.absolutePath)
                prepare()
                start()

                // if there is a second file plays it after the first one is done
                secondFileName?.let { secondAudio ->
                    setOnCompletionListener {
                        playAudio(secondAudio)
                    }
                }
            }
        }
    }

    fun stopAudio() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            mediaPlayer = null
        }
    }

    fun release() {
        mediaPlayer?.release()
    }
}