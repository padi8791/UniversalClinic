package com.clinic.clinic.DAO;

import com.clinic.clinic.Entity.Doctor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DoctorPaginatedRepo extends PagingAndSortingRepository<Doctor, Long> {
}