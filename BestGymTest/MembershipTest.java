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
    public void getDateDifferenceTest() {
        Customer customerOne = customers.get(0);
        long expectedDays = 108;

        Assertions.assertEquals(expectedDays, m.getDateDifference(customerOne));
        assertNotEquals(300, m.getDateDifference(customerOne));
    }

    //TODO VARFÖR SKA DENNA ENS FÅ EXISTERA
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

