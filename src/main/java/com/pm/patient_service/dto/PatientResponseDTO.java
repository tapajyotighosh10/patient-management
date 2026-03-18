package com.pm.patient_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
}
