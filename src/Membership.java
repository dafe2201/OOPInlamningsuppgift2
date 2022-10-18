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

    LocalDateTime dateTimeNow = LocalDateTime.now();
    Scanner scanner = new Scanner(System.in);
    List<Customer> customers;



    public Membership() {
        final Path urlAttendance = Path.of("src/Attendance.txt");
        final Path urlCustomers = Path.of("src/Customers.txt");
        FileReader fr = new FileReader();
        customers = fr.createListFromFile(urlCustomers);
    }


    public Customer searchCustomer(List<Customer> customers) {

        while (true) {
            System.out.println("Skriv in personnummer (xxxxxxxxxx), eller hela namnet: ");
            String input = scanner.nextLine();

            for (Customer customer : customers) {
                if (input.equals("3")) {
                    System.exit(0);
                } else if (input.trim().equalsIgnoreCase(customer.getFullName().trim()) ||
                        input.trim().contains(customer.getPersonNr())) {

                    System.out.println("Hittade kund: \n" + "Personnummer: "
                            + customer.getPersonNr() + " Namn: " + customer.getFullName() + "\n");

                    return customer;
                }

            }
            System.out.println("Kunden finns inte i systemet");

        }

    }


    protected String formatLocalDateTime(LocalDateTime dateTimeNow) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return formatter.format(dateTimeNow);
    }

    public void reportAttendanceToFile(String input) {
        final Path urlAttendance = Path.of("src/Attendance.txt");

        try (FileWriter f = new FileWriter(urlAttendance.toFile(), true);
             BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);) {

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

    public String buildAttendance(Customer customer) {
        StringBuilder b = new StringBuilder();

        b.append(formatLocalDateTime(dateTimeNow)).append(", ").append(customer.getPersonNr()).append(", ")
                .append(customer.getFullName()).append("\n");

        return b.toString();
    }

    public void printMembershipStatus(long diff) {
        System.out.println("Senaste betalning skedde för " + diff + " dagar sedan.");
        if (doesntHaveMembership(diff)) {
            System.out.println("Inget medlemskap.");
        } else {
            System.out.println("Aktivt medlemskap.");
        }
    }

    public boolean doesntHaveMembership(long diff){
        return diff > 364;
    }

    public void printReportedAttendance(){
        System.out.println("Kundens närvaro har registrerats.");
    }
    public void printNoReportedAttendance(){
        System.out.println("Inget aktivt medlemsskap.\nIngen närvaro registrerades.");
    }

    public long getDateDifference(Customer customer) {
        LocalDateTime latestPayment = dateToDateTime(customer.getLatestPayment());
        LocalDateTime dateTimeNow = LocalDateTime.now();

        Duration duration = Duration.between(dateTimeNow, latestPayment);

        return Math.abs(duration.toDays());
    }

    public LocalDateTime dateToDateTime(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();

        return localDateTime;
    }

}