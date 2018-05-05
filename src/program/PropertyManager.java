package program;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyManager {

    public static final String PROPERTY_FILE = "marketlog.properties";
    private static Logger log = null;
    private static Properties properties = null;

    public PropertyManager() {

    }

    private static Logger getLogger() {
        if ( log == null ) log = Logger.getLogger(PropertyManager.class.getName());
        return log;
    }

    public static Properties getProperties(){
        if(properties != null) return properties;
        String filename = System.getProperty( PROPERTY_FILE, PROPERTY_FILE );
        properties = new Properties();
        getLogger().info("Loading properties from " + filename);

        ClassLoader loader = PropertyManager.class.getClassLoader();
        try (InputStream instream = loader.getResourceAsStream(filename);) {

            properties.load(instream);
            getLogger().info("Number of properties read: " + properties.size());

            return properties;
        }catch (FileNotFoundException fe){
            getLogger().warning( "Couldn't find properties file "+filename);

        } catch (IOException ioe){
            getLogger().log(Level.SEVERE, "I/O exception reading properties file "+filename, ioe);
        }

        try (InputStream instream = new FileInputStream(filename)){

            properties.load(instream);

        }catch (FileNotFoundException fe){
            getLogger().warning( "Couldn't find properties file "+filename);

        } catch (IOException ioe){
            getLogger().log(Level.SEVERE, "I/O exception reading properties file "+filename, ioe);
        }

        getLogger().info("Number of properties read: "+properties.size() );

        return properties;
    }

    public static String getProperty(String name, String defaultValue){
        if(properties == null) getProperties();
        return properties.getProperty(name, defaultValue);
    }
    public static String getProperty(String name){
        if(properties == null) getProperties();
        return properties.getProperty(name);
    }

    public static void setProperty(String key, String value){
        if(properties == null) getProperties();
        properties.setProperty(key, value);
    }

    public static void main(String[] args) {
        Properties property = PropertyManager.getProperties();
        if(property == null) {
            System.out.println("Properties return null");
        }
        else {
            property.list(System.out);

            System.out.println("Test username: " + PropertyManager.getProperty("jdbc.user"));
        }

    }

}
