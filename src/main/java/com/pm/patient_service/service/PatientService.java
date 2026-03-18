package com.pm.patient_service.service;

import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.dto.PatientsRequestDto;
import com.pm.patient_service.exception.EmailAlreadyExitsException;
import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientResponseDTO createPatient(@Valid PatientsRequestDto requestDto) {

        if(patientRepository.existsByEmail(requestDto.getEmail())){
            throw new EmailAlreadyExitsException("Email already exists");
        }

        Patient patient = Patient.builder()
                .email(requestDto.getEmail())
                .address(requestDto.getAddress())
                .registerDate(requestDto.getRegisteredDate())
                .name(requestDto.getName())
                .dateOfBirth(requestDto.getDateOfBirth())
                .build();

        Patient patient1 = patientRepository.save(patient);

        return new PatientResponseDTO(
                patient1.getId(),
                patient1.getName(),
                patient1.getEmail(),
                patient1.getAddress(),
                patient1.getDateOfBirth()
        );
    }

    public List<PatientResponseDTO> getAllPatients() {
//        return patientRepository.findAll()
//                .stream()
//                .map(patient -> new PatientResponseDTO(
//                        patient.getId(),
//                        patient.getName(),
//                        patient.getEmail(),
//                        patient.getAddress(),
//                        patient.getDateOfBirth()
//                ))
//                .collect(Collectors.toList());

        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(patient -> new PatientResponseDTO(
                        patient.getId(),
                        patient.getName(),
                        patient.getEmail(),
                        patient.getAddress(),
                        patient.getDateOfBirth()
                ))
                .collect(Collectors.toList());

    }


    public PatientResponseDTO updatePatient(UUID id, @Valid PatientsRequestDto patientsRequestDto) {

        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with id: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientsRequestDto.getEmail(),id)) {
            throw new EmailAlreadyExitsException("Email already exists with this email: " + patientsRequestDto.getEmail());
        }
            patient.setName(patientsRequestDto.getName());
            patient.setEmail(patientsRequestDto.getEmail());
            patient.setAddress(patientsRequestDto.getAddress());
            patient.setDateOfBirth(patientsRequestDto.getDateOfBirth());
            patient.setRegisterDate(patientsRequestDto.getRegisteredDate());

            Patient updatedPatient = patientRepository.save(patient);

            return new PatientResponseDTO(
                    updatedPatient.getId(),
                    updatedPatient.getName(),
                    updatedPatient.getEmail(),
                    updatedPatient.getAddress(),
                    updatedPatient.getDateOfBirth()
            );
    }


    public void deletePatient(UUID id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }


}
