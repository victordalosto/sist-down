package dalosto.dnit.sistdown.framework;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BeanFinder {

    public static <T> Set<Class<?>> findClassesAnnotated(String packageName, Class<? extends Annotation> annotationClass) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packageName.replace('.', '/'));
        
        Set<File> directories = new HashSet<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            directories.add(new File(resource.getFile()));
        }
        
        Set<Class<?>> componentClasses  = new HashSet<>();
        for (File directory : directories) {
            List<Class<?>> classes = findClasses(directory, packageName);
            for (Class<?> clazz : classes) {
                if (clazz.isAnnotationPresent(annotationClass)) {
                    componentClasses.add(clazz);
                }
            }
        }
        return componentClasses ;
    }


    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.'
                        + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }

}
