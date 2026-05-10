import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class StrengthAuditor {
    
    // Option A: Utilisation de ProcessBuilder pour Docker
    public static String auditWithDockerCLI(String password) {
        try {
            // Exemple avec un conteneur Zxcvbn
            ProcessBuilder pb = new ProcessBuilder(
                "docker", "run", "--rm", 
                "zxcvbn-container", "zxcvbn", password
            );
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            String result = reader.readLine();
            process.waitFor();
            return result;
        } catch (Exception e) {
            return "Erreur: " + e.getMessage();
        }
    }
    
    // Option B: API HTTP avec HttpClient (Java 11+)
    public static String auditWithHttp(String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/check-strength"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                    String.format("{\"password\":\"%s\"}", password)
                ))
                .build();
            
            HttpResponse<String> response = client.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            return response.body();
        } catch (Exception e) {
            return "Validation impossible: " + e.getMessage();
        }
    }
}