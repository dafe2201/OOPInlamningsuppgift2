import java.time.LocalDate;

public class Customer {

    private final String personalNr;
    private final String fullName;
    private final LocalDate latestPayment;

    public Customer(String personNr, String fullName, LocalDate latestPayment) {
        this.personalNr = personNr;
        this.fullName = fullName;
        this.latestPayment = latestPayment;
    }


    public String getPersonalNr() {
        return personalNr;
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
                "personNr='" + personalNr + '\'' +
                ", fullName='" + fullName + '\'' +
                ", latestPayment=" + latestPayment +
                '}';
    }

}
