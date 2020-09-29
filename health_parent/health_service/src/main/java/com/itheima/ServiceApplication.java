package com.itheima;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author: chencongming
 * @date: 2020-09-18 20:43
 */
public class ServiceApplication {
    public static void main(String[] args) throws IOException {
      new ClassPathXmlApplicationContext
                ("classpath:applicationContext-service.xml");

       System.in.read();

    }
}
