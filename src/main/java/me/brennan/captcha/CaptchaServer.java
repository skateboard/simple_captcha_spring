package me.brennan.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Brennan
 * @since 3/24/2021
 **/
@SpringBootApplication
public class CaptchaServer {
    public final static ImageGenerator IMAGE_GENERATOR = new ImageGenerator();

    public static void main(String[] args) {
        SpringApplication.run(CaptchaServer.class);
    }

}
