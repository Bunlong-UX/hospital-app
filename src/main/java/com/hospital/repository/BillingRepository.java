package com.hospital.repository;

import com.hospital.model.Billing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingRepository extends MongoRepository<Billing, String> {
    List<Billing> findByPatientId(String patientId);
    List<Billing> findByPaymentStatus(String paymentStatus);
}
