package uz.pdp.jwtcardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.jwtcardtransfer.payload.IncomeDTO;
import uz.pdp.jwtcardtransfer.service.IncomeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    private final IncomeService service;

    @Autowired
    public IncomeController(IncomeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> addIncome(@Valid @RequestBody IncomeDTO dto) {
        return null;
    }
}
