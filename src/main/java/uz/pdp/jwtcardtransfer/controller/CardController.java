package uz.pdp.jwtcardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.jwtcardtransfer.payload.CardDTO;
import uz.pdp.jwtcardtransfer.service.CardService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService service;

    @Autowired
    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CardDTO dto, HttpServletRequest request) {
        return service.createCard(dto, request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBalance(@PathVariable String id, @RequestBody CardDTO dto, HttpServletRequest request) {
        return service.editBalance(id, dto, request);
    }
}
