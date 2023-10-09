package dev.astranfalio.teioc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@Slf4j
public class TeiocApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TeiocApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Hello world!");
    }
}
