# Claude Code Copy File Location Plugin

A JetBrains IDE plugin that allows you to quickly copy file locations with optional line numbers to the clipboard in Claude Code format.

## Features

- **Quick Copy**: Use Ctrl+Shift+C (configurable) to copy file location
- **Smart Line Numbers** (Claude Code format):
  - When text is selected: `@/path/to/file#L41-49`
  - When no text selected: `@/path/to/file`
- **Multiple Access Points**: Available in Edit menu and right-click context menu
- **Instant Feedback**: Shows notification when location is copied

## Installation

### From Source
1. Clone this repository
2. Run `./gradlew buildPlugin`
3. Install the generated ZIP from `build/distributions/` in your IDE:
   - Go to **File** → **Settings** → **Plugins**
   - Click the gear icon → **Install Plugin from Disk...**
   - Select the ZIP file

### Development
1. Run `./gradlew runIde` to test the plugin in a development IDE instance

## Usage

1. Open any file in your JetBrains IDE
2. Optionally select some text to include line numbers
3. Press **Ctrl+Shift+C** or use **Edit** → **Copy File Location**
4. The file location will be copied to your clipboard

## Examples

**No selection:**
```
@/home/user/project/src/main/java/Example.java
```

**Text selected from line 41 to 49:**
```
@/home/user/project/src/main/java/Example.java#L41-49
```

**Single line selected (line 25):**
```
@/home/user/project/src/main/java/Example.java#L25
```

## Configuration

### Keyboard Shortcut
The keyboard shortcut can be changed in:
**File** → **Settings** → **Keymap** → Search for "Copy File Location"

### Path Format
You can configure whether to copy absolute or relative paths:
**File** → **Settings** → **Tools** → **Claude Code Copy Location** → **Use relative paths**

- **Enabled (default)**: Copies relative paths like `@src/main/java/Example.java`
- **Disabled**: Copies absolute paths like `@/home/user/project/src/main/java/Example.java`

## Compatibility

- IntelliJ IDEA Community/Ultimate 2023.2+
- Other JetBrains IDEs based on IntelliJ Platform

## Development

This plugin is built using:
- Kotlin
- IntelliJ Platform Plugin SDK
- Gradle with Kotlin DSL

See [CLAUDE.md](CLAUDE.md) for detailed development information.