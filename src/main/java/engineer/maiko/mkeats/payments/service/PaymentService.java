package engineer.maiko.mkeats.payments.service;

import engineer.maiko.mkeats.payments.dto.PaymentDTO;
import engineer.maiko.mkeats.payments.model.Payment;
import engineer.maiko.mkeats.payments.model.Status;
import engineer.maiko.mkeats.payments.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PaymentDTO> getAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, PaymentDTO.class));
    }

    public PaymentDTO getById(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(payment, PaymentDTO.class);


    }

    public PaymentDTO createPayment(PaymentDTO dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setStatus(Status.CREATE);
        repository.save(payment);

        return modelMapper.map(payment, PaymentDTO.class);

    }

    public PaymentDTO updatePayment(Long id, PaymentDTO dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setId(id);
        payment = repository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public void deletePayment(Long id) {
        repository.deleteById(id);
    }





}
