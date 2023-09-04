package engineer.maiko.mkeats.payments.repository;

import engineer.maiko.mkeats.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {




}
