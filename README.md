# Mac Terminal App Launcher 🚀

A Java program that automatically opens Terminal on Mac startup and lets you launch apps like VSCode and Brave Browser with simple commands.

---

## What It Does

- Opens automatically every time your Mac boots
- Listens for simple text commands in Terminal
- Type `code` → launches **VSCode + Brave** instantly
- Fully customizable to launch any app you want

---

## Requirements

- MacBook running macOS
- Java installed (JDK 11 or higher)
- VSCode installed in `/Applications`
- Brave Browser installed in `/Applications`

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
            case "code":   launchAbleApp("Visual Studio Code", "Brave Browser"); break;
            case "brave":  launchAbleApp("Brave Browser");                       break;
            case "vscode": launchAbleApp("Visual Studio Code");                  break;
            case "help":   printHelp();                                          break;
            default:       System.out.println("Unknown command. Please try again.");
        }
    }

    public static void launchAbleApp(String... appNames) {
        for (String app : appNames) {
            lauchApp(app);
        }
    }

    public static void lauchApp(String appName) {
        try {
            ProcessBuilder pb = new ProcessBuilder("open", "-a", appName);
            pb.inheritIO();
            pb.start();
            System.out.println("Launched: " + appName);
        } catch (IOException e) {
            System.err.println("Failed to launch " + appName + ": " + e.getMessage());
        }
    }

    public static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  code   - Launch VSCode + Brave");
        System.out.println("  brave  - Launch Brave browser");
        System.out.println("  vscode - Launch Visual Studio Code");
        System.out.println("  help   - Show this help message");
        System.out.println("  exit   - Quit the App Launcher");
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
**What this does:** Runs your program. You should see the launcher prompt. Type `code` to test that VSCode and Brave open correctly.

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

**What this does:** Creates a simple script file that navigates to your project folder and runs the Java program. macOS will use this script on startup.

---

### Step 6 — Give the script permission to run
```bash
chmod +x ~/TerminalLauncher/launcher.sh
```
**What this does:** By default, macOS doesn't allow new scripts to execute. This command grants it permission to run as a program.

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

**What this does:** Creates a special macOS config file in the `LaunchAgents` folder. macOS reads this folder on every boot and runs whatever is listed inside. This is how apps like Dropbox, Spotify, and others start automatically on your Mac.

- `Label` — a unique name so macOS can identify your agent
- `ProgramArguments` — the exact command macOS runs on boot (opens Terminal and starts your Java program)
- `RunAtLoad` — tells macOS to run this immediately on startup

---

### Step 8 — Register the Launch Agent with macOS
```bash
launchctl load ~/Library/LaunchAgents/com.user.applauncher.plist
```
**What this does:** Tells macOS "I added a new startup program, please register it." Without this step, macOS won't know the plist file exists even though it's in the right folder.

---

### Step 9 — Test the auto-start without rebooting
```bash
launchctl start com.user.applauncher
```
**What this does:** Triggers your Launch Agent immediately so you can test it without restarting your Mac. A new Terminal window should open with the App Launcher running.

---

## Available Commands

| Command | What it does |
|---|---|
| `code` | Launch VSCode + Brave Browser |
| `vscode` | Launch VSCode only |
| `brave` | Launch Brave Browser only |
| `help` | Show all available commands |
| `exit` | Quit the App Launcher |

---

## Useful Management Commands

| Command | What it does |
|---|---|
| `launchctl start com.user.applauncher` | Start the agent manually |
| `launchctl stop com.user.applauncher` | Stop the agent |
| `launchctl unload ~/Library/LaunchAgents/com.user.applauncher.plist` | Disable auto-start permanently |
| `launchctl load ~/Library/LaunchAgents/com.user.applauncher.plist` | Re-enable auto-start |

---

## Adding More Apps

To add a new app, find its exact name first:
```bash
ls /Applications | grep -i "appname"
```

Then add a new case in the `handleCommand` method in `Applauncher.java`:
```java
case "spotify": launchAbleApp("Spotify"); break;
```

Recompile after any changes:
```bash
javac Applauncher.java
```

---

## Troubleshooting

**`ClassNotFoundException: Applauncher`**
You haven't compiled the Java file yet. Run `javac Applauncher.java` first.

**`Unable to find application named 'X'`**
The app name doesn't match exactly. Run `ls /Applications | grep -i "appname"` to find the correct name.

**Terminal doesn't open on boot**
Make sure you ran `launchctl load` in Step 8. Also check that your `.class` file exists in `~/TerminalLauncher`.

**App is in Downloads, not Applications**
Move it first: `mv "/Users/yourname/Downloads/AppName.app" /Applications/`

---

## Important Notes

- This launcher opens on **full Mac boot/restart only** — it does NOT trigger when unlocking from sleep
- App names must match exactly what's in your `/Applications` folder including spaces and capital letters
- You must recompile (`javac Applauncher.java`) every time you edit the Java code

---

## What You Learn Building This

- Java `Scanner` for reading user input
- Java `ProcessBuilder` for launching external programs
- `switch` statements and method organization
- Shell scripting basics (`#!/bin/bash`, `chmod`)
- How macOS Launch Agents work
- Terminal navigation and file management

---

---

## Making Changes in the Future

Every time you want to update or add something to the program, always follow these 3 steps in order:

**Step 1 — Edit the code** in VSCode, make whatever changes you need.

**Step 2 — Recompile** — this is the most important step people forget. Your changes won't work until you do this:
```bash
cd ~/TerminalLauncher
javac Applauncher.java
```

**Step 3 — Restart the agent** so macOS picks up the new version:
```bash
launchctl stop com.user.applauncher
launchctl start com.user.applauncher
```

> **Golden Rule: Edit → Compile → Restart. Always all 3 steps, always in that order.**

If something isn't working after a change, 99% of the time it's because Step 2 was skipped.

### Adding a New App
Find the exact app name, add a new `case` line inside `handleCommand`, recompile, and restart. That's it.

---

*Built with Java on macOS. No external libraries required.*