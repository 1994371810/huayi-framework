package com.huayi;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author gjw
 * @Date 2021/3/16 14:07
 **/
public class TestApplication {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void test(){


        boolean matches = passwordEncoder.matches("1", "$2a$10$DEHP0Ev2IwSWoJdy0ay.k.lA6q8Z61ukLV5.itnRPGD5CDvlaRl7y");

        System.out.println(matches);
    }

}
