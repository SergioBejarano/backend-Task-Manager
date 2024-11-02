package edu.eci.cvds.taskManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

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
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * Provides a CORS configuration source.
     *
     * <p>This bean defines allowed origins, HTTP methods, headers, and sets
     * credentials support and max age for CORS preflight requests.</p>
     *
     * @return a configured UrlBasedCorsConfigurationSource instance
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Registers a CORS filter.
     *
     * <p>This bean provides a CorsFilter that applies the configured CORS
     * settings across the application.</p>
     *
     * @return a new instance of CorsFilter with specified configuration
     */
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

}
