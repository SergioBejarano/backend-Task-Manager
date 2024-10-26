package edu.eci.cvds.taskManager.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "BatmanYRobinSeQuieren"; // Cambia esto por una clave secreta segura

    /**
     * Genera un token JWT utilizando el nombre de usuario.
     *
     * @param username El nombre de usuario que se incluirá en el token.
     * @return Un token JWT como cadena de texto.
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Guarda el username en el token
                .setIssuedAt(new Date()) // Fecha de creación del token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expira en 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Usa el algoritmo HS256 con una clave secreta
                .compact(); // Construye y retorna el token
    }
}

