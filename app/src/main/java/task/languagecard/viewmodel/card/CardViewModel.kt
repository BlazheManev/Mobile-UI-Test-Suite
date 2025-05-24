package task.languagecard.viewmodel.card

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import task.languagecard.data.AudioPlayer
import task.languagecard.data.audiDirectory
import task.languagecard.data.repository.card.CardRepository
import task.languagecard.model.mapper.toUiModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: CardRepository, private val audioPlayer: AudioPlayer
) : ViewModel() {
    private val _uiState = MutableStateFlow(CardUiState())
    val uiState = _uiState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        repository.release()
        audioPlayer.release()
    }

    fun start(context: Context) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val outputDir = File(context.filesDir, audiDirectory)
            if (!outputDir.exists()) {
                outputDir.mkdirs()
            }
            repository.downloadAudio(outputDir)

            _uiState.update { state ->
                state.copy(
                    cards = repository.getAllCard().map { it.toUiModel() }, isLoading = false
                )
            }
        }
    }


    fun flipCard(cardId: Int) {
        _uiState.update {
            it.copy(isFirstLanguage = !it.isFirstLanguage)
        }
        playCardAudio(cardId)
    }

    fun playCardAudio(cardId: Int) {
        val isFirstLanguage = uiState.value.isFirstLanguage
        val wordAudioFile = "${cardId}_w_${if (isFirstLanguage) "ru" else "fi"}"
        val sentenceAudioFile = "${cardId}_s_${if (isFirstLanguage) "ru" else "fi"}"

        // if the previous card audio is still playing it stops it
        audioPlayer.stopAudio()
        audioPlayer.playAudio(wordAudioFile, sentenceAudioFile)
    }
}