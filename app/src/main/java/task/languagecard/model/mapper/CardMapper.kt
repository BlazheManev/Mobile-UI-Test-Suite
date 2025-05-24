package task.languagecard.model.mapper

import task.languagecard.model.network.NetworkCard
import task.languagecard.model.ui.UiCard

fun NetworkCard.toUiModel(): UiCard {
    return UiCard(
        id = id,
        wordFirstLang = wordFirstLang,
        sentenceFirstLang = sentenceFirstLang,
        wordSecondLang = wordSecondLang,
        sentenceSecondLang = sentenceSecondLang
    )
}

fun UiCard.toNetworkModel(): NetworkCard {
    return NetworkCard(
        id = id,
        wordFirstLang = wordFirstLang,
        sentenceFirstLang = sentenceFirstLang,
        wordSecondLang = wordSecondLang,
        sentenceSecondLang = sentenceSecondLang
    )
}