import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PasswordGenerator generator = new PasswordGenerator();
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Mode interactif ou ligne de commande
            if (args.length > 0) {
                handleCommandLine(generator, args);
            } else {
                handleInteractive(generator, scanner);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur inattendue: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    private static void handleCommandLine(PasswordGenerator generator, String[] args) {
        int length = Integer.parseInt(args[0]);
        boolean useUppercase = args.length > 1 && args[1].contains("up");
        boolean useDigits = args.length > 1 && args[1].contains("dig");
        boolean useSymbols = args.length > 1 && args[1].contains("sym");
        
        // Mode rafale si --batch présent
        if (args.length > 2 && args[2].equals("--batch")) {
            int count = args.length > 3 ? Integer.parseInt(args[3]) : 5;
            List<Password> passwords = generator.generateBatch(count, length, 
                useUppercase, useDigits, useSymbols);
            
            System.out.println("\n=== " + count + " MOTS DE PASSE GÉNÉRÉS ===");
            for (int i = 0; i < passwords.size(); i++) {
                System.out.println((i+1) + ". " + passwords.get(i));
            }
        } else {
            Password pwd = generator.generate(length, useUppercase, useDigits, useSymbols);
            System.out.println("\n=== MOT DE PASSE GÉNÉRÉ ===");
            System.out.println(pwd);
        }
    }
    
    private static void handleInteractive(PasswordGenerator generator, Scanner scanner) {
        System.out.println("=== GÉNÉRATEUR DE MOTS DE PASSE ===");
        System.out.print("Longueur du mot de passe: ");
        int length = scanner.nextInt();
        
        System.out.print("Inclure majuscules? (true/false): ");
        boolean useUppercase = scanner.nextBoolean();
        
        System.out.print("Inclure chiffres? (true/false): ");
        boolean useDigits = scanner.nextBoolean();
        
        System.out.print("Inclure symboles? (true/false): ");
        boolean useSymbols = scanner.nextBoolean();
        
        System.out.print("Mode rafale: combien de mots de passe? (1 pour simple): ");
        int count = scanner.nextInt();
        
        if (count > 1) {
            List<Password> passwords = generator.generateBatch(count, length,
                useUppercase, useDigits, useSymbols);
            System.out.println("\n=== " + count + " MOTS DE PASSE ===");
            for (int i = 0; i < passwords.size(); i++) {
                System.out.println((i+1) + ". " + passwords.get(i));
            }
        } else {
            Password pwd = generator.generate(length, useUppercase, useDigits, useSymbols);
            System.out.println("\n" + pwd);
        }
    }
}