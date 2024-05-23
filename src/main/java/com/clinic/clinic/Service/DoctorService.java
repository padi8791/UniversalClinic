package com.clinic.clinic.Service;

import com.clinic.clinic.Entity.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DoctorService {
    List<Doctor> findAll();
    Doctor findById(Long id);
    Doctor save(Doctor doctor);
    void deleteById(Long id);

    Page<Doctor> getAllPaginated(int page, int i);
}
