package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(String id) {
        return appointmentRepository.findById(id);
    }

    public Appointment saveAppointment(Appointment appointment) {
        // Enrich with denormalized names
        patientRepository.findById(appointment.getPatientId()).ifPresent(p -> {
            appointment.setPatientName(p.getFirstName() + " " + p.getLastName());
        });
        doctorRepository.findById(appointment.getDoctorId()).ifPresent(d -> {
            appointment.setDoctorName(d.getFirstName() + " " + d.getLastName());
            appointment.setDoctorSpecialization(d.getSpecialization());
        });
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }

    // Report: appointments by patient
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Report: appointments by doctor
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
}
