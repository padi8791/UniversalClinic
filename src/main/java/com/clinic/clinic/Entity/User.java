package com.clinic.clinic.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @OneToMany
    @JsonBackReference
    private List<Doctor> doctors = new ArrayList<>();


    @OneToMany
    @JsonBackReference
    private List<Patient> patients = new ArrayList<>();

    @OneToMany
    @JsonBackReference
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany
    @JsonBackReference
    private List<Department> departments = new ArrayList<>();

    public User(){

    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }


    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    // Utility method to add doctor
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        doctor.setUser(this);
    }

    // Utility method to remove doctor
    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
        doctor.setUser(null);
    }


    // Utility method to add doctor
    public void addPatient(Patient patient) {
        patients.add(patient);
        patient.setUser(this);
    }

    // Utility method to remove doctor
    public void removePatient(Patient patient) {
        patients.remove(patient);
        patient.setUser(null);
    }

    // Utility method to add doctor
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setUser(this);
    }

    // Utility method to remove doctor
    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setUser(null);
    }
    public void addDepartment(Department department) {
        departments.add(department);

    }

    public void removeADepartment(Department department) {
        departments.remove(department);

    }
}
