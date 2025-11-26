package com.example.patient_management_1.service;

import com.example.patient_management_1.entity.Doctor;
import com.example.patient_management_1.entity.Patient;
import com.example.patient_management_1.exception.*;
import com.example.patient_management_1.repository.DoctorRepository;
import com.example.patient_management_1.repository.PatientRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DoctorService {

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	PatientRepository patientRepository;

	public Doctor getDoctor(Long id) {
		return doctorRepository.findById(id).get();
	}

	public List<Doctor> createDoctor(Long patientId, List<Doctor> doctorList) {
		Patient patient = patientRepository.findById(patientId).get();
		patient.setDoctorList(doctorList);
		patientRepository.save(patient);
		return doctorList;
	}

	public Doctor updateDoctor(Doctor doctor) {

		Doctor oldDoctor = doctorRepository.findById(doctor.getId()).get();
		oldDoctor.setName(doctor.getName());
		oldDoctor.setPatientList(doctor.getPatientList());
		oldDoctor.setSpecialty(doctor.getSpecialty());
		doctorRepository.save(oldDoctor);
		return oldDoctor;
	}

public void deleteDoctor(Long id) {
		Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new IdNotFound("Doctor id not found"));
		List<Patient> patientsList = new ArrayList<>();
		for (Patient patient : patientRepository.findAll()) {
			patientsList.add(patient);
		}
		for (Patient patient : patientsList) {
			if (patient.getDoctorList().contains(doctor)) {
				patient.getDoctorList().remove(doctor);// wo @transactional,this and few more linesll be out of
														// transaction as only repo methods will be in their own
														// transaction and they close as soon as their methdo ends,
														// so to access doctorslist via patient here either it should
														// have fetchtype eager or we should put @transactional
														// annotation for this entire method to make it a single
														// transaction and this lien inside it
				patientRepository.save(patient);// remove the ref from owner to inverse for this record
			}
		}
		doctorRepository.deleteById(id);// delete the inverse's record
	}

}
