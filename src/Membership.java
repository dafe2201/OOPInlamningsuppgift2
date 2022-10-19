import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Membership {

    protected LocalDateTime dateTimeNow = LocalDateTime.now();
    protected Scanner scanner = new Scanner(System.in);
    protected List<Customer> customers;

    public Membership() {
        final Path urlCustomers = Path.of("src/Customers.txt");
        FileReader fr = new FileReader();
        customers = fr.createListFromFile(urlCustomers);
    }

    // IO. om Customer hittas -> returnera Customer.
    public Customer searchCustomer(List<Customer> customers, boolean test) {
        while (true) {

            if (test) {
                return customers.get(0);
            }
            System.out.println("Skriv in personnummer (xxxxxxxxxx), eller hela namnet: ");
            String input = scanner.nextLine();

            for (Customer customer : customers) {
                if (input.equals("3")) {
                    System.exit(0);
                } else if (input.trim().equalsIgnoreCase(customer.getFullName().trim()) ||
                        input.trim().contains(customer.getPersonalNr())) {

                    System.out.println("Hittade kund: \n" + "Personnummer: "
                            + customer.getPersonalNr() + " Namn: " + customer.getFullName() + "\n");

                    return customer;
                }
            }
            System.out.println("Kunden finns inte i systemet");
        }
    }

    // Formaterar LocalDateTime till ett mer önskvärt format.
    protected String formatLocalDateTime(LocalDateTime dateTimeNow) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return formatter.format(dateTimeNow);
    }

    // Rapporterar betalande kunds närvaro till fil. Anledningen till den omfattande kodduplikationen är att kunna
    // append: false, i syfte att göra testerna bättre.
    public void reportAttendanceToFile(String input, Path urlAttendance, boolean test) {

        if (test) {
            try (FileWriter f = new FileWriter(urlAttendance.toFile(), false);
                 BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b)) {

                p.println(input);
                p.flush();

            } catch (IOException i) {
                i.printStackTrace();
                System.out.println("Skrivning till fil misslyckades.");
            } catch (RuntimeException e) {
                System.out.println("Skrivning till fil misslyckades.");
                e.printStackTrace();
            }

        } else {
            try (FileWriter f = new FileWriter(urlAttendance.toFile(), true);
                 BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b)) {

                p.println(input);
                p.flush();

            } catch (IOException i) {
                i.printStackTrace();
                System.out.println("Skrivning till fil misslyckades.");
            } catch (RuntimeException e) {
                System.out.println("Skrivning till fil misslyckades.");
                e.printStackTrace();
            }

        }
    }

    // Returnerar strängen som ska skickas till reportAttendanceToFile() för att skrivas till Attendance.txt.
    public String buildAttendance(Customer customer) {
        return formatLocalDateTime(dateTimeNow) + ", " + customer.getPersonalNr() + ", " +
                customer.getFullName() + "\n";
    }

    // Skriver ut hur längesedan någon köpte ett medlemskap och huruvida det fortfarande är giltigt.
    public void printMembershipStatus(long diff) {
        System.out.println("Senaste betalning skedde för " + diff + " dagar sedan.");

        if (doesntHaveMembership(diff)) {
            System.out.println("Inget medlemskap.");
        } else {
            System.out.println("Aktivt medlemskap.");
        }
    }

    public boolean doesntHaveMembership(long diff) {
        return diff > 364;
    }

    public void printReportedAttendance() {
        System.out.println("Kundens närvaro har registrerats.");
    }

    public void printNoReportedAttendance() {
        System.out.println("Inget aktivt medlemskap.\nIngen närvaro registrerades.");
    }

    // Räknar ut tid i dagar sedan ett medlemskap köptes.
    public long getDateDifference(Customer customer) {
        LocalDateTime latestPayment = dateToDateTime(customer.getLatestPayment());
        LocalDateTime dateTimeNow = LocalDateTime.now();

        Duration duration = Duration.between(dateTimeNow, latestPayment);

        return Math.abs(duration.toDays());
    }

    // Konverterar LocalDate till LocalDateTime.
    public LocalDateTime dateToDateTime(LocalDate localDate) {
        return localDate.atStartOfDay();
    }
}