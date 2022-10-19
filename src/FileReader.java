import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public List<Customer> createListFromFile(Path filePath) {
        int counter = 0;
        String line;
        StringBuilder sb = new StringBuilder();
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader in = Files.newBufferedReader(filePath)) {
            while ((line = in.readLine()) != null) {
                // Tar bort eventuella blanktecken och tillskriver "," vid slutet av varje line
                sb.append(line.trim()).append(", ");
                counter++;
                if (counter >= 2) {
                    // Tar bort sista komma
                    sb.replace(sb.length() - 2, sb.length() - 1, "");
                    // delar upp till tre lines till array med split.
                    String[] currentLine = sb.toString().trim().split(", ");
                    // Skapar och lägger till Customer i lista customers.
                    customers.add(new Customer(currentLine[0], currentLine[1], LocalDate.parse(currentLine[2])));
                    // startar om räknaren.
                    counter = 0;
                    // Rensar StringBuilder.
                    sb.setLength(0);
                }
            }
        } catch (IOException e) {
            System.out.println("Inläsning av fil misslyckades. Säkerställ att den ligger i src-mappen.");
        }
        return customers;
    }
}




