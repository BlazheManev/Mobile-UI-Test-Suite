package task.languagecard.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCard(
    @SerialName("id") val id: Int,
    @SerialName("wordFirstLang") val wordFirstLang: String,
    @SerialName("sentenceFirstLang") val sentenceFirstLang: String,
    @SerialName("wordSecondLang") val wordSecondLang: String,
    @SerialName("sentenceSecondLang") val sentenceSecondLang: String,
)
