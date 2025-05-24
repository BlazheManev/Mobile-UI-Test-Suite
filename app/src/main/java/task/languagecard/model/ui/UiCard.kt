package task.languagecard.model.ui

import androidx.compose.runtime.Immutable

@Immutable
data class UiCard(
    val id: Int,
    val wordFirstLang: String,
    val sentenceFirstLang: String,
    val wordSecondLang: String,
    val sentenceSecondLang: String,
)
