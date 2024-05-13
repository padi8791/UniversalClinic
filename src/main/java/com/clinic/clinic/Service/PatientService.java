package com.clinic.clinic.Service;

import com.clinic.clinic.Entity.Doctor;
import com.clinic.clinic.Entity.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();
    Patient findById(Long id);
    Patient save(Patient patient);
    void deleteById(Long id);
}
