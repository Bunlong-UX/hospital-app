package com.hospital.controller;

import com.hospital.model.Patient;
import com.hospital.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public String listPatients(Model model, @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("patients", patientService.searchPatients(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("patients", patientService.getAllPatients());
        }
        return "patients/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/form";
    }

    @PostMapping
    public String createPatient(@Valid @ModelAttribute Patient patient, BindingResult result) {
        if (result.hasErrors()) return "patients/form";
        patientService.savePatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        patientService.getPatientById(id).ifPresent(p -> model.addAttribute("patient", p));
        return "patients/form";
    }

    @PostMapping("/{id}")
    public String updatePatient(@PathVariable String id, @Valid @ModelAttribute Patient patient, BindingResult result) {
        if (result.hasErrors()) return "patients/form";
        patient.setId(id);
        patientService.savePatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/delete")
    public String deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }
}
