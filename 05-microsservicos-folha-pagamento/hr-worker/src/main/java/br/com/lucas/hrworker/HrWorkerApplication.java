package br.com.lucas.hrworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Microsserviço de funcionários.
 * O registro no Eureka acontece sozinho só por ter o eureka-client no classpath.
 * Sua parte: model, repository, service, controller e exception (ver README).
 */
@SpringBootApplication
public class HrWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrWorkerApplication.class, args);
    }
}
