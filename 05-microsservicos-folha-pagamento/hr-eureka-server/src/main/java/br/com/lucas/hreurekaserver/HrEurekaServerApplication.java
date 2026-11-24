package br.com.lucas.hreurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/** Service registry. Infraestrutura pronta: não precisa mexer. */
@EnableEurekaServer
@SpringBootApplication
public class HrEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrEurekaServerApplication.class, args);
    }
}
