package br.com.lucas.hrpayroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Microsserviço de folha de pagamento.
 * O Feign já está habilitado: ele procura interfaces de cliente dentro deste pacote.
 * Sua parte: client, dto, service, controller e exception (ver README).
 */
@EnableFeignClients
@SpringBootApplication
public class HrPayrollApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrPayrollApplication.class, args);
    }
}
