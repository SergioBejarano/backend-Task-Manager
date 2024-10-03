package edu.eci.cvds.taskManager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The {@code CorsConfig} class configures Cross-Origin Resource Sharing (CORS)
 * settings for the Spring web application.
 * <p>
 * This configuration allows the application to handle CORS requests from
 * different origins, specifying the allowed methods, headers, and other CORS
 * settings.
 * </p>
 *
 * <p>
 * The CORS settings allow requests from any origin and specify the HTTP
 * methods that are permitted.
 * </p>
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings.
     *
     * <p>This method sets up CORS for all endpoints (/**), allowing requests
     * from any origin with specified HTTP methods and headers.</p>
     *
     * @param registry the CORS registry to customize
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
