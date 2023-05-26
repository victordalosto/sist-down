package dalosto.dnit.sistdown.framework;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.framework.annotations.Order;


/**
 * This is the main class of the application. 
 * It is responsible for initializing the project.
 * It is also responsible for injecting the dependencies of the classes.
 * Author: VictorDalosto
 */
public class ApplicationContext {

    private static Set<Object> beans = new HashSet<>();

    private static String packageName = "dalosto.dnit.sistdown";


    public static void initialize() throws Exception {
        scanComponentes(Component.class);
        wireAttributes(Autowired.class);
    }


    private static void scanComponentes(Class<? extends Annotation> annotationClass) throws Exception {
        Set<Class<?>> classes = BeanFinder.findClassesAnnotated(packageName, annotationClass);
        for (Class<?> clazz : classes) {
            beans.add(clazz.getDeclaredConstructor().newInstance());
        }
    }


    private static void wireAttributes(Class<? extends Annotation> annotationClass) throws IllegalArgumentException, IllegalAccessException {
        for (Object bean : beans) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                injectBean(annotationClass, bean, field);
            }
        }
    }


    private static void injectBean(Class<? extends Annotation> annotationClass, Object bean, Field field) throws IllegalAccessException {
        if (field.isAnnotationPresent(annotationClass)) {
            injectBeanInFieldIfMatches(bean, field);
            if (List.class.isAssignableFrom(field.getType())
               || Set.class.isAssignableFrom(field.getType())) {
                ParameterizedType collectionType = (ParameterizedType) field.getGenericType();
                Class<?> collectionGenericType = (Class<?>) collectionType.getActualTypeArguments()[0];

                List<Object> matchingObjects = new ArrayList<>();
                for (Object obj : beans) {
                    if (collectionGenericType.isAssignableFrom(obj.getClass())) {
                        matchingObjects.add(obj);
                    }
                }
                field.setAccessible(true);
                
                if (List.class.isAssignableFrom(field.getType())) {
                    field.set(bean, matchingObjects);
                    Collections.sort(matchingObjects, new Comparator<Object>() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            return Order.orderComparator.compare(o1.getClass(),
                                    o2.getClass());
                        }
                    });
                } else if (Set.class.isAssignableFrom(field.getType())) {
                    field.set(bean, new HashSet<>(matchingObjects));
                }
            }
        }
    }


    private static void injectBeanInFieldIfMatches(Object bean, Field field) throws IllegalAccessException {
        for (Object managedBean : beans) {
            if (field.getType().isAssignableFrom(managedBean.getClass())) {
                field.setAccessible(true);
                field.set(bean, managedBean);
                field.setAccessible(false);
                break;
            }
        }
    }



}
