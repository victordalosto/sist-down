package dalosto.sistdown.framework;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import dalosto.sistdown.action.Teste;
import dalosto.sistdown.framework.annotations.Autowired;
import dalosto.sistdown.framework.annotations.Component;


public class ApplicationContext {

    private static Set<Object> beans = new HashSet<>();


    public static void initialize() throws Exception {
        scanComponentes(Component.class);
        wireAttributes(Autowired.class);
        teste();
    }


    private static void scanComponentes(Class<? extends Annotation> annotationClass) throws Exception {
        Set<Class<?>> classes = BeanFinder.findClassesAnnotated("dalosto.sistdown", annotationClass);
        for (Class<?> clazz : classes) {
            beans.add(clazz.getDeclaredConstructor().newInstance());
        }
    }


    private static void wireAttributes(Class<? extends Annotation> annotationClass) throws IllegalArgumentException, IllegalAccessException {
        for (Object bean : beans) {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(annotationClass)) {
                    for (Object managedBean : beans) {
                        if (field.getType().isAssignableFrom(managedBean.getClass())) {
                            field.setAccessible(true);
                            field.set(bean, managedBean);
                            break;
                        }
                    }
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
                        } else if (Set.class.isAssignableFrom(field.getType())) {
                            field.set(bean, new HashSet<>(matchingObjects));
                        }
                    }
                }
            }
        }
    }


    public static void teste() {
        System.out.println();
        for (Object c : beans) {
            // System.out.println(c.getClass().getSimpleName());
            if (c.getClass().getSimpleName().equals("Teste")) {
                Teste a = (Teste) c;
                try {
                    a.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
