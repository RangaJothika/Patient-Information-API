package com.example.patient_management_1.service;

import com.example.patient_management_1.entity.Address;
import com.example.patient_management_1.entity.Patient;
import com.example.patient_management_1.repository.AddressRepository;
import com.example.patient_management_1.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	PatientRepository patientRepository;

	public Address getAddress(Long id) {
		return addressRepository.findById(id).get();
	}

	public Address createAddress(Long patientId, Address address) {
		Patient oldPatient=patientRepository.findById(patientId).get();
		oldPatient.setAddress(address);
		patientRepository.save(oldPatient);
		return address;
	}

	public Address updateAddress(Address address) {
		Address oldAddress=addressRepository.findById(address.getId()).get();
		oldAddress.setCity(address.getCity());
		oldAddress.setState(address.getState());
		oldAddress.setStreet(address.getStreet());
		oldAddress.setZipCode(address.getZipCode());
		addressRepository.save(oldAddress);
		return oldAddress;
	}

	public void deleteAddress(Long id) {
		/*
		 * Explanation:
		 * 
		 * 1. We cannot delete address directly using jparepository deleteById method
		 * due to relational mapping with the patient class.
		 * 
		 * 2. So before deleting address you need find the patient with same address and
		 * remove the linked address details.
		 * 
		 * 3. And finally save the patient and delete the address using jpa deleteById
		 * method.
		 * 
		 */

		for (Patient patient : patientRepository.findAll()) {
			if (patient.getAddress() != null && patient.getAddress().getId().equals(id)) {
				patient.setAddress(null);
				patientRepository.save(patient);
				addressRepository.deleteById(id);
			}
		}
	}
}
