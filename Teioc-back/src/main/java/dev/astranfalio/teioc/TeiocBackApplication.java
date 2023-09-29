package dev.astranfalio.teioc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TeiocBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeiocBackApplication.class, args);
    }

}
