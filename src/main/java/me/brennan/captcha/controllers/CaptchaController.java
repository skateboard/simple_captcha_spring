package me.brennan.captcha.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.brennan.captcha.CaptchaServer;
import me.brennan.captcha.models.Captcha;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brennan
 * @since 3/24/2021
 **/
@RestController
@RequestMapping("captcha/")
public class CaptchaController {

    @GetMapping
    public void generate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final Captcha captcha = CaptchaServer.IMAGE_GENERATOR.generateImage();

        request.getSession().setAttribute("captcha_solution", captcha.getSolution());
        ImageIO.write(captcha.getImage(), "JPG", response.getOutputStream());
    }

    @GetMapping("refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final Captcha captcha = CaptchaServer.IMAGE_GENERATOR.generateImage();

        request.getSession().setAttribute("captcha_solution", captcha.getSolution());
        ImageIO.write(captcha.getImage(), "JPG", response.getOutputStream());
    }

    @PostMapping("validate")
    public ResponseEntity<String> validate(HttpServletRequest request, @RequestBody String body) {
        final JsonObject responseObject = new JsonObject();
        final JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        HttpStatus httpStatus = HttpStatus.OK;

        if(jsonObject.has("solution") && request.getSession().getAttribute("captcha_solution") != null) {
            final String storedSolution = (String) request.getSession().getAttribute("captcha_solution");
            if(storedSolution.equals(jsonObject.get("solution").getAsString())) {
                responseObject.addProperty("status", true);
                responseObject.addProperty("message", "Passed");
            } else {
                httpStatus = HttpStatus.FORBIDDEN;
                responseObject.addProperty("status", true);
                responseObject.addProperty("message", "Failed");
            }
        } else {
            httpStatus = HttpStatus.FORBIDDEN;
            responseObject.addProperty("status", true);
            responseObject.addProperty("message", "Malformed");
        }

        return ResponseEntity
                .status(httpStatus)
                .body(responseObject.toString());
    }

}
