package uz.pdp.jwtcardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.jwtcardtransfer.payload.OutcomeDTO;
import uz.pdp.jwtcardtransfer.service.OutcomeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {
    private final OutcomeService service;

    @Autowired
    public OutcomeController(OutcomeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        return service.getAll(request);
    }

    @GetMapping("/{outcomeId}")
    public ResponseEntity<?> getOne(@PathVariable String outcomeId, HttpServletRequest request) {
        return service.getOne(outcomeId, request);
    }

    @PostMapping
    public ResponseEntity<?> transfer(@Valid @RequestBody OutcomeDTO dto, HttpServletRequest request) {
        return service.transferMoney(dto, request);
    }
}
