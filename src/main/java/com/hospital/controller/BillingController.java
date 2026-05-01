package com.hospital.controller;

import com.hospital.model.Billing;
import com.hospital.service.BillingService;
import com.hospital.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/billings")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;
    private final PatientService patientService;

    @GetMapping
    public String listBillings(Model model) {
        model.addAttribute("billings", billingService.getAllBillings());
        return "billings/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Billing billing = new Billing();
        billing.setBillingDate(LocalDate.now().toString());
        billing.setPaymentStatus("UNPAID");
        model.addAttribute("billing", billing);
        model.addAttribute("patients", patientService.getAllPatients());
        return "billings/form";
    }

    @PostMapping
    public String createBilling(@Valid @ModelAttribute Billing billing, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patients", patientService.getAllPatients());
            return "billings/form";
        }
        billingService.saveBilling(billing);
        return "redirect:/billings";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        billingService.getBillingById(id).ifPresent(b -> model.addAttribute("billing", b));
        model.addAttribute("patients", patientService.getAllPatients());
        return "billings/form";
    }

    @PostMapping("/{id}")
    public String updateBilling(@PathVariable String id, @Valid @ModelAttribute Billing billing,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patients", patientService.getAllPatients());
            return "billings/form";
        }
        billing.setId(id);
        billingService.saveBilling(billing);
        return "redirect:/billings";
    }

    @GetMapping("/{id}/delete")
    public String deleteBilling(@PathVariable String id) {
        billingService.deleteBilling(id);
        return "redirect:/billings";
    }

    // Report: billing history by patient
    @GetMapping("/by-patient")
    public String byPatient(@RequestParam String patientId, Model model) {
        model.addAttribute("billings", billingService.getBillingByPatient(patientId));
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("selectedPatientId", patientId);
        return "billings/by-patient";
    }

    // Report: unpaid records
    @GetMapping("/unpaid")
    public String unpaid(Model model) {
        model.addAttribute("billings", billingService.getUnpaidBillings());
        return "billings/unpaid";
    }
}
