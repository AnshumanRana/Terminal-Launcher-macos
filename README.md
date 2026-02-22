# Mac Terminal App Launcher 🚀

A Java program that automatically opens Terminal on Mac startup and lets you launch apps with simple commands — now with **Python voice recognition support**.

---

## Project Timeline

**22 Feb 2026 — 3:00 AM**
Built the entire Terminal-based Java launcher in one late night session. Coffee, music, and a Tony Stark JARVIS scene from Iron Man 2 as inspiration. Completed the Java program, auto-boot system via macOS Launch Agents, and all combo commands. Then went to sleep.

**22 Feb 2026 — 11:00 AM**
Woke up and added the Python voice recognition script. You can now speak commands out loud and the launcher responds — just like JARVIS.

---

## What It Does

- Opens automatically every time your Mac boots
- Listens for typed commands in Terminal
- Supports voice commands via Python speech recognition
- Type or say `code` → launches **VSCode + Brave** instantly
- Supports combo commands to launch multiple apps at once
- Fully customizable — add any app or combination you want

---

## How It Works

### Text Mode (Java)
```
You type a command in Terminal
        ↓
Java reads the input
        ↓
ProcessBuilder runs: open -a "App Name"
        ↓
App launches
```

### Voice Mode (Python + Java)
```
You speak a command
        ↓
Python listens via your mic
        ↓
Google Speech API converts voice to text
        ↓
Python passes the text command to Java
        ↓
Java launches the app
```

---

## Requirements

- MacBook running macOS (Apple Silicon or Intel)
- Java installed (JDK 11 or higher)
- Python 3 installed
- Homebrew installed
- Apps installed in `/Applications`
- Internet connection (required for voice recognition)

### Check if Java is installed:
```bash
java -version
```

### Check if Python is installed:
```bash
python3 --version
```

If not installed, download Java from https://adoptium.net

---

## Project Structure

```
~/TerminalLauncher/
│
├── Applauncher.java          # Main Java source code
├── Applauncher.class         # Compiled Java file (auto-generated)
├── launcher.sh               # Shell script to run the program
└── VoiceLauncher.py          # Python voice recognition script

~/Library/LaunchAgents/
└── com.user.applauncher.plist  # macOS startup config file
```

---

## Part 1 — Java Terminal Launcher Setup

### Step 1 — Create the project folder
```bash
mkdir ~/TerminalLauncher
cd ~/TerminalLauncher
```

---

### Step 2 — Create the Java file
Create `Applauncher.java` and paste the following code:

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

---

### Step 4 — Test it manually
```bash
java Applauncher
```

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

### Step 8 — Register and test the Launch Agent
```bash
launchctl load ~/Library/LaunchAgents/com.user.applauncher.plist
launchctl start com.user.applauncher
```

---

## Part 2 — Python Voice Launcher Setup

### Step 1 — Install dependencies
```bash
brew install portaudio flac
pip3 install SpeechRecognition pyaudio
```

**Why portaudio?** pyaudio needs it to access your Mac's microphone.
**Why flac?** SpeechRecognition uses it to process audio. The bundled version doesn't work on Apple Silicon Macs so we install the correct one via Homebrew.

---

### Step 2 — Verify installation
```bash
python3 -c "import speech_recognition; import pyaudio; print('All OK')"
```

Should print `All OK`.

---

### Step 3 — Create the Python voice script
Create `VoiceLauncher.py` and paste the following code:

```python
import speech_recognition as sr
import subprocess
import time

def listen_for_command():
    recognizer = sr.Recognizer()
    mic = sr.Microphone()

    with mic as source:
        print("🎙️  Adjusting for background noise...")
        recognizer.adjust_for_ambient_noise(source, duration=1)
        print("✅  Listening... Speak your command!")

        try:
            audio = recognizer.listen(source, timeout=5)
            command = recognizer.recognize_google(audio).lower()
            print(f"🗣️  You said: {command}")
            return command
        except sr.WaitTimeoutError:
            print("⏱️  No speech detected. Try again.")
            return None
        except sr.UnknownValueError:
            print("❓  Could not understand. Try again.")
            return None
        except sr.RequestError:
            print("🌐  Internet connection required for voice recognition.")
            return None

def send_command_to_java(command):
    valid_commands = [
        "code", "brave", "vscode", "android",
        "chrome", "safari", "xcode", "whatsapp",
        "morning", "androiddev", "iosdev", "help", "exit"
    ]

    if command in valid_commands:
        print(f"⚡  Sending '{command}' to App Launcher...")
        subprocess.run(["java", "Applauncher"],
                      input=command,
                      text=True,
                      cwd="/Users/anshumanrana/TerminalLauncher")
    else:
        print(f"❌  '{command}' is not a valid command. Try again.")

def main():
    print("======== Voice App Launcher ========")
    print("Speak a command to launch your apps!")
    print("Say 'exit' to quit.\n")

    while True:
        command = listen_for_command()

        if command:
            if command == "exit":
                print("👋  Goodbye!")
                break
            send_command_to_java(command)

        time.sleep(1)

if __name__ == "__main__":
    main()
```

---

### Step 4 — Copy to TerminalLauncher folder and run
```bash
cp "/Users/anshumanrana/Desktop/code/Personal Projects/TerminalLauncher/VoiceLauncher.py" ~/TerminalLauncher/
cd ~/TerminalLauncher
python3 VoiceLauncher.py
```

Speak any command like `morning` or `brave` and the apps will launch!

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

Every time you update the Java code follow these 4 steps:

**Step 1 — Edit** the code in VSCode.

**Step 2 — Copy** the updated file:
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

For Python changes just copy the updated file and run it again — no compilation needed.

---

## Useful Management Commands

| Command | What it does |
|---|---|
| `java Applauncher` | Run text launcher manually |
| `python3 VoiceLauncher.py` | Run voice launcher manually |
| `launchctl start com.user.applauncher` | Start boot agent manually |
| `launchctl stop com.user.applauncher` | Stop boot agent |
| `launchctl unload ~/Library/LaunchAgents/com.user.applauncher.plist` | Disable auto-start permanently |
| `launchctl load ~/Library/LaunchAgents/com.user.applauncher.plist` | Re-enable auto-start |

---

## Troubleshooting

**`ClassNotFoundException: Applauncher`**
Run the copy command then `javac Applauncher.java`.

**Old commands showing after update**
Run the `cp` copy command first then recompile and restart the agent.

**`Unable to find application named 'X'`**
Run `ls /Applications | grep -i "appname"` to find the exact name.

**`Bad CPU type in executable: flac-mac`**
You are on Apple Silicon. Run `brew install flac` to fix it.

**Voice not being recognized**
Make sure you have an internet connection. Google Speech API requires it. Also check mic permissions in System Settings → Privacy & Security → Microphone.

**Terminal doesn't open on boot**
Make sure you ran `launchctl load` and that your `.class` file exists in `~/TerminalLauncher`.

---

## Important Notes

- The launcher opens on **full Mac boot/restart only** — not when unlocking from sleep
- Voice recognition requires an **internet connection**
- App names must match exactly what's in `/Applications` including spaces and capital letters
- Always copy the latest file from your Desktop to `~/TerminalLauncher` before compiling
- Python changes don't need recompilation — just copy and run

---

## What You Learn Building This

- Java `Scanner` and `ProcessBuilder` for input and launching programs
- `switch` statements and method organization in Java
- Shell scripting basics (`#!/bin/bash`, `chmod`)
- How macOS Launch Agents work
- Python speech recognition and microphone access
- How to connect two different programs (Python + Java) together
- Terminal navigation and file management

---

*Built with Java + Python on macOS. No paid libraries or APIs required.*