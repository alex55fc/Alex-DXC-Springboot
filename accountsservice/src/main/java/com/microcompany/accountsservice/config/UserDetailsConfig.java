package com.microcompany.accountsservice.config;

import com.microcompany.accountsservice.exception.GlobalException;
import com.microcompany.accountsservice.model.Customer;
import com.microcompany.accountsservice.model.ERole;
import com.microcompany.accountsservice.persistence.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
public class UserDetailsConfig {
    @Autowired
    private CustomerRepository custoRepo;

    @Bean
    public UserDetailsService UserDetailsService(){
        return new UserDetailsService() {

            BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

            List<Customer> users = List.of(
                    new Customer(1L, "user@mail.com", enc.encode("my_pass"), ERole.CAJERO),
                    new Customer(2L, "admin@mail.com",enc.encode("my_pass"), ERole.DIRECTOR),
                    new Customer(3L, "client@mail.com",enc.encode("my_pass"), ERole.CAJERO),
                    new Customer(4L, "gestor@mail.com",enc.encode("my_pass"), ERole.CAJERO));

            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElseThrow(() -> new UsernameNotFoundException(""));
            }

        };
    }

}
