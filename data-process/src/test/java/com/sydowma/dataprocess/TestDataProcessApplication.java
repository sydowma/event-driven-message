package com.sydowma.dataprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestDataProcessApplication {

    public static void main(String[] args) {
        SpringApplication.from(DataProcessApplication::main).with(TestDataProcessApplication.class).run(args);
    }

}
