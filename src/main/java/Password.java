public class Password {
    private String content;
    private int length;
    private String strengthScore;
    
    // Constructeur
    public Password(String content, int length, String strengthScore) {
        this.content = content;
        this.length = length;
        this.strengthScore = strengthScore;
    }
    
    // Getters et Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }
    
    public String getStrengthScore() { return strengthScore; }
    public void setStrengthScore(String strengthScore) { this.strengthScore = strengthScore; }
    
    @Override
    public String toString() {
        return String.format("Password: %s | Length: %d | Strength: %s", 
                            content, length, strengthScore);
    }
}