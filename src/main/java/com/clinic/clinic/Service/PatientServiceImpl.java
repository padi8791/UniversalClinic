package com.clinic.clinic.Service;

import com.clinic.clinic.DAO.PatientRepository;
import com.clinic.clinic.Entity.Patient;

import java.util.List;
import java.util.Optional;

public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(Long id) {
        Optional<Patient> result = patientRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }
}
