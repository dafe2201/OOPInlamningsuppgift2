import java.nio.file.Path;
import java.util.Scanner;

public class FrontDesk {

   protected Membership m;
   protected Scanner scanner;
    final Path urlAttendance = Path.of("src/Attendance.txt");

    public FrontDesk() {
        scanner = new Scanner(System.in);
        m = new Membership();
        handleMenuChoice();
    }

    // Hantera val i huvudmenyn och skicka vidare utifrån input.
    public void handleMenuChoice() {
        while (true) {
            menuText();

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> validateMembership();
                case "2" -> reportAttendance();
                case "3" -> System.exit(0);
                default -> System.out.println("\nHåll dig till valen i menyn");
            }
        }
    }

    // Om man väljer menyval "1".
    private void validateMembership() {
        m.printMembershipStatus(m.getDateDifference(m.searchCustomer(m.customers,false)));
    }

    // Om man väljer menyval "2".
    private void reportAttendance() {
        Customer customer = m.searchCustomer(m.customers, false);
        long diff = m.getDateDifference(customer);

        if (!m.doesntHaveMembership(diff)) {
            m.printReportedAttendance();
            logSession(customer);
        }
        else{
            m.printNoReportedAttendance();
        }
    }

    //Fortsättning på menyval "2".
    private void logSession(Customer customer){
        m.reportAttendanceToFile(m.buildAttendance(customer),urlAttendance,false);
    }

    public void menuText() {
        System.out.println("\n_-Best Gym Ever-_");
        System.out.println("""
                1. Sök efter medlemskapsstatus.
                2. Rapportera närvaro.
                3. Avsluta""");
    }

}
