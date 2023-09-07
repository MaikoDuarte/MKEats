package engineer.maiko.mkeats.payments.controller;

import engineer.maiko.mkeats.payments.dto.PaymentDTO;
import engineer.maiko.mkeats.payments.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService service;

    @GetMapping
    public Page<PaymentDTO> list(@PageableDefault(size = 10) Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> detail(@PathVariable @NotNull Long id) {
        PaymentDTO dto = service.getById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> register(@RequestBody @Valid PaymentDTO dto, UriComponentsBuilder uriBuilder) {
        PaymentDTO payment = service.createPayment(dto);
        URI address = uriBuilder.path("/payments/{id}").buildAndExpand(payment.getId()).toUri();

        return ResponseEntity.created(address).body(payment);
    }

    @PutMapping
    public ResponseEntity<PaymentDTO> update(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDTO dto) {
        PaymentDTO update = service.updatePayment(id, dto);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDTO> remove(@PathVariable @NotNull Long id) {
        service.deletePayment(id);
        return ResponseEntity.noContent().build();
    }


}
