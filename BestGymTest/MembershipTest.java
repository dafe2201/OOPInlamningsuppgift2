import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MembershipTest {

    Membership m = new Membership();
    FileReader fr = new FileReader();
    Path customerTestUrl = Path.of("BestGymTest/CustomersTest.txt");

    List<Customer> customers;


    public MembershipTest() {
        customers = fr.createListFromFile(customerTestUrl);
    }

    @Test
    public void createListFromFileTest(){
        List<Customer> customersList;
        customersList = fr.createListFromFile(customerTestUrl);

        assertNotNull(customers);
        assertEquals(customersList.getClass(),customers.getClass());
        assertEquals(3,customers.size());
        assertEquals("Chamade Coriola",customers.get(2).getFullName());
        assertNotNull(customers.get(1).getPersonNr());
        assertNotNull(customers.get(2).getLatestPayment());
    }


    //TODO: Skriv en parameter boolean "isTest" till konsturktorn för searchCustomer. Lägg en if sats som säger vad den
    //TODO: ska göra istället och ta bort ALL input.
    //TODO: Vill jag testa något annat?
    @Test
    public void doesSearchCustomerReturnTypeCustomer(){
        Customer customer = customers.get(0);

        Assertions.assertEquals(customer.getClass(), m.searchCustomer(customers).getClass());
    }

    //TODO: Kontrollera på onsdag i syfte att säkerställa att testet går igenom på ett annat datum.
    @Test
    public void getDateDifferenceTest() {
        LocalDate localDate = LocalDate.now();
        Customer customer = new Customer("911225","Olle Ollesson",localDate);

        System.out.println(m.getDateDifference(customer));
        Assertions.assertEquals(0,m.getDateDifference(customer));
    }

    @Test
    public void reportAttendancetoFileTest(){

    }

    /*    public void reportAttendanceToFile(String input) {
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

    }*/

    @Test
    public void formatLocalDateTimeTest() {

        assert (m.formatLocalDateTime(LocalDateTime.now()).length() == 16);
        assert (m.formatLocalDateTime(LocalDateTime.now()).length() != 10);
    }


    @Test
    public void hasMembershipTest() {
        long diff = 144;

        Assertions.assertFalse(m.doesntHaveMembership(diff));
        Assertions.assertTrue(m.doesntHaveMembership(400));
    }

    @Test
    public void buildAttendanceTest() {
        Customer customerTwo = customers.get(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String localTime = formatter.format(LocalDateTime.now());

        String val1 = m.buildAttendance(customerTwo);
        String val2 = localTime + ", " + customerTwo.getPersonNr() + ", " + customerTwo.getFullName() + "\n";
        String val3 = localTime + ". " + customerTwo.getPersonNr() + ", " + customerTwo.getFullName() + "\n";


        assertEquals(val2, val1);
        assertNotEquals(val3, val1);
    }

    @Test
    public void dateToDateTimeTest() {
        LocalDate localDate = LocalDate.now();
        String localDateTimeActual = m.dateToDateTime(LocalDate.now()).toString();
        String localDateTimeExpected = localDate.atStartOfDay().toString();

        assertEquals(localDateTimeExpected, localDateTimeActual);
    }


}

