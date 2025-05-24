package task.languagecard.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import task.languagecard.model.ui.UiCard
import task.languagecard.ui.preview.PreviewCard
import task.languagecard.ui.preview.PreviewData
import task.languagecard.ui.theme.LanguageCardTheme

@Composable
fun LanguageCard(
    card: UiCard,
    isFirstLanguage: Boolean,
    onCardClick: () -> Unit,
    onAppear: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onAppear()
    }

    val rotation by animateFloatAsState(
        targetValue = if (isFirstLanguage) 180f else 0f, animationSpec = tween(500)
    )

    ElevatedCard(
        onClick = onCardClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .graphicsLayer {
                rotationY = rotation
            }) {
            CardContent(card, isFirstLanguage)
        }
    }
}

@Composable
fun CardContent(card: UiCard, isFirstLanguage: Boolean) {
    val flag = if (isFirstLanguage) "\uD83C\uDDF7\uD83C\uDDFA" else "\uD83C\uDDEB\uD83C\uDDEE"
    val word = if (isFirstLanguage) card.wordFirstLang else card.wordSecondLang
    val sentence = if (isFirstLanguage) card.sentenceFirstLang else card.sentenceSecondLang

    Text(flag, style = MaterialTheme.typography.displayLarge)
    Spacer(Modifier.height(32.dp))
    Text(
        text = word,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.displayMedium
    )
    Spacer(Modifier.height(24.dp))
    Text(
        text = sentence,
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.displaySmall
    )
}

@PreviewCard
@Composable
private fun LanguageCardPreview() {
    LanguageCardTheme {
        val uiCard = PreviewData.cards.first()
        var isFirstLanguage by remember { mutableStateOf(true) }
        LanguageCard(uiCard, isFirstLanguage = isFirstLanguage, onCardClick = {
            isFirstLanguage = !isFirstLanguage
        }, onAppear = {})
    }
}