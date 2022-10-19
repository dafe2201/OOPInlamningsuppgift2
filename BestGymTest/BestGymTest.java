import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BestGymTest {

    Membership m = new Membership();
    FileReader fr = new FileReader();
    Path customerTestUrl = Path.of("BestGymTest/CustomersTest.txt");
    Path attendanceTestUrl = Path.of("BestGymTest/AttendanceTest.txt");
    List<Customer> customers;


    public BestGymTest() {
        customers = fr.createListFromFile(customerTestUrl);
    }

    //Testar om List och Customer-objekt kan genereras från fil, samt att datan tas in på rätt sätt, i rätt format.
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

    //Testar att metoden fungerar genom att kontrollera att den kan returnera rätt typ.
    @Test
    public void doesSearchCustomerReturnTypeCustomer() {
        Customer customer = customers.get(0);

        Assertions.assertEquals(customer.getClass(), m.searchCustomer(customers, true).getClass());
        assertNotNull(customer);
    }

    //Testar att metod för att räkna ut differensen i dagar fungerar. "Olles" long latestPayment är satt till dagens
    //datum för att testet ska fungera varje dag utan att manuellt behöva ändras.
    @Test
    public void getDateDifferenceTest() {
        LocalDate localDate = LocalDate.now();
        Customer customer = new Customer("911225", "Olle Ollesson", localDate);

        System.out.println(m.getDateDifference(customer));
        Assertions.assertEquals(0, m.getDateDifference(customer));
    }

    // Kontrollerar att metoden m.reportAttendanceToFile kan skriva data till en fil. Formatet på data är för detta
    // test irrelevant. Se buildAttendanceTest().
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

    // Kontrollerar att datum och tid formateras på rätt sätt.
    @Test
    public void formatLocalDateTimeTest() {

        assert (m.formatLocalDateTime(LocalDateTime.now()).length() == 16);
        assert (m.formatLocalDateTime(LocalDateTime.now()).length() != 10);
    }

    // Kontrollerar att metoden som räknar ut huruvida en Customer som finns i systemet har ett aktivt medlemskap.
    @Test
    public void hasMembershipTest() {
        long diff = 144;

        Assertions.assertFalse(m.doesntHaveMembership(diff));
        Assertions.assertTrue(m.doesntHaveMembership(400));
    }

    // Kontrollerar att strängen som ska skrivas till Attendance.txt formateras på rätt sätt.
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

    // Testar att LocalDate kan formateras till LocalDateTime.
    @Test
    public void dateToDateTimeTest() {
        LocalDate localDate = LocalDate.now();
        String localDateTimeActual = m.dateToDateTime(LocalDate.now()).toString();
        String localDateTimeExpected = localDate.atStartOfDay().toString();

        assertEquals(localDateTimeExpected, localDateTimeActual);
    }
}

