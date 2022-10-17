import java.time.LocalDate;

public class Customer {

    private final String personNr;
    private final String fullName;
    private final LocalDate latestPayment;

    public Customer(String personNr, String fullName, LocalDate latestPayment) {
        this.personNr = personNr;
        this.fullName = fullName;
        this.latestPayment = latestPayment;
    }


    public String getPersonNr() {
        return personNr;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getLatestPayment() {
        return latestPayment;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "personNr='" + personNr + '\'' +
                ", fullName='" + fullName + '\'' +
                ", latestPayment=" + latestPayment +
                '}';
    }

}
