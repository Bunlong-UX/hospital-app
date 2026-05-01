package com.hospital.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "billings")
public class Billing {

    @Id
    private String id;

    @NotNull(message = "Patient is required")
    private String patientId;

    private String patientName;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private String billingDate;

    private String paymentStatus; // PAID, UNPAID, PENDING
}
