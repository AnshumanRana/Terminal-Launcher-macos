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