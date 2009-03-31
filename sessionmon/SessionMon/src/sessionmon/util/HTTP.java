package sessionmon.util;

import java.security.Provider;
import java.security.Security;

public class HTTP {

    /** The name of the JSSE class which provides support for SSL. */
    private final static String SunJSSE_PROVIDER_CLASS = "com.sun.net.ssl.internal.ssl.Provider";
    /** The name of the JSSE class which supports the https protocol. */
    private final static String SSL_PROTOCOL_HANDLER = "com.sun.net.ssl.internal.www.protocol";
    /**
     * The name of the system parameter used by java.net to locate protocol
     * handlers.
     */
    private final static String PROTOCOL_HANDLER_PKGS = "java.protocol.handler.pkgs";
    /**
     * Verifies to see if HTTPS support can be enabled.
     */
    public static void verifyHttpsSupport() {
        try {
            Class providerClass = Class.forName(SunJSSE_PROVIDER_CLASS);

            if (!hasProvider(providerClass)) {
                Security.addProvider((Provider) providerClass.newInstance());
            }

            registerSSLProtocolHandler();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "https support requires the Java Secure Sockets Extension. See http://java.sun.com/products/jsse");
        } catch (Throwable e) {
            throw new RuntimeException(
                "Unable to enable https support. Make sure that you have installed JSSE " +
                "as described in http://java.sun.com/products/jsse/install.html: " +
                e);
        }
    }
    
    /**
     * Checks to see if the input provider class exits.
     *
     * @param providerClass Input provider class
     *
     * @return Boolean flag that indicates whether the input provider class exists or not.
     */
    private static boolean hasProvider(Class providerClass) {
        Provider[] list = Security.getProviders();

        for (int i = 0; i < list.length; i++) {
            if (list[i].getClass().equals(providerClass)) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Sets SSL PROTOCOL_HANDLER to the system property.
     */
    private static void registerSSLProtocolHandler() {
        String list = System.getProperty(PROTOCOL_HANDLER_PKGS);

        if ((list == null) || (list.length() == 0)) {
            System.setProperty(PROTOCOL_HANDLER_PKGS, SSL_PROTOCOL_HANDLER);
        } else if (list.indexOf(SSL_PROTOCOL_HANDLER) < 0) {
            System.setProperty(PROTOCOL_HANDLER_PKGS,
                SSL_PROTOCOL_HANDLER + " | " + list);
        }
    }
}
