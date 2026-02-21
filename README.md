# Mac Terminal App Launcher 🚀

A Java program that automatically opens Terminal on Mac startup and lets you launch apps with simple commands.

---

## What It Does

- Opens automatically every time your Mac boots
- Listens for simple text commands in Terminal
- Type `code` → launches **VSCode + Brave** instantly
- Supports combo commands to launch multiple apps at once
- Fully customizable to launch any app you want

---

## Requirements

- MacBook running macOS
- Java installed (JDK 11 or higher)
- Apps installed in `/Applications`

### Check if Java is installed:
```bash
java -version
```
If not installed, download it from https://adoptium.net

---

## Project Structure

```
~/TerminalLauncher/
│
├── Applauncher.java          # Main Java source code
├── Applauncher.class         # Compiled Java file (auto-generated)
└── launcher.sh               # Shell script to run the program

~/Library/LaunchAgents/
└── com.user.applauncher.plist  # macOS startup config file
```

---

## Installation & Setup

### Step 1 — Create the project folder
```bash
mkdir ~/TerminalLauncher
cd ~/TerminalLauncher
```
**What this does:** Creates a folder called `TerminalLauncher` in your home directory and navigates into it.

---

### Step 2 — Create the Java file
Create a file called `Applauncher.java` and paste the following code:

```java
import java.io.IOException;
import java.util.Scanner;

public class Applauncher {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("======== App Launcher ========");
        System.out.println("Type 'code' to launch VSCode + Brave. Type 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            String command = sc.nextLine().trim().toLowerCase();

            if (command.equals("exit")) {
                System.out.println("Exiting App Launcher. Goodbye!");
                break;
            }
            handleCommand(command);
        }

        sc.close();
    }

    public static void handleCommand(String command) {
        switch (command) {
            case "code":
                launchAbleApp("Visual Studio Code", "Brave Browser");
                break;
            case "brave":
                launchAbleApp("Brave Browser");
                break;
            case "vscode":
                launchAbleApp("Visual Studio Code");
                break;
            case "android":
                launchAbleApp("Android Studio");
                break;
            case "chrome":
                launchAbleApp("Google Chrome");
                break;
            case "safari":
                launchAbleApp("Safari");
                break;
            case "xcode":
                launchAbleApp("Xcode");
                break;
            case "whatsapp":
                launchAbleApp("WhatsApp");
                break;

            // ---- Combo Commands ----
            case "morning":
                launchAbleApp("WhatsApp", "Brave Browser", "Visual Studio Code");
                break;
            case "androiddev":
                launchAbleApp("Android Studio", "Brave Browser");
                break;
            case "iosdev":
                launchAbleApp("Xcode", "Brave Browser");
                break;

            case "help":
                printHelp();
                break;
            default:
                System.out.println("Unknown command. Please try again.");
                break;
        }
    }

    public static void launchAbleApp(String... appNames) {
        for (String app : appNames) {
            lauchApp(app);
        }
    }

    public static void lauchApp(String appNames) {
        try {
            ProcessBuilder pb = new ProcessBuilder("open", "-a", appNames);
            pb.inheritIO();
            pb.start();
            System.out.println("Launched " + appNames);
        } catch (IOException e) {
            System.err.println("Failed to launch " + appNames + ": " + e.getMessage());
        }
    }

    public static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  code       - Launch VSCode + Brave");
        System.out.println("  brave      - Launch Brave browser");
        System.out.println("  vscode     - Launch Visual Studio Code");
        System.out.println("  android    - Launch Android Studio");
        System.out.println("  chrome     - Launch Google Chrome");
        System.out.println("  safari     - Launch Safari");
        System.out.println("  xcode      - Launch Xcode");
        System.out.println("  whatsapp   - Launch WhatsApp");
        System.out.println("  --- Combo Commands ---");
        System.out.println("  morning    - Launch WhatsApp + Brave + VSCode");
        System.out.println("  androiddev - Launch Android Studio + Brave");
        System.out.println("  iosdev     - Launch Xcode + Brave");
        System.out.println("  help       - Show this help message");
        System.out.println("  exit       - Quit the App Launcher");
    }
}
```

---

### Step 3 — Compile the Java file
```bash
cd ~/TerminalLauncher
javac Applauncher.java
```
**What this does:** Converts your Java source code into a `.class` file that Java can actually run. You must do this every time you edit the code.

---

### Step 4 — Test it manually
```bash
java Applauncher
```
**What this does:** Runs your program. You should see the launcher prompt. Type any command to test.

---

### Step 5 — Create the shell script
```bash
nano ~/TerminalLauncher/launcher.sh
```
Paste this inside:
```bash
#!/bin/bash
cd ~/TerminalLauncher
java Applauncher
```
Save with `Ctrl+X` → `Y` → `Enter`

---

### Step 6 — Give the script permission to run
```bash
chmod +x ~/TerminalLauncher/launcher.sh
```

---

### Step 7 — Create the Launch Agent (auto-start on boot)
```bash
nano ~/Library/LaunchAgents/com.user.applauncher.plist
```
Paste this inside:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN"
  "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    <key>Label</key>
    <string>com.user.applauncher</string>

    <key>ProgramArguments</key>
    <array>
        <string>/usr/bin/osascript</string>
        <string>-e</string>
        <string>tell app "Terminal" to do script "cd ~/TerminalLauncher && java Applauncher"</string>
    </array>

    <key>RunAtLoad</key>
    <true/>
</dict>
</plist>
```
Save with `Ctrl+X` → `Y` → `Enter`

---

### Step 8 — Register the Launch Agent with macOS
```bash
launchctl load ~/Library/LaunchAgents/com.user.applauncher.plist
```

---

### Step 9 — Test the auto-start without rebooting
```bash
launchctl start com.user.applauncher
```

---

## Available Commands

| Command | What it does |
|---|---|
| `code` | Launch VSCode + Brave |
| `vscode` | Launch VSCode only |
| `brave` | Launch Brave Browser only |
| `android` | Launch Android Studio only |
| `chrome` | Launch Google Chrome |
| `safari` | Launch Safari |
| `xcode` | Launch Xcode |
| `whatsapp` | Launch WhatsApp |
| `morning` | Launch WhatsApp + Brave + VSCode |
| `androiddev` | Launch Android Studio + Brave |
| `iosdev` | Launch Xcode + Brave |
| `help` | Show all available commands |
| `exit` | Quit the App Launcher |

---

## Making Changes in the Future

Every time you want to update or add something, always follow these 4 steps in order:

**Step 1 — Edit the code** in VSCode.

**Step 2 — Copy the updated file** to the TerminalLauncher folder:
```bash
cp "/Users/anshumanrana/Desktop/code/Personal Projects/TerminalLauncher/Applauncher.java" ~/TerminalLauncher/
```

**Step 3 — Recompile:**
```bash
cd ~/TerminalLauncher
javac Applauncher.java
```

**Step 4 — Restart the agent:**
```bash
launchctl stop com.user.applauncher
launchctl start com.user.applauncher
```

> **Golden Rule: Edit → Copy → Compile → Restart. Always in that order.**

If something isn't working after a change, 99% of the time it's because the file wasn't copied or Step 3 was skipped.

---

## Useful Management Commands

| Command | What it does |
|---|---|
| `launchctl start com.user.applauncher` | Start the agent manually |
| `launchctl stop com.user.applauncher` | Stop the agent |
| `launchctl unload ~/Library/LaunchAgents/com.user.applauncher.plist` | Disable auto-start permanently |
| `launchctl load ~/Library/LaunchAgents/com.user.applauncher.plist` | Re-enable auto-start |

---

## Troubleshooting

**`ClassNotFoundException: Applauncher`**
You haven't compiled the Java file yet or compiled the wrong file. Run the copy command then `javac Applauncher.java`.

**Old commands showing after update**
Your `~/TerminalLauncher` folder has the old file. Run the `cp` copy command first then recompile.

**`Unable to find application named 'X'`**
The app name doesn't match exactly. Run `ls /Applications | grep -i "appname"` to find the correct name.

**Terminal doesn't open on boot**
Make sure you ran `launchctl load` in Step 8. Also check that your `.class` file exists in `~/TerminalLauncher`.

**App is in Downloads, not Applications**
Move it first:
```bash
mv "/Users/yourname/Downloads/AppName.app" /Applications/
```

---

## Important Notes

- This launcher opens on **full Mac boot/restart only** — it does NOT trigger when unlocking from sleep
- App names must match exactly what's in your `/Applications` folder including spaces and capital letters
- Always copy the latest file from your Desktop project folder to `~/TerminalLauncher` before compiling
- You must recompile every time you edit the Java code

---

## What You Learn Building This

- Java `Scanner` for reading user input
- Java `ProcessBuilder` for launching external programs
- `switch` statements and method organization
- Shell scripting basics (`#!/bin/bash`, `chmod`)
- How macOS Launch Agents work
- Terminal navigation and file management

---

*Built with Java on macOS. No external libraries required.*