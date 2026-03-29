import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Employee extends ObjectPlus {

    public static final double maxBonus = 10_000;

    private final String name;
    private final String surname;
    private final LocalDate hiredAt;
    private final String email;
    private final List<String> phoneNumbers;
    private double salary;
    private Integer licenceNumber;

    Employee(String name, String surname, String email, double salary, LocalDate hiredAt, List<String> phoneNumbers) {
        super();

        if (salary < 0) {
            throw new IllegalArgumentException("The salary should be greater than or equal to 0.");
        }

        if (!phoneNumbers.stream().allMatch((number) -> number.matches("\\+48\\s?\\d{3}\\s?\\d{3}\\s?\\d{3}"))) {
            throw new IllegalArgumentException("There are phone numbers with invalid format. Correct format is e.g. +48 ddd ddd ddd");
        }

        if (!email.matches("[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException(("The email " + email + " is not a valid email address."));
        }

        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.email = email;
        this.hiredAt = hiredAt;
        this.phoneNumbers = new ArrayList<>(phoneNumbers);
    }

    Employee(String name, String surname, String email, double salary, LocalDate hiredAt, List<String> phoneNumbers, int licenseNumber) {
        this(name, surname, email, salary , hiredAt, phoneNumbers);
        this.licenceNumber = licenseNumber;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public double getSalary() { return salary; }
    public LocalDate getHiredAt() { return hiredAt; }
    public List<String> getPhoneNumbers() { return List.copyOf(phoneNumbers); }
    public Optional<Integer> getLicenseNumber() { return Optional.ofNullable(licenceNumber); }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary must be greater than or equal to 0.");
        }
        this.salary = salary;
    }

    public void addPhoneNumber(String number) {
        if (phoneNumbers.stream().anyMatch((n) -> n.equals(number))) {
            throw new IllegalArgumentException("Number " + number + " was already registered for " + name + " " + surname + ".");
        }
        if (!number.matches("\\+48\\s?\\d{3}\\s?\\d{3}\\s?\\d{3}")) {
            throw new IllegalArgumentException("The phone number " + number + " has invalid format. Correct format is e.g. +48 ddd ddd ddd");
        }
        phoneNumbers.add(number);
    }

    public void removePhoneNumber(String number) {
        if (phoneNumbers.stream().noneMatch((n) -> n.equals(number))) {
            throw new NoSuchElementException("Can't delete number " + number + " as it was not registered for " + name + " " + surname + ".");
        }
        phoneNumbers.removeIf((n) -> n.equals(number));
    }

    public int getYearsOfEmployment() {
        return (int) ChronoUnit.YEARS.between(hiredAt, LocalDate.now());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-- EMPLOYEE").append('\n');
        builder.append("-- Name                 ").append(name).append('\n');
        builder.append("-- Surname              ").append(surname).append('\n');
        builder.append("-- Email                ").append(email).append('\n');
        builder.append("-- Hired At             ").append(hiredAt).append('\n');
        builder.append("-- Years Of Employment  ").append(getYearsOfEmployment()).append('\n');
        builder.append("-- Phone Numbers        ").append(String.join(", ", phoneNumbers)).append('\n');
        builder.append("-- License Number       ").append(getLicenseNumber().isPresent() ? licenceNumber : "no license").append('\n');
        return builder.toString();
    }

    public static double calculateUniformBonus(double totalBonusToDistribute) {
        List<Employee> employees;
        try {
             employees = ObjectPlus.get(Employee.class);
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("The application does not have any employees registered in the system.");
        }

        if (totalBonusToDistribute < 0) {
            throw new IllegalArgumentException("Total bonus to distribute must be greater than or equal to 0.");
        }

        return totalBonusToDistribute / employees.size();
    }
}
