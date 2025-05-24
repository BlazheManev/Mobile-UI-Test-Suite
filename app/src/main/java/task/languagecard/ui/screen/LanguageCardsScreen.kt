package task.languagecard.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import task.languagecard.R
import task.languagecard.ui.component.LanguageCard
import task.languagecard.ui.preview.PreviewCard
import task.languagecard.ui.preview.PreviewData.cards
import task.languagecard.ui.theme.LanguageCardTheme
import task.languagecard.viewmodel.card.CardUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageCardsScreen(
    uiState: CardUiState,
    onCardClick: (Int) -> Unit,
    onCardAppear: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier, topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge
            )
        })
    }) { innerPadding ->
        if (uiState.isLoading) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator(
                    Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                )
            }
        } else {
            val cards = uiState.cards
            val state = rememberPagerState(pageCount = { cards.size })
            HorizontalPager(
                state,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                reverseLayout = true,
                key = { cards[it].id },
            ) { page ->
                val card = uiState.cards[page]
                LanguageCard(
                    card = card,
                    isFirstLanguage = uiState.isFirstLanguage,
                    onCardClick = { onCardClick(card.id) },
                    onAppear = { onCardAppear(card.id) },
                    modifier = Modifier.fillMaxHeight(0.7f)
                )
            }
        }
    }
}

@PreviewCard
@Composable
private fun LanguageCardsScreenPreview() {
    LanguageCardTheme {
        val uiState = CardUiState(
            cards = cards,
        )
        var isFirstLanguage by remember { mutableStateOf(false) }
        LanguageCardsScreen(
            uiState = uiState,
            onCardClick = { isFirstLanguage = !isFirstLanguage },
            onCardAppear = {},
        )
    }
}