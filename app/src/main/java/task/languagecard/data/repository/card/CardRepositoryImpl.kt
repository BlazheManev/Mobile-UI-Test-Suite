package task.languagecard.data.repository.card

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.readRawBytes
import io.ktor.utils.io.jvm.javaio.toInputStream
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import task.languagecard.di.IoDispatcher
import task.languagecard.model.network.NetworkCard
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipInputStream
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient, @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CardRepository {
    override suspend fun getAllCard(): List<NetworkCard> {
        return withContext(ioDispatcher) {
            val response = httpClient.get("cards/curated_platform_cards/sm1_new_kap1.json")
            val jsonString = response.readRawBytes().decodeToString()

            return@withContext Json.decodeFromString(jsonString)
        }
    }

    override suspend fun getCard(id: Int): NetworkCard? {
        return withContext(ioDispatcher) {
            val list = getAllCard()
            return@withContext list.find { it.id == id }
        }
    }

    override suspend fun downloadAudio(outputDir: File) {
        withContext(ioDispatcher) {
            if (outputDir.listFiles()?.isEmpty() == true) {
                val response = httpClient.get("audio/curated_platform_audios/sm1_new_kap1.zip")
                val channel = response.bodyAsChannel()
                val inputStream = channel.toInputStream()

                ZipInputStream(inputStream).use { zipInputStream ->
                    var entry = zipInputStream.nextEntry
                    while (entry != null) {
                        val filePath = "$outputDir/${entry.name}"
                        if (entry.isDirectory) {
                            File(filePath).mkdirs()
                        } else {
                            FileOutputStream(filePath).use {
                                zipInputStream.copyTo(it)
                            }
                        }
                        zipInputStream.closeEntry()
                        entry = zipInputStream.nextEntry
                    }
                }
            }
        }
    }

    override fun release() {
        httpClient.close()
    }
}