package com.hospital.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointments")
public class Appointment {

    @Id
    private String id;

    @NotNull(message = "Patient is required")
    private String patientId;

    @NotNull(message = "Doctor is required")
    private String doctorId;

    // Denormalized fields for easy display
    private String patientName;
    private String doctorName;
    private String doctorSpecialization;

    @NotBlank(message = "Appointment date is required")
    private String appointmentDate;

    private String status; // SCHEDULED, COMPLETED, CANCELLED
}
