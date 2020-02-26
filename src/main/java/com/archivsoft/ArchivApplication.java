package com.archivsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ArchivApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchivApplication.class, args);
    }

}
