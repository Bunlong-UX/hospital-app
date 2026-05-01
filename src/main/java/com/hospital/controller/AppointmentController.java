package com.hospital.controller;

import com.hospital.model.Appointment;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "appointments/form";
    }

    @PostMapping
    public String createAppointment(@Valid @ModelAttribute Appointment appointment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("doctors", doctorService.getAllDoctors());
            return "appointments/form";
        }
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        appointmentService.getAppointmentById(id).ifPresent(a -> model.addAttribute("appointment", a));
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "appointments/form";
    }

    @PostMapping("/{id}")
    public String updateAppointment(@PathVariable String id, @Valid @ModelAttribute Appointment appointment,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("doctors", doctorService.getAllDoctors());
            return "appointments/form";
        }
        appointment.setId(id);
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/{id}/delete")
    public String deleteAppointment(@PathVariable String id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }

    // Report: by patient
    @GetMapping("/by-patient")
    public String byPatient(@RequestParam String patientId, Model model) {
        model.addAttribute("appointments", appointmentService.getAppointmentsByPatient(patientId));
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("selectedPatientId", patientId);
        return "appointments/by-patient";
    }

    // Report: by doctor
    @GetMapping("/by-doctor")
    public String byDoctor(@RequestParam String doctorId, Model model) {
        model.addAttribute("appointments", appointmentService.getAppointmentsByDoctor(doctorId));
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("selectedDoctorId", doctorId);
        return "appointments/by-doctor";
    }
}
