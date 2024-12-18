package edu.eci.cvds.taskManager.security;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The {@code SecurityConfig} class is a configuration class for Spring Security.
 * It sets up the security configuration for the application, including user authentication
 * and authorization settings.
 */
@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    /**
     * Constructor to inject the UserDetailsService.
     *
     * @param userDetailsService the service used for loading user-specific data
     */
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Defines a PasswordEncoder bean that uses BCrypt for password encoding.
     *
     * @return a PasswordEncoder instance configured with BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures a DaoAuthenticationProvider bean that uses the specified UserDetailsService
     * and PasswordEncoder for authentication.
     *
     * @return a DaoAuthenticationProvider instance
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provides an AuthenticationManager bean using the provided AuthenticationConfiguration.
     *
     * @param authenticationConfiguration the configuration for authentication
     * @return an AuthenticationManager instance
     * @throws Exception if an error occurs during authentication manager creation
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures the security filter chain for HTTP requests.
     * It disables CSRF protection, permits all requests to the application,
     * and enforces authentication for any requests not explicitly permitted.
     *
     * @param http the HttpSecurity object to configure
     * @return a SecurityFilterChain instance
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults());
        return http.build();
    }


    /**
     * Configures the servlet container for the application by adding additional Tomcat connectors.
     *
     * @return a WebServerFactoryCustomizer for TomcatServletWebServerFactory
     */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return factory -> factory.addAdditionalTomcatConnectors(httpConnector());
    }

    /**
     * Creates and configures a new HTTP connector for the Tomcat server.
     *
     * @return a Connector instance configured for HTTP
     */
    private Connector httpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8081);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
}
