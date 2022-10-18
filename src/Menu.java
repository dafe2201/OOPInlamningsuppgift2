import java.util.Scanner;

public class Menu {

    Membership m;
    Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
        m = new Membership();
        System.out.println(m.customers); //FIXME: Endast för test. Ta bort när klar.
        handleMenuChoice();
    }

    public void handleMenuChoice() {
        while (true) {
            menuText();

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> handleCustomerSearch();
                case "2" -> evaluateIfMember();
                case "3" -> System.exit(0);
                default -> System.out.println("\nHåll dig till valen i menyn");
            }
        }
    }

    private void handleCustomerSearch() {
        m.printMembershipStatus(m.getDateDifference(m.searchCustomer(m.customers)));
    }

    private void evaluateIfMember() {
        Customer customer = m.searchCustomer(m.customers);
        long diff = m.getDateDifference(customer);

        if (!m.doesntHaveMembership(diff)) {
            m.printReportedAttendance();
            logSession(customer);
        }
        else{
            m.printNoReportedAttendance();
        }
    }

    private void logSession(Customer customer){
        m.reportAttendanceToFile(m.buildAttendance(customer));
    }

    public void menuText() {
        System.out.println("\n_-Best Gym Ever-_");
        System.out.println("""
                1. Sök efter medlemskapsstatus.
                2. Rapportera närvaro.
                3. Avsluta""");
    }

}
