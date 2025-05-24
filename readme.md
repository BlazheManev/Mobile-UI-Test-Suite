<div style="text-align: center">
  <h1>Language Card Android App</h1>
</div>

# Requirements:

For this evaluation, the main requirements are:

1. **Single Screen:**
    - Display a card with a word and a sentence on the front.
    - When the card is tapped, it flips to show the translation on the back.
    - Audio plays automatically when the card appears.
    - Swiping left navigates to the next card.

2. **Content Loading:**
    - Card contents should be downloaded at the app start from the
      following [URL](https://pecto-content-f2egcwgbcvbkbye6.z03.azurefd.net/language-data/language-data/russian-finnish/cards/curated_platform_cards/sm1_new_kap1.json)
    - Audio files should be downloaded at the app start from the
      following [ZIP](https://pecto-content-f2egcwgbcvbkbye6.z03.azurefd.net/language-data/language-data/russian-finnish/audio/curated_platform_audios/sm1_new_kap1.zip)
3. **Deliverables:**
    - A GitHub repository containing the implementation
    - A README.md file with:
        - A brief description of the app.
        - Step-by-step instructions on how to launch the app using Android Studio
          on a simulator or a real device.

# Approach:

This app is built with **Jetpack Compose** and displays various cards containing a _word_ and a
_sentence_ in two
languages:
_russian_ and _finnish_.  
The first one is on the _front_ of card while the second one is on the _back_, by pressing the card
it flips and display the other side.

Also, once the card is displayed, two **audio tracks** are played one for the _word_ and the other
one for the
_sentence_.

# How to build:

A more detailed and update version is available on
this [site](https://developer.android.com/studio/run).

The easiest way to run this application is to:

- Install Android Studio: https://developer.android.com/studio/install
- Clone this repository. You have two options:
    - Use the `Project from version control` in Android Studio, or
    - Use the `git clone` command and import it into Android Studio
- Build and run the app directly in Android Studio by pressing the **Run** button.

Alternatively, it is possible to build the app directly from the **command line**; you will
first need to install the _command line_ tools from the official
[documentation](https://developer.android.com/studio#cmdline-tools).  
Then, go to the app’s root directory in the _command line_ tool of Android Studio and run:

```
./gradlew installDebug
```

Once this command completes, the application will be **installed** on the selected device ready to
be run.

# 🧪 Automated UI Testing

This project includes a complete **Jetpack Compose UI test suite** to verify the most important app behavior.

## ✅ Covered Scenarios

- ✅ Shows loading spinner when data is loading
- ✅ Displays correct text on the initial card
- ✅ Tapping a card flips to the translated side
- ✅ Swiping through all cards works as expected
- ✅ `onAppear()` triggers when a card becomes visible
- ✅ Tapping triggers language toggle
- ❌ **Intentional failing test** included (see below)

## 🐞 Known Bug (Intentional Test Failure)

The test `bugTest_tapWhileLoadingDoesNothing` is expected to fail.

> It asserts that card text should not be visible while loading, but the card still renders.  
> This reflects an actual bug with how `isLoading` is implemented in the current UI.

Bug is documented in [`issues.md`](./issues.md)

## 🔁 Swipe Logic (Important)

The app uses:

reverseLayout = true
This means:

swipeRight() = move forward (next card)

swipeLeft() = move backward

All tests are designed to follow this logic to avoid false test failures.
his means:

- swipeRight() = move forward (next card)
- swipeLeft() = move backward

All tests are designed to follow this logic to avoid false test failures.

## 📦 How to Run Tests
To run all UI tests via the command line:

```
./gradlew connectedCheck
```
To run them in Android Studio:

- Open LanguageCardsScreenTest.kt
- Right-click the class or method
- Select Run
## CI Test Workflow
This project uses GitHub Actions to automatically run Jetpack Compose UI tests.

🔄 When does it run?
✅ On every push to main

✅ On every pull request to main

✅ ✅ Can also be triggered manually via the GitHub UI

▶️ How to run tests manually
Go to the Actions tab in the GitHub repository.

Select “Android UI Tests” from the workflow list.

Click “Run workflow” in the top-right corner.

Confirm to run — no need to push or open a PR.

