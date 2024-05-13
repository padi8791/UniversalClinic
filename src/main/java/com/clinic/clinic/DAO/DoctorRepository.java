package com.clinic.clinic.DAO;

import com.clinic.clinic.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}