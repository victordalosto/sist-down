package dalosto.sistdown.service;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class ImplementationFinder {

    @SuppressWarnings("unchecked")
    public static <T> List<Class<? extends T>> findClases(String packageName, Class<T> filterClass) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packageName.replace('.', '/'));
        
        List<File> directories = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            directories.add(new File(resource.getFile()));
        }

        List<Class<?>> classes = new ArrayList<>();
        for (File directory : directories) {
            classes.addAll(findClasses(directory, packageName));
        }
        
        // Filter classes that implement the specified interface
        List<Class<? extends T>> implementations = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if (filterClass.isAssignableFrom(clazz) && !clazz.equals(filterClass)) {
                implementations.add((Class<? extends T>) clazz);
            }
        }
        return implementations;
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
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }

}
