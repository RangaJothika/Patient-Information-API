package com.example.patient_management_1.service;

import com.example.patient_management_1.entity.Patient;
import com.example.patient_management_1.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PatientService {
    
    @Autowired
    PatientRepository patientRepository;

    public Patient getPatient(Long id) {
        return patientRepository.findById(id).get();
    }
    public Patient createPatient(@RequestBody Patient patient) {
        patientRepository.save(patient);
        return patient;
    }

    public Patient updatePatient(@RequestBody Patient patient) {
        Patient oldPatient=patientRepository.findById(patient.getId()).get();
        oldPatient.setAddress(patient.getAddress());
        oldPatient.setDoctorList(patient.getDoctorList());
        oldPatient.setName(patient.getName());
        patientRepository.save(oldPatient);
        return oldPatient;
    }

    public void deletePatient(@PathVariable Long id) {
       patientRepository.deleteById(id);
    }
}
