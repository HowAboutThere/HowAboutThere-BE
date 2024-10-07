package com.kosa2team.howaboutthere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HowAboutThereApplication {

    public static void main(String[] args) {
        SpringApplication.run(HowAboutThereApplication.class, args);
    }

}
