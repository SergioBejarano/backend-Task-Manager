package edu.eci.cvds.taskManager.security;

import edu.eci.cvds.taskManager.model.User;
import edu.eci.cvds.taskManager.repositories.mongo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * The {@code UserDetailsServiceImpl} class is an implementation of the Spring Security
 * {@code UserDetailsService} interface. It is responsible for loading user-specific data
 * during the authentication process.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor to inject the UserRepository.
     *
     * @param userRepository the repository used for accessing user data
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username. This method is called by Spring Security during the authentication
     * process to retrieve user details.
     *
     * @param username the username of the user to be loaded
     * @return a UserDetails object containing user information
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }
}
