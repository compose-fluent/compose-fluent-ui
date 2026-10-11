# WinUI MinGW Gallery experiment

This branch starts from `origin/dev` and adds `gallery:mingwApp`. It uses the published
`compose-winui:1.12-SNAPSHOT` plugin to select CMP WinUI `1.12.0-alpha01` for MinGW.
Compose Resources follows the CMP WinUI release as `1.12.0-alpha01-SNAPSHOT`.
JVM redirection remains opt-in; the existing JVM Gallery stays on CMP.

`-Pfluent.winui=true` selects the MinGW experiment profile. The library and shared
Gallery then compile their common and Skiko sources directly for MinGW; KSP runs
on that compilation. Without this flag, the original Android, JVM, Apple, JS and
Wasm targets remain available. Separate profiles avoid mixing CMP and WinUI module
identities in Kotlin's shared metadata dependency analysis.

## Build and run on Windows

Use JDK 25 and Windows SDK
10.0.26100.0. The Windows Toolkit restores the Windows App SDK components and
creates a self-contained, unpackaged native application.

```powershell
git submodule update --init external/haze
./gradlew.bat :gallery:mingwApp:runDebugExecutableMingwX64 "-Pfluent.winui=true"
```

An automated startup check loads every generated sample document, loads the Gallery
image resource, renders highlighted sample text, waits for actual WinUI frames,
verifies the renderer, and closes the app:

```powershell
./gradlew.bat :gallery:mingwApp:runDebugExecutableMingwX64 "-Pfluent.winui=true" "-Pgallery.winui.smokeTest=true"
```

`compose-winui` stages the Skiko DLL, ICU data, and generated/transitive Compose
Resources automatically. No manual import library or DLL setup is needed.

## Haze fork

`external/haze` pins [compose-fluent/haze](https://github.com/compose-fluent/haze),
based on Haze 2.0.1. Its `winui` composite build compiles the existing Skia effects
for WinUI and supplies Windows timing, logging, lifecycle, and window identity.
The original Haze targets remain available in the upstream build profile.

The experiment redirects `dev.chrisbanes.haze:*:2.0.1` to
`io.github.compose-fluent.haze:*:2.0.1-winui-SNAPSHOT` for MinGW through the plugin's
`redirectGroup` configuration. JVM, Android, Apple, JS, and Wasm continue using the
upstream Haze coordinates in the original build profile.

## Sample code documents

`gallery-processor` extracts `@Sample` bodies and parses their Kotlin ASTs at build
time. It generates `KotlinCodeDocument` data containing the source and categorized
UTF-16 spans, plus a `sampleCodeDocuments` index. The Gallery renders those spans
with its light/dark palette and copies the original source text. The runtime has no
`dev.snipme:highlights` dependency or Kotlin parser.

```powershell
./gradlew.bat :gallery-processor:jvmTest :gallery:sharedApp:compileKotlinJvm
```
## Validation

Validated on Windows with JDK 25 and Gradle 9.7.1:

- The public `compose-winui:1.12-SNAPSHOT` and Resources snapshots build and run the
  native Gallery with parallel execution and configuration cache enabled.
- The startup check loads all 106 generated documents, renders highlighted text
  and the image resource, and confirms successful WinUI frames before closing.
- The original JVM Gallery and shared application compile successfully.
- Four parser/generator tests cover AST categories, Unicode, string templates,
  incomplete snippets, and generated Kotlin constants.
- The Haze fork passes five Skia effect pixel tests and two Windows/lifecycle tests.
