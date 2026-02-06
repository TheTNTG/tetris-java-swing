package simple.tetris;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Launcher {
    
    static TetrisGame activeGame = null;
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        
       
    }
    
    private static void playGame(){
        while(!activeGame.getGameState()){

            System.out.println("\nControls: 4=Left, 6=Right, 5=Down, 8=Rotate, 0=Pause");
           // System.out.println("Current Score: " + activeGame.getScore());
            System.out.print("Action: ");
            
            try {
                int input = Integer.parseInt(scanner.nextLine());

                switch(input){
                    case 0: // Pause
                        System.out.println("\n--- PAUSED ---");
                        System.out.println("1. Resume\n2. Save and Exit\n3. Quit to Desktop");
                        int pChoice = Integer.parseInt(scanner.nextLine());

                        if (pChoice == 2) {
                            System.out.print("Enter name for this save slot: ");
                            String saveName = scanner.nextLine();
                            saveGame(saveName);
                            return; // Returns to Main Menu
                        } else if (pChoice == 3) {
                            System.exit(0);
                        }
                        break;
                    case 4:
                        activeGame.attemptMove(-1,0);
                        break;
                    case 6:
                        activeGame.attemptMove(1,0);
                        break; // <-- THIS BREAK WAS MISSING
                    case 5: 
                        activeGame.hardDrop();
                        activeGame.update();
                        break;
                    case 8: 
                       activeGame.attemptRotate(); 
                       break;
                    default:
                        activeGame.update(); // Gravity for any other key
                }
            } catch(NumberFormatException nfe){
                activeGame.update(); 
            }
        }// Inside playGame() after the while loop ends:
        if (activeGame.getGameState()) {
            System.out.println("GAME OVER! Final Score: " + activeGame.getScore());
            System.out.print("Enter your name for the leaderboard: ");
            String name = scanner.nextLine();
            saveHighScore(name, activeGame.getScore());
            activeGame = null;
        }
    }
    
    private static void saveGame(String playerName) {
        if (activeGame == null) return;

        try {
            // Create a unique filename based on the player name
            String filename = "save_" + playerName + ".dat";
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));

            // We save both the name and the game object together
            out.writeObject(playerName); 
            out.writeObject(activeGame);

            out.close();
            System.out.println("Game saved as " + filename);
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    private static void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            activeGame = (TetrisGame) in.readObject();
            System.out.println("Game loaded successfully!");
            playGame(); // Jump straight into the loaded game
        } catch (FileNotFoundException e) {
            System.out.println("No saved game found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }

    private static void saveHighScore(String name, int score) {
            // We use 'true' in FileWriter to APPEND to the file rather than overwrite it
            try (PrintWriter writer = new PrintWriter(new FileWriter("scores.txt", true))) {
                writer.println(name + ":" + score);
                System.out.println("Score saved!");
            } catch (IOException e) {
                System.out.println("Could not save score: " + e.getMessage());
            }
     }

    private static void displayScores() {
        System.out.println("\n--- HIGH SCORES ---");
        try (BufferedReader reader = new BufferedReader(new FileReader("scores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by the colon (e.g., "John:150")
                String[] parts = line.split(":");
                System.out.println(parts[0] + " - " + parts[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No scores yet. Be the first!");
        } catch (IOException e) {
            System.out.println("Error reading scores.");
        }
    }   
    private static void listSavedGames() {
        File folder = new File("."); // Look in current folder
        // Filter files that start with "save_" and end with ".dat"
        File[] saveFiles = folder.listFiles((dir, name) -> name.startsWith("save_") && name.endsWith(".dat"));

        if (saveFiles == null || saveFiles.length == 0) {
            System.out.println("No saved games found.");
            return;
        }

        System.out.println("\n--- SELECT A SAVE ---");
        for (int i = 0; i < saveFiles.length; i++) {
            // Clean up the name for display (remove "save_" and ".dat")
            String displayName = saveFiles[i].getName().replace("save_", "").replace(".dat", "");
            System.out.println((i + 1) + ". " + displayName);
        }
        System.out.println("0. Back");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice > 0 && choice <= saveFiles.length) {
                loadSpecificGame(saveFiles[choice - 1]);
            }
        } catch (Exception e) {
            System.out.println("Invalid selection.");
        }   
    }

    private static void loadSpecificGame(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            String savedName = (String) in.readObject();
            activeGame = (TetrisGame) in.readObject();
            System.out.println("Welcome back, " + savedName + "!");
            playGame();
        } catch (Exception e) {
            System.out.println("Error loading this save: " + e.getMessage());
        }
    }
    
    
    private static void handleLoadMenu() {
        File folder = new File("."); // Looks in the current project folder

        // This finds all files that start with "save_" and end with ".dat"
        File[] saves = folder.listFiles((dir, name) -> name.startsWith("save_") && name.endsWith(".dat"));

        if (saves == null || saves.length == 0) {
            System.out.println("\n[!] No saved games found.");
            return;
        }

        System.out.println("\n--- CHOOSE A SAVE SLOT ---");
        for (int i = 0; i < saves.length; i++) {
            // We clean the name for display: "save_John.dat" becomes "John"
            String displayName = saves[i].getName().replace("save_", "").replace(".dat", "");
            System.out.println((i + 1) + ". " + displayName);
        }
        System.out.println("0. Back to Main Menu");
        System.out.print("Select slot: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) return;

            if (choice > 0 && choice <= saves.length) {
                loadSpecificFile(saves[choice - 1]);
            } else {
                System.out.println("Invalid slot.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private static void loadSpecificFile(File saveFile) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile))) {
            // Read the name and the game object exactly as we saved them
            String playerName = (String) in.readObject(); 
            activeGame = (TetrisGame) in.readObject();

            System.out.println("\nSuccessfully loaded " + playerName + "'s game!");
            playGame(); // Now it starts the game
        } catch (Exception e) {
            System.out.println("Failed to load save: " + e.getMessage());
        }
    }
}