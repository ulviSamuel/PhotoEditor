# Test Photo Editor

An Android photo-editing application written in Java that provides drawing, text, color, zoom, and edit-history controls over a bundled sample image.

![Java](https://img.shields.io/badge/Language-Java-007396?style=flat-square)
![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square)
![Academic Project](https://img.shields.io/badge/Category-Academic%20Project-6B7280?style=flat-square)
![Year | 2023](https://img.shields.io/badge/Year%20%7C%202023-2023-6B7280?style=flat-square)

> [!NOTE]
> This repository contains an academic project originally developed during earlier programming studies. It is preserved as a record of the technical knowledge, design decisions, and development experience acquired at the time.

## Overview

The application starts with `img_test_icon.jpg` as its editing canvas. `MainActivity` connects the XML interface in `app/src/main/res/layout/new_layout.xml` to the `PhotoEditor` library and manages the available editing modes.

## Features

- Draw on the canvas with a pencil tool.
- Erase brush strokes with a dedicated eraser mode.
- Insert text using the default `Enter Text` label and edit it through a dialog.
- Select black, white, sky blue, yellow, green, or red for drawing and text.
- Zoom and pan the canvas through `ZoomLayout`.
- Undo, redo, or clear editor views.
- Confirm the current edit to invoke the library's bitmap-save callback.

The confirm action currently receives the bitmap in an in-memory callback; the application does not define a file path or other persistent export destination.

## Technology stack

- **Language:** Java 8 source and target compatibility
- **Platform:** Android
- **Build system:** Gradle Wrapper with Android Gradle Plugin 7.4.1
- **Compile and target SDK:** Android API 33
- **Minimum SDK:** Android API 24
- **UI:** Android XML layouts and Material Components
- **Editing:** `com.burhanrashid52:photoeditor:3.0.1`
- **Zooming:** `com.otaliastudios:zoomlayout:1.9.0`

## Project structure

```text
.
├── app/
│   └── src/main/
│       ├── java/it/volta/ts/pcto/testphotoeditor/MainActivity.java
│       ├── res/layout/new_layout.xml
│       └── AndroidManifest.xml
├── build.gradle
├── gradle/wrapper/
├── gradlew
├── gradlew.bat
└── settings.gradle
```

The project contains one Android application module, `app`. `MainActivity` is the launcher activity declared in `AndroidManifest.xml`.

## Getting started

### Prerequisites

- Android SDK with the Android API 33 platform available.
- A Java runtime compatible with Gradle 8.0 and Android Gradle Plugin 7.4.1.

### Build

From the repository root, use the included Gradle Wrapper:

```bash
bash ./gradlew assembleDebug
```

The debug APK is generated under `app/build/outputs/apk/debug/` when the build completes successfully.

### Run

Open the project in Android Studio, select the `app` configuration, and run it on an Android device or emulator that satisfies the application's minimum SDK. The launcher entry point is `MainActivity`.

## Testing

No unit-test or instrumented-test source sets are present in the repository. The available Gradle test tasks can be listed with:

```bash
bash ./gradlew tasks --all
```

## Project status

The repository records a completed academic prototype with a single activity and a bundled test image. It does not include a release workflow, deployment configuration, or automated test suite.

## License

This project is shared for educational and portfolio purposes. All rights reserved unless otherwise stated.
