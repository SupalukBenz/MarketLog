package util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
// use java.util.logging to simplify project requirements
//import org.apache.log4j.Logger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles reading of application properties from a properties file.
 * Use getProperty()  or getProperties() to access properties. 
 * @author Jim
 */
public class PropertyManager {
	public static final String PROPERTY_FILE = "marketlog.config";

	/** Logging */
	private static Logger log = null;
	
	private static Properties properties = null;
	
	public PropertyManager() { /* don't create properties yet */ }
	
	/** Get a logger.  Create it the first time invoked. */
	private static Logger getLogger() {
		if ( log == null ) log = Logger.getLogger(PropertyManager.class.getName());
		return log;
	}
	
    /**
     * Get all application properties.
     * This method will read properties from a predefined properties file
     * located in the application root directory or the classpath.
     * The property filename may also be specified with a command line option.
     * 
     * @return properties object containing all properties read from configuration
     * @postcondition properties is not null.  It is empty if no configuration file.
     */
    public static Properties getProperties( ) {
    	if ( properties != null ) return properties;
    	
    	// This lets the user override the name of the properties file 
    	// by setting a system property or using -d option of java command.
    	String filename = System.getProperty( PROPERTY_FILE, PROPERTY_FILE ); 
    	
    	properties = new Properties();

    	getLogger().info("Loading properties from "+filename);
    	
    	/**
    	 * First try to load properties from specified filename.
    	 * This works if user specifies absolute path to configuration file;
    	 * OR, program is launched by a "wrapper" that makes current directory
    	 * of class loader be the install directory of the application.
    	 * JSmooth does this.
    	 */
		/**
		 * Use classloader to locate properties file on the classpath.
		 * This is useful for running application inside IDE or from a Jar file.
		 */
    	ClassLoader loader =  PropertyManager.class.getClassLoader();
    	try (InputStream instream = loader.getResourceAsStream(filename) ) {
			properties.load( instream );
			// try-with-resources automatically closes the instream
			getLogger().info("Number of properties read: "+properties.size() );
			return properties;
		} catch (FileNotFoundException e) {
			getLogger().warning( "Couldn't find properties file "+filename);
		} catch (IOException e) {
			getLogger().log(Level.SEVERE, "I/O exception reading properties file "+filename, e);
		}
		
    	// open File directly using path.  Location depends on class loaders "current directory"
		try (InputStream instream = new FileInputStream(filename) ) {		
			properties.load( instream );
		} catch (FileNotFoundException e) {
			getLogger().warning( "Couldn't open properties file "+filename+" "+e);
		} catch (IOException e) {
			getLogger().log(Level.SEVERE, "I/O exception reading properties file "+filename, e);
		}
		
		getLogger().info("Number of properties read: "+properties.size() );
    	return properties;
    }
    
    /**
     * Return a named property
     * @param name is name of the desired property
     * @return string value of the property. May be null
     */
    public static String getProperty(String name) {
    	if ( properties == null ) getProperties();
    	return properties.getProperty(name);
    }
    
    /**
     * Return a named property, with user supplied default if 
     * the requested property is not defined.
     * @param name is name of the desired property
     * @param defaultValue is the default value to return if property is not defined
     * @return string value of the property, or default
     */
    public static String getProperty(String name, String defaultValue) {
    	if ( properties == null ) getProperties();
    	return properties.getProperty(name, defaultValue);
    }

    
    /**
     * Set a property.
     * @param name is name of the desired property
     * @param value is the value to assign to the property
     */
    public static void setProperty(String name, String value) {
    	if ( properties == null ) getProperties();
    	properties.setProperty(name, value);
    }
	
	/** 
	 * Example use and testing.
	 * @param args not used.
	 * @throws IOException propagated from PropertyManager.getProperties()
	 */
	public static void main(String[] args) throws IOException {
		Properties properties = PropertyManager.getProperties();
		if ( properties == null ) System.out.println("BUG: getProperties() returned null.");
		else properties.list( System.out );
	}
}
