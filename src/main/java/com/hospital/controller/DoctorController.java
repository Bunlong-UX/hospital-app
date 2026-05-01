package com.hospital.controller;

import com.hospital.model.Doctor;
import com.hospital.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/form";
    }

    @PostMapping
    public String createDoctor(@Valid @ModelAttribute Doctor doctor, BindingResult result) {
        if (result.hasErrors()) return "doctors/form";
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        doctorService.getDoctorById(id).ifPresent(d -> model.addAttribute("doctor", d));
        return "doctors/form";
    }

    @PostMapping("/{id}")
    public String updateDoctor(@PathVariable String id, @Valid @ModelAttribute Doctor doctor, BindingResult result) {
        if (result.hasErrors()) return "doctors/form";
        doctor.setId(id);
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/delete")
    public String deleteDoctor(@PathVariable String id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}
