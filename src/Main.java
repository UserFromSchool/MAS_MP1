import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] argv) {

        Employee emp1 = new Employee("Jack", "Jason", "jack.jason@gmail.com", 10_000, LocalDate.now(), new ArrayList<>());
        Employee emp2 = new Employee("Ana", "Ger", "ana.ger@gmail.com", 4_000, LocalDate.now(), new ArrayList<>(), 14903673);

        emp1.addPhoneNumber("507 407 307");
        emp1.addPhoneNumber("800 900 800");
        emp1.addPhoneNumber("100 100 100");
        emp1.removePhoneNumber("507 407 307");

        System.out.println("Usage of normal attribute: ");
        System.out.println(emp1.getName());
        System.out.println(emp1.getSurname());
        System.out.println(emp1.getEmail());
        System.out.println();


        System.out.println("Usage of derived attribute: ");
        System.out.println("Employed for " + emp1.getYearsOfEmployment() + " years.");
        System.out.println();


        System.out.println("Usage of complex attribute: ");
        System.out.println("Hired at " + emp1.getHiredAt());
        System.out.println();


        System.out.println("Usage of multivalue attribute: ");
        System.out.println("Phone numbers: " + String.join(", ", emp1.getPhoneNumbers()));
        System.out.println();


        System.out.println("Usage of class attribute: ");
        System.out.println("Maximum bonus allowed: " + Employee.maxBonus);
        System.out.println();


        System.out.println("Usage of optional attribute: ");
        var license1 = emp1.getLicenseNumber();
        var license2 = emp2.getLicenseNumber();
        System.out.println("First employee license number " + (license1.isPresent() ? license1.get() : "No license"));
        System.out.println("Second employee license number " + (license2.isPresent() ? license2.get() : "No license"));
        System.out.println();


        System.out.println("Usage of class method: ");
        double totalBonusMoney = 10_000;
        System.out.println("I have $" + totalBonusMoney + " to distribute equally as bonuses.");
        System.out.println("Therefore, each employee gets $" + Employee.calculateUniformBonus(totalBonusMoney));
        System.out.println();


        System.out.println("Usage of toString() override method: ");
        System.out.println(emp1);
        System.out.println(emp2);
        System.out.println();


        System.out.println("Class extent persistency showcase: ");
        System.out.println("Current objects: ");
        ObjectPlus.print();
        System.out.println("Saving the extent (all at once)...");
        try {
            ObjectPlus.saveAll();
        } catch (IOException ex) {
            System.out.println("Could not save the file extent.");
            ex.printStackTrace();
            System.exit(1);
        }
        System.out.println("Succeeded.");
        System.out.println("Clearing extent to ensure loading does not use pre-existing data...");
        ObjectPlus.clear();
        ObjectPlus.print();
        System.out.println("Loading the extent...");
        try {
            ObjectPlus.loadAll();
        } catch (IOException ex) {
            System.out.println("Could not load the file extent.");
            ex.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException ex) {
            System.out.println("Loaded extent contains incompatible class definitions. Probably the extent is obsolete.");
            System.exit(1);
        }
        System.out.println("Succeeded.");
        System.out.println("Showing loaded extent...");
        ObjectPlus.print();
    }

}
