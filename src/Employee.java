import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        if (name == null || name.isBlank()) {
            throw new RequiredFieldException("name", name);
        }
        this.name = name;

        if (surname == null || surname.isBlank()) {
            throw new RequiredFieldException("surname", surname);
        }
        this.surname = surname;

        this.salary = salary;

        if (email == null || email.isBlank()) {
            throw new RequiredFieldException("email", email);
        }
        this.email = email;

        if (hiredAt == null) {
            throw new RequiredFieldException("hiredAt", null);
        }
        this.hiredAt = hiredAt;

        this.phoneNumbers = Objects.requireNonNullElseGet(phoneNumbers, ArrayList::new);
        if (this.phoneNumbers.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Specified employee's phone numbers contain null values.");
        }
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
            throw new IllegalArgumentException("Salary must be greater than 0.");
        }
        this.salary = salary;
    }

    public void addPhoneNumber(String number) {
        if (phoneNumbers.stream().anyMatch((n) -> n.equals(number))) {
            throw new IllegalArgumentException("Number " + number + " was already registered for " + name + " " + surname + ".");
        }
        phoneNumbers.add(number);
    }

    public void removePhoneNumber(String number) {
        if (phoneNumbers.stream().noneMatch((n) -> n.equals(number))) {
            throw new IllegalArgumentException("Can't delete number " + number + " as it was not registerd for " + name + " " + surname + ".");
        }
        phoneNumbers.removeIf((n) -> n.equals(number));
    }

    public int getYearsOfEmployment() {
        return LocalDate.now().minusYears(hiredAt.getYear()).getYear();
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
        builder.append("-- License Number       ").append(licenceNumber).append('\n');
        return builder.toString();
    }

    public static double calculateUniformBonus(double totalBonusToDistribute) {
        Optional<List<ObjectPlus>> employees = ObjectPlus.get(Employee.class);

        if (totalBonusToDistribute < 0) {
            throw new IllegalArgumentException("Total bonus to distribute must be greater than 0.");
        }

        if (employees.isEmpty()) {
            throw new IllegalStateException("There are no employee registered in the system.");
        }

        return totalBonusToDistribute / employees.get().size();
    }
}
