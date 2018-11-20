package eg.edu.alexu.csd.oop.draw.cs41.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Muhammad Salah
 * helper class used to hold all static functions
 * used for class loading, jar loading, points operations
 */
public class Helper {

    /**
     * @param interfaceClass the interface to find implementations
     * @param fromPackage the package to look in
     * @return list of classes implementing the interface in the package
     */
    public static List<Class<?>> findClassesImpmenenting(final Class<?> interfaceClass, final Package fromPackage) {
        if (interfaceClass == null) {
            System.out.println("Unknown subclass.");
            return null;
        }

        if (fromPackage == null) {
            System.out.println("Unknown package.");
            return null;
        }

        final List<Class<?>> rVal = new ArrayList<Class<?>>();
        try {
            final Class<?>[] targets = getAllClassesFromPackage(fromPackage.getName());
            if (targets != null) {
                for (Class<?> aTarget : targets) {
                    if (aTarget == null) {
                        continue;
                    }
                    else if (aTarget.equals(interfaceClass)) {
                       // System.out.println("Found the interface definition.");
                        continue;
                    }
                    else if (!interfaceClass.isAssignableFrom(aTarget)) {
                        //System.out.println("Class '" + aTarget.getName() + "' is not a " + interfaceClass.getName());
                        continue;
                    }
                    else {
                        rVal.add(aTarget);
                    }
                }
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("Error reading package name.");
            //System.out.printStackTrace(e, System.out.LOW_LEVEL);
        }
        catch (IOException e) {
            System.out.println("Error reading classes in package.");
            //System.out.printStackTrace(e, System.out.LOW_LEVEL);
        }

        return rVal;
    }

    /**
     * Load all classes from a package.
     * 
     * @param packageName package to look in
     * @return array of classes in a package
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class<?>[] getAllClassesFromPackage(final String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Find file in package.
     * 
     * @param directory
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            }
            else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return filterConcerteClasses(classes);
    }
    
    /**
     * @param classes classes to filter
     * @return list of concrete classes
     */
    private static List<Class<?>> filterConcerteClasses(List<Class<?>> classes){
    	
    	List<Class<?>> filteredClasses = new ArrayList<Class<?>>();
    	
    	for (Class<?> fetchedClass : classes){
    		Integer modifiers = fetchedClass.getModifiers();
    		
    		if (!Modifier.isInterface(modifiers) && !Modifier.isAbstract(modifiers) && Modifier.isPublic(modifiers)){
    			filteredClasses.add(fetchedClass);
    		}
    	}
    
    	return filteredClasses;
    }
    
    /**
     * @param JarPath path to the jar file
     * @param supported a list of shapes to append the classes to
     */
    public static void loadShapesFromJar(String JarPath, List<Class<? extends Shape>> supported) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(JarPath);
            Enumeration<JarEntry> e = jarFile.entries();

            URL[] urls = { new URL("jar:file:" + JarPath+"!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if(je.isDirectory() || !je.getName().endsWith(".class")){
                    continue;
                }
                String className = je.getName().substring(0,je.getName().length()-6);
                className = className.replace('/', '.');
                Class c = cl.loadClass(className);
                if(Shape.class.isAssignableFrom(c)) {
                    supported.add(c);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * @param dir directory to look in
     * @return list of jar files
     */
    public static List<File> getJars(String dir){
        File folder = new File(dir);
        if(! folder.exists()) {
            folder.mkdir();
        }
        File[] files = folder.listFiles();
        List<File> jars = new ArrayList<File>();
        for(File f : files) {
            if(f.isFile()) {
                if(f.getName().endsWith(".jar")) {
                    jars.add(f);
                }
            }
        }
        return jars;
    }
    
    /**
     * @param file
     * @param s
     * 
     * writes a string s to the file
     */
    public static void writeFile(File file, String s) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(s);

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close( );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @param file
     * @return string read from the file
     */
    public static String readFile(File file) {
        StringBuilder text = new StringBuilder();
        FileReader in = null;
        BufferedReader br = null;
        try {
            in = new FileReader(file);
            br = new BufferedReader(in);
            String s;
            while((s = br.readLine()) != null) {
                text.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return text.toString();
    }
}