package com.clinic.clinic.DAO;

import com.clinic.clinic.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d WHERE d.lastName LIKE %:lastName%")
    List<Doctor> findByLastName(@Param("lastName") String lastName);
}