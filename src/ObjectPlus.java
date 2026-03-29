import java.io.*;
import java.util.*;

public abstract class ObjectPlus implements Serializable {

    private static Map<Class<? extends ObjectPlus>, List<ObjectPlus>> extents = new HashMap<>();

    public ObjectPlus() {
        if (!extents.containsKey(this.getClass())) {
            extents.put(this.getClass(), new ArrayList<>());
        }
        extents.get(this.getClass()).add(this);
    }

    public static void saveAll(String path) throws IOException {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(path))) {
            stream.writeObject(extents);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadAll(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path))) {
            extents.clear();
            extents = (Map<Class<? extends ObjectPlus>, List<ObjectPlus>>)stream.readObject();
        }
    }

    public static void saveAll() throws IOException {
        saveAll(".extent");
    }

    public static void loadAll() throws IOException, ClassNotFoundException {
        loadAll(".extent");
    }

    public static void print() {
        System.out.println("\n============ ALL EXTENTS ============\n");
        extents.forEach((key, value) -> {
            System.out.println("====== Extent Of " + key + " ======");
            value.forEach(System.out::println);
        });
        System.out.println("\n============ ___________ ============\n");
    }

    public static void clear() {
        extents.clear();
    }

    @SuppressWarnings("unchecked")
    public static<T extends ObjectPlus> List<T> get(Class<? extends ObjectPlus> forClass) throws ClassNotFoundException {
        List<ObjectPlus> classExtent = extents.get(forClass);
        if (classExtent == null) {
            throw new ClassNotFoundException("Couldn't find the class extent for the " + forClass.getName() + ".");
        }
        return classExtent.stream().map((ObjectPlus o) -> (T) forClass.cast(o)).toList();
    }
}