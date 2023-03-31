package dalosto.sistdown.framework;
import java.util.Comparator;
import dalosto.sistdown.framework.annotations.Order;


public class OrderComparator {

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
