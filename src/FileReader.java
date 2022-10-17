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

        try(BufferedReader bufIn = Files.newBufferedReader(filePath)){
            while ((line = bufIn.readLine()) != null){
                sb.append(line.trim()).append(", ");
                counter++;
                if(counter >= 2) {
                    sb.replace(sb.length() -2, sb.length() - 1, ""); // index första är
                    String[] currentLines = sb.toString().trim().split(", ");
                    customers.add(new Customer(currentLines[0], currentLines[1], LocalDate.parse(currentLines[2]))); // metod IsDate boolean, Try parsa string till datum, funka return TRUE annars FALSE
                    counter = 0;
                    sb.setLength(0);
                }
            }
        } catch (IOException e){
            System.out.println("Inläsning av fil misslyckades. Säkerställ att den ligger i src-mappen.");
        }
        return customers;
    }


}




