package com.hospital.controller;

import com.hospital.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final BillingService billingService;
    private final InventoryService inventoryService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalPatients", patientService.getAllPatients().size());
        model.addAttribute("totalDoctors", doctorService.getAllDoctors().size());
        model.addAttribute("totalAppointments", appointmentService.getAllAppointments().size());
        model.addAttribute("unpaidBillings", billingService.getUnpaidBillings().size());
        model.addAttribute("nearExpirationItems", inventoryService.getItemsNearExpiration().size());
        return "index";
    }
}
