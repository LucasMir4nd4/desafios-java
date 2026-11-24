package br.com.lucas.hrapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API Gateway. Infraestrutura pronta: as rotas estão no application.yml.
 * Também serve o front de testes (static/index.html) e o Swagger agregado.
 */
@SpringBootApplication
public class HrApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrApiGatewayApplication.class, args);
    }
}
