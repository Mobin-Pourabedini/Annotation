import javax.xml.datatype.DatatypeConstants;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Serializer {
    class MyField {
        private String name;
        private Object value;

        public MyField(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return name + ":" + value;
        }
    }
    private List<MyField> myFields = new ArrayList<>();
    private Object object;
    public String getSerializedString() throws IllegalAccessException {
        Collections.sort(myFields, Comparator.comparing((MyField x) -> x.name));
        List<String> result = new ArrayList<>();
        for (MyField field: myFields) {
            result.add(field.toString());
        }
        return String.join(",", result);
    }

    public Object getObject() {
        return this.object;
    }

    public void setObject(Object object) {
        this.object = object;
        myFields.clear();
        for (Field f: object.getClass().getDeclaredFields()) {
            String name = f.getName();
            Rename rename = f.getAnnotation(Rename.class);
            if (rename != null && !rename.name().equals("")) {
                name = rename.name();
            }
            Class claz = f.getType();
            Object value = new Object();
            try {
                value = f.get(object);
            } catch (IllegalAccessException e) {
                f.setAccessible(true);
                try {
                    value = f.get(object);
                }catch (IllegalAccessException e1) {
                    System.out.println("wtf");
                }
                f.setAccessible(false);
            }
            MyField myField = new MyField(name, value);
            myFields.add(myField);
//            System.out.println(name + " " + value);
        }
    }
}


