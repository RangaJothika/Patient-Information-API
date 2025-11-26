package com.example.patient_management_1.entity;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Doctor {
//	public List<Patient> getPatientList() {
//		return patientList;
//	}
//
//	public void setPatientList(List<Patient> patientList) {
//		this.patientList = patientList;
//	}

	public List<Patient> getPatientList() {
		return patientList;
	}

	public void setPatientList(List<Patient> patientList) {
		this.patientList = patientList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column
	private String name;
	@ManyToMany(mappedBy="doctorList")
	@JsonIgnore
	private List<Patient> patientList=new ArrayList<>();

	public Doctor(String name, String specialty) {
		super();
		this.name = name;
		this.specialty = specialty;
	}

	public Doctor() {
	}

	@Column
	private String specialty;
}
