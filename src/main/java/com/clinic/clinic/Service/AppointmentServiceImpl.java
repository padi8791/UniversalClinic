package com.clinic.clinic.Service;

import com.clinic.clinic.DAO.AppointmentRepository;
import com.clinic.clinic.Entity.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment findById(Long id) {
        Optional<Appointment> result = appointmentRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
}
