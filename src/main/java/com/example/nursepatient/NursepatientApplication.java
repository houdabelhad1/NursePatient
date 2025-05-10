package com.example.nursepatient;

import com.example.nursepatient.console.Menu;
import com.example.nursepatient.entity.User;
import com.example.nursepatient.enums.Role;
import com.example.nursepatient.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class NursepatientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NursepatientApplication.class, args);
        Menu menu = context.getBean(Menu.class);
        menu.start();
    }
}
