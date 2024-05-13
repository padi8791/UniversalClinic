package com.clinic.clinic.Service;

import com.clinic.clinic.Entity.Appointment;
import com.clinic.clinic.Entity.Patient;

import java.util.List;

public interface AppointmentService {
    List<Appointment> findAll();
    Appointment findById(Long id);
    Appointment save(Appointment appointment);
    void deleteById(Long id);
}
