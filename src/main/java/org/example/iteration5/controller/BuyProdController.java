package org.example.iteration5.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
@Log
@Profile("prod")
public class BuyProdController {
    @SneakyThrows
    @GetMapping("/buyAll/")
    public String testThreadController() {
        return "А я прод у меня нет реализации";
    }
}
