package com.pm.patient_service.controller;

import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.dto.PatientsRequestDto;
import com.pm.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
@Tag(name = "Patient Controller", description = "APIs for managing patients")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    @Operation(summary = "Create Patient", description = "Creates a new patient in the system")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientsRequestDto patientRequest) {
        // Implementation for creating a new patient
        PatientResponseDTO patientResponseDTO=patientService.createPatient(patientRequest);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Patients", description = "Retrieves a list of all patients in the system")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update Patient", description = "Updates an existing patient's information in the system")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable("id") UUID id, @Valid @RequestBody PatientsRequestDto patientRequest) {
        // Implementation for creating a new patient
        PatientResponseDTO patientResponseDTO=patientService.updatePatient(id,patientRequest);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Patient", description = "Deletes a patient from the system by ID")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") UUID id) {

        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
