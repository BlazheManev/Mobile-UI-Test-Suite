# Known Issues

## 🐞 Bug: Card Content Visible While Loading

### Summary
The app displays card content even when `isLoading = true`, which contradicts the expected behavior. During a loading state, only the loading spinner should be visible.

### Reproduced By
UI test `bugTest_tapWhileLoadingDoesNothing` renders the screen in a loading state and attempts to find the word `"Привет!"` on screen. This node **should not** exist, but it does.

### Test:
```kotlin
@Test
fun bugTest_tapWhileLoadingDoesNothing() {
    composeTestRule.setContent {
        LanguageCardsScreen(
            uiState = CardUiState(isLoading = true),
            onCardClick = {},
            onCardAppear = {}
        )
    }

    composeTestRule.onNodeWithText("Привет!").assertExists() // This fails intentionally
}
```
Expected Behavior
When isLoading == true, no card content should be present in the UI tree.

Actual Behavior
The first card (Привет!) is still rendered even though the isLoading flag is set.

Suggested Fix
Make sure that when uiState.isLoading is true, the Composable tree renders only the loading spinner, and card content is excluded entirely.

Status
✅ Intentionally failing test is included for this issue.