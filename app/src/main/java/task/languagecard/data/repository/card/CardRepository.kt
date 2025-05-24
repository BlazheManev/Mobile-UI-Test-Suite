package task.languagecard.data.repository.card

import task.languagecard.model.network.NetworkCard
import java.io.File

interface CardRepository {
    suspend fun getAllCard(): List<NetworkCard>

    suspend fun getCard(id: Int): NetworkCard?

    suspend fun downloadAudio(outputDir: File)

    fun release()
}