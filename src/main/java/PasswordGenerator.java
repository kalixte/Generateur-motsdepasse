import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    
    private SecureRandom random = new SecureRandom();
    
    // Génère plusieurs mots de passe (mode rafale)
    public List<Password> generateBatch(int count, int length, boolean useUppercase, 
                                        boolean useDigits, boolean useSymbols) {
        List<Password> passwords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            passwords.add(generate(length, useUppercase, useDigits, useSymbols));
        }
        return passwords;
    }
    
    // Génère un seul mot de passe
    public Password generate(int length, boolean useUppercase, 
                            boolean useDigits, boolean useSymbols) {
        StringBuilder characters = new StringBuilder(LOWERCASE);
        
        if (useUppercase) characters.append(UPPERCASE);
        if (useDigits) characters.append(DIGITS);
        if (useSymbols) characters.append(SYMBOLS);
        
        if (characters.length() == 0) {
            throw new IllegalArgumentException("Au moins un type de caractères doit être sélectionné");
        }
        
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        
        // Score initial basé sur la complexité
        String strength = evaluateStrength(length, useUppercase, useDigits, useSymbols);
        return new Password(password.toString(), length, strength);
    }
    
    private String evaluateStrength(int length, boolean useUppercase, 
                                   boolean useDigits, boolean useSymbols) {
        int score = 0;
        if (length >= 12) score++;
        if (useUppercase) score++;
        if (useDigits) score++;
        if (useSymbols) score++;
        
        if (score >= 4 && length >= 16) return "TRÈS FORT";
        if (score >= 3 && length >= 12) return "FORT";
        if (score >= 2 && length >= 8) return "MOYEN";
        return "FAIBLE";
    }
}