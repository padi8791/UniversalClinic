package com.clinic.clinic.DAO;

import com.clinic.clinic.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppointmentPaginatedRepo extends PagingAndSortingRepository<Appointment, Long> {
}
