package task.languagecard.viewmodel.card

import androidx.compose.runtime.Immutable
import task.languagecard.model.ui.UiCard

@Immutable
data class CardUiState(
    val isLoading: Boolean = false,
    val isFirstLanguage: Boolean = true,
    val cards: List<UiCard> = emptyList(),
)
