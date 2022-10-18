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
    Path attendanceTestUrl = Path.of("BestGymTest/AttendanceTest.txt");
    List<Customer> customers;


    public MembershipTest() {
        customers = fr.createListFromFile(customerTestUrl);
    }

    @Test
    public void createListFromFileTest() {
        List<Customer> customersList;
        customersList = fr.createListFromFile(customerTestUrl);

        customers.forEach(Assertions::assertNotNull);
        assertNotNull(customers);
        assertEquals(customersList.getClass(), customers.getClass());
        assertEquals(3, customers.size());
        assertEquals("Chamade Coriola", customers.get(2).getFullName());
        assertNotNull(customers.get(1).getPersonalNr());
        assertNotNull(customers.get(2).getLatestPayment());

    }

    @Test
    public void doesSearchCustomerReturnTypeCustomer() {
        Customer customer = customers.get(0);

        Assertions.assertEquals(customer.getClass(), m.searchCustomer(customers, true).getClass());
        assertNotNull(customer);
    }

    //TODO: Kontrollera på onsdag i syfte att säkerställa att testet går igenom på ett annat datum.
    @Test
    public void getDateDifferenceTest() {
        LocalDate localDate = LocalDate.now();
        Customer customer = new Customer("911225", "Olle Ollesson", localDate);

        System.out.println(m.getDateDifference(customer));
        Assertions.assertEquals(0, m.getDateDifference(customer));
    }

    @Test
    public void writeToFileTest() {
        m.reportAttendanceToFile(
                """
                        8204021234, Bear Belle
                        2019-12-02
                        8512021234, Chamade Coriola
                        2018-03-12""",
                attendanceTestUrl, true);

        List<Customer> allCustomersTest = fr.createListFromFile(attendanceTestUrl);
        Customer customerTwo = allCustomersTest.get(0);
        Customer customerOne = allCustomersTest.get(1);

        assertNotNull(customerOne);
        assertNotNull(customerTwo);
        assertEquals("8512021234", customerOne.getPersonalNr());
    }

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
        String val2 = localTime + ", " + customerTwo.getPersonalNr() + ", " + customerTwo.getFullName() + "\n";
        String val3 = localTime + ". " + customerTwo.getPersonalNr() + ", " + customerTwo.getFullName() + "\n";

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

