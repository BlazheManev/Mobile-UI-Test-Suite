package task.languagecard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import task.languagecard.ui.screen.LanguageCardsScreen
import task.languagecard.ui.theme.LanguageCardTheme
import task.languagecard.viewmodel.card.CardViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LanguageCardTheme {
                val viewModel = hiltViewModel<CardViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    viewModel.start(context)
                }

                LanguageCardsScreen(
                    uiState = uiState,
                    onCardClick = viewModel::flipCard,
                    onCardAppear = viewModel::playCardAudio,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
