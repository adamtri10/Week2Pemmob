# Implementation Plan - Refactor Profile and Academic Information

Separate the Profile screen from the Academic Information (Grades) screen while maintaining the existing design and architecture.

## Proposed Changes

### [Component: Data & Models]

#### [MODIFY] [ProfileViewModel.kt](file:///C:/Users/A485/Mobile/ProfilMahasiswa/app/src/main/java/com/example/profilmahasiswa/ProfileViewModel.kt)
- Define `MataKuliah` data class.
- Add a list of `MataKuliah` (Daftar Nilai) with the provided sample data.
- Keep existing profile data.

### [Component: Shared UI]

#### [NEW] [AcademicComponents.kt](file:///C:/Users/A485/Mobile/ProfilMahasiswa/app/src/main/java/com/example/profilmahasiswa/components/AcademicComponents.kt)
- Move `AcademicStatsCard` and `StatItem` from `ProfileScreen.kt` to this file for reuse.

### [Component: Screens]

#### [MODIFY] [ProfileScreen.kt](file:///C:/Users/A485/Mobile/ProfilMahasiswa/app/src/main/java/com/example/profilmahasiswa/screens/ProfileScreen.kt)
- Remove the `AcademicStatsCard` from the layout.
- Add a new "Lihat Data Nilai" card or button at the bottom that navigates to the academic screen.

#### [NEW] [DataNilaiScreen.kt](file:///C:/Users/A485/Mobile/ProfilMahasiswa/app/src/main/java/com/example/profilmahasiswa/screens/DataNilaiScreen.kt)
- Implement the academic information screen:
    - **Header**: Title "Data Nilai Mahasiswa", Student Name, and NIM.
    - **Academic Statistics**: Reuse `AcademicStatsCard`.
    - **Daftar Nilai**: Use `LazyColumn` to display a list of subjects with their details (Kode, Name, SKS, Score, Grade).
    - **Footer**: Display "Jumlah Mata Kuliah", "Total SKS", and "IPK".
    - **Navigation**: Include a back button to return to the Profile screen.

### [Component: Navigation]

#### [MODIFY] [MainActivity.kt](file:///C:/Users/A485/Mobile/ProfilMahasiswa/app/src/main/java/com/example/profilmahasiswa/MainActivity.kt)
- Update the navigation state to include a `"nilai"` screen.
- Integrate `DataNilaiScreen` into the navigation logic.

## Verification Plan

### Automated Tests
- Build the project using `./gradlew assembleDebug` to ensure no compilation errors.

### Manual Verification
1. Open the app; verify the Profile screen no longer shows academic statistics.
2. Verify the "Lihat Data Nilai" button/card is present at the bottom of the Profile screen.
3. Click "Lihat Data Nilai"; verify navigation to `DataNilaiScreen`.
4. On `DataNilaiScreen`, verify:
    - Header shows correct Name and NIM.
    - Statistics card is displayed correctly.
    - The list of grades shows all 6 subjects with correct details.
    - The summary footer shows correct totals.
5. Click the back button on `DataNilaiScreen` to return to the Profile screen.
6. Verify "Edit Profile" still works and correctly updates data across screens.
