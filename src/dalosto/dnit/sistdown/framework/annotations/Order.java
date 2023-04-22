package dalosto.dnit.sistdown.framework.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;


/**
 * Sugere uma ordem para a classe ser injetada
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Order {

    int value();

    public static Comparator<Class<?>> orderComparator = new Comparator<Class<?>>() {

        @Override
        public int compare(Class<?> c1, Class<?> c2) {
            Order o1 = c1.getAnnotation(Order.class);
            Order o2 = c2.getAnnotation(Order.class);
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null) {
                return 1;
            } else if (o2 == null) {
                return -1;
            } else {
                return Integer.compare(o1.value(), o2.value());
            }
        }
    };
}
