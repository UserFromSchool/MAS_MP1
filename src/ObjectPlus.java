import java.io.*;
import java.util.*;

public abstract class ObjectPlus implements Serializable {

    private static Map<Class<?>, List<ObjectPlus>> extents = new HashMap<>();

    public ObjectPlus() {
        if (!extents.containsKey(this.getClass())) {
            extents.put(this.getClass(), new ArrayList<>());
        }
        extents.get(this.getClass()).add(this);
    }

    public static void saveAll(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extents);
    }

    @SuppressWarnings("unchecked")
    public static void loadAll(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extents = (Map<Class<?>, List<ObjectPlus>>)stream.readObject();
    }

    public static void saveAll() throws IOException {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(".extent"))) {
            saveAll(stream);
        }
    }

    public static void loadAll() throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(".extent"))) {
            loadAll(stream);
        }
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

    public static Optional<List<ObjectPlus>> get(Class<?> forClass) {
        return Optional.ofNullable(extents.get(forClass));
    }
}