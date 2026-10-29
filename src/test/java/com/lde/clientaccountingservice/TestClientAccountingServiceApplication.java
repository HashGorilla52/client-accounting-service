package com.lde.clientaccountingservice;

import org.springframework.boot.SpringApplication;

public class TestClientAccountingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ClientAccountingServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
