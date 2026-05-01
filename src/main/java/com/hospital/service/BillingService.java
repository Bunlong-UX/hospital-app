package com.hospital.service;

import com.hospital.model.Billing;
import com.hospital.repository.BillingRepository;
import com.hospital.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;
    private final PatientRepository patientRepository;

    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    public Optional<Billing> getBillingById(String id) {
        return billingRepository.findById(id);
    }

    public Billing saveBilling(Billing billing) {
        patientRepository.findById(billing.getPatientId()).ifPresent(p -> {
            billing.setPatientName(p.getFirstName() + " " + p.getLastName());
        });
        return billingRepository.save(billing);
    }

    public void deleteBilling(String id) {
        billingRepository.deleteById(id);
    }

    // Report: billing history by patient
    public List<Billing> getBillingByPatient(String patientId) {
        return billingRepository.findByPatientId(patientId);
    }

    // Report: unpaid billing records
    public List<Billing> getUnpaidBillings() {
        return billingRepository.findByPaymentStatus("UNPAID");
    }
}
