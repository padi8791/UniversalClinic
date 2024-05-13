package com.clinic.clinic.DAO;

import com.clinic.clinic.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
