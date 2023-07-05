public class Main {
    public static void main(String[] args) {
        Dummy dummy = new Dummy(1, 2, 0.3, "sa", "da");
        Serializer serializer = new Serializer();
        serializer.setObject(dummy);
    }
}