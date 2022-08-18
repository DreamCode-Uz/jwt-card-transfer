package uz.pdp.jwtcardtransfer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class Controller {
    @GetMapping
    public ResponseEntity<?> sayHi() {
        return ResponseEntity.ok("WOW");
    }
}
