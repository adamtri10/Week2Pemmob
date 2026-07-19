# Walkthrough - Profile and Academic Information Separation

I have refactored the application to separate student profile information and academic data into two distinct screens. This implementation promotes better organization while strictly adhering to the existing Material 3 design system and architecture.

## Changes Made

### Core Logic & Components
- **[NEW] [AcademicComponents.kt](file:///C:/Users/A485/Mobile/ProfilMahasiswa/app/src/main/java/com/example/profilmahasiswa/components/AcademicComponents.kt)**
    - Extracted `AcademicStatsCard` and `StatItem` into a shared file to allow reuse across multiple screens without code duplication.
- **[MODIFY] [ProfileViewModel.kt](file:///C:/Users/A485/Mobile/ProfilMahasiswa/app/src/main/java/com/example/profilmahasiswa/ProfileViewModel.kt)**
    - Added `MataKuliah` data class.
    - Initialized academic data including IPK (3.75), Total SKS (120), and a list of 6 sample subjects with their respective grades and SKS.

### Navigation & Screens
- **[MODIFY] [MainActivity.kt](file:///C:/Users/A485/Mobile/ProfilMahasiswa/app/src/main/java/com/example/profilmahasiswa/MainActivity.kt)**
    - Expanded navigation logic to support the new `"nilai"` (Academic Data) screen.
- **[MODIFY] [ProfileScreen.kt](file:///C:/Users/A485/Mobile/ProfilMahasiswa/app/src/main/java/com/example/profilmahasiswa/screens/ProfileScreen.kt)**
    - Cleaned up the profile screen by removing the academic statistics section.
    - Added a prominent **"Lihat Data Nilai"** card that navigates users to the academic screen.
- **[NEW] [DataNilaiScreen.kt](file:///C:/Users/A485/Mobile/ProfilMahasiswa/app/src/main/java/com/example/profilmahasiswa/screens/DataNilaiScreen.kt)**
    - Created a comprehensive academic information screen featuring:
        - **Header**: Displays student name and NIM.
        - **Academic Statistics**: Reuses the `AcademicStatsCard`.
        - **Grade List**: Uses `LazyColumn` for efficient rendering of subjects.
        - **Summary Footer**: Provides totals for subjects, SKS, and cumulative IPK.
        - **Navigation**: Includes a TopAppBar with a back button to return to the Profile.

## Verification

### Automated Tests
- Ran `./gradlew :app:compileDebugKotlin` successfully to ensure no regressions or compilation errors.

### Manual Verification
1. **Launch App**: Verify the Profile screen shows only personal/contact info and the new "Lihat Data Nilai" button.
2. **Navigate to Grades**: Tap "Lihat Data Nilai". Verify navigation works.
3. **Verify Grades**: On the new screen, check that all 6 subjects are listed correctly with their codes, SKS, and scores.
4. **Check Summary**: Verify the bottom card correctly summarizes the number of subjects and total SKS.
5. **Back Navigation**: Use the back arrow to return to the Profile screen.
