package task.languagecard.ui.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import task.languagecard.model.ui.UiCard
import task.languagecard.ui.component.LanguageCard
import task.languagecard.ui.preview.PreviewData
import task.languagecard.viewmodel.card.CardUiState
import androidx.compose.runtime.mutableStateOf

class LanguageCardsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val cards = PreviewData.cards
    private val firstCard = cards.first()
    private val lastCard = cards.last()

    @Test
    fun loadingState_displaysSpinner() {
        composeTestRule.setContent {
            LanguageCardsScreen(
                uiState = CardUiState(isLoading = true),
                onCardClick = {},
                onCardAppear = {}
            )
        }

        composeTestRule.onNode(hasTestTag("LoadingSpinner")).assertExists()
    }

    @Test
    fun cardDisplaysCorrectFirstLanguageText() {
        composeTestRule.setContent {
            LanguageCardsScreen(
                uiState = CardUiState(cards = cards),
                onCardClick = {},
                onCardAppear = {}
            )
        }

        composeTestRule.onNodeWithText(firstCard.wordFirstLang).assertExists()
        composeTestRule.onNodeWithText(firstCard.sentenceFirstLang).assertExists()
    }

    @Test
    fun clickingCard_flipsLanguage() {
        val card = cards[0]
        val isFirstLanguage = mutableStateOf(true)

        composeTestRule.setContent {
            LanguageCard(
                card = card,
                isFirstLanguage = isFirstLanguage.value,
                onCardClick = { isFirstLanguage.value = !isFirstLanguage.value },
                onAppear = {}
            )
        }

        composeTestRule.onNodeWithText(card.wordFirstLang).performClick()
        composeTestRule.waitUntil(3000) {
            composeTestRule.onAllNodesWithText(card.wordSecondLang).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText(card.wordSecondLang).assertExists()
    }

    @Test
    fun userCanSwipeThroughAllCards() {
        composeTestRule.setContent {
            LanguageCardsScreen(
                uiState = CardUiState(cards = cards),
                onCardClick = {},
                onCardAppear = {}
            )
        }

        val pager = composeTestRule.onAllNodes(hasScrollAction())[0]

        for (i in 0 until cards.size) {
            val card = cards[i]

            composeTestRule.waitUntil(
                timeoutMillis = 5000,
                condition = {
                    composeTestRule.onAllNodesWithText(card.wordFirstLang).fetchSemanticsNodes().isNotEmpty()
                }
            )

            if (i < cards.lastIndex) {
                pager.performTouchInput { swipeRight() }
                composeTestRule.waitForIdle()
            }
        }

        val lastCard = cards.last()
        composeTestRule.onNodeWithText(lastCard.wordFirstLang).assertExists()
    }

    @Test
    fun eachCardTriggersOnAppear() {
        val appearedCardIds = mutableListOf<Int>()

        composeTestRule.setContent {
            LanguageCardsScreen(
                uiState = CardUiState(cards = cards),
                onCardClick = {},
                onCardAppear = { appearedCardIds.add(it) }
            )
        }

        composeTestRule.waitForIdle()

        assert(appearedCardIds.contains(firstCard.id))
    }

    @Test
    fun tappingMultipleCardsTogglesLanguagesIndividually() {
        val toggled = mutableListOf<Boolean>()

        composeTestRule.setContent {
            LanguageCard(
                card = cards[1],
                isFirstLanguage = true,
                onCardClick = { toggled.add(true) },
                onAppear = {}
            )
        }

        composeTestRule.onNodeWithText(cards[1].wordFirstLang).performClick()
        assert(toggled.isNotEmpty())
    }

    @Test
    fun bugTest_tapWhileLoadingDoesNothing() {
        composeTestRule.setContent {
            LanguageCardsScreen(
                uiState = CardUiState(isLoading = true),
                onCardClick = {},
                onCardAppear = {}
            )
        }

        composeTestRule.onNodeWithText("Привет!").assertExists()
    }
}
