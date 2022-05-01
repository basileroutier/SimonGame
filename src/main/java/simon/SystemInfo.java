package simon;

/**
 * System info from JavaFx class creation
 * @author basile
 */
public class SystemInfo {

    /**
     * Return the javaversion
     * @return javaversion
     */
    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Return the javafx version
     * @return javafx version
     */
    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

}