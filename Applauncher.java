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