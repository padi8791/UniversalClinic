package com.clinic.clinic.DAO;

import com.clinic.clinic.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientPaginatedRepo extends PagingAndSortingRepository<Patient, Long> {
}
