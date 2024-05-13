package com.clinic.clinic.DAO;

import com.clinic.clinic.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
