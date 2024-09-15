package com.bitsathy.quarters.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitsathy.quarters.model.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Compliant;
import com.bitsathy.quarters.model.Users;
import com.bitsathy.quarters.repo.CompliantRepo;
import com.bitsathy.quarters.repo.UserRepo;

@Service
public class CompliantService {

    @Autowired
    private CompliantRepo compliantRepo;
    @Autowired
    private UserRepo userRepo;

    public List<Compliant> getAllCompliants(){
        return compliantRepo.findAll();
    }

    public Map<String, Object> getCompliantCount(){
        Map<String, Object> response = new HashMap<>();
        response.put("issued", compliantRepo.countIssuedComplaintsToday());
        response.put("pending", compliantRepo.countPendingComplaints());
        response.put("resolved", compliantRepo.countResolvedComplaintsToday());
        return response;
    }

    public Compliant getCompliantById(int id){
        return compliantRepo.findById(id).orElse(null);
    }

    public Compliant newCompliant(Compliant compliant, Long id) throws Exception{

        if (compliant.getStatus() == null) {
            compliant.setStatus("Initiated");
        }
        Faculty issuedBy = Faculty.builder().id(id).build();

        compliant.setIssuedBy(issuedBy);

        compliant.setIssuedOn(LocalDateTime.now());
        return compliantRepo.save(compliant);
    }

    public List<Compliant> getAllCompliantsByUser(Long id) {
        return compliantRepo.findByIssuedBy_Id(id);
        
    }

    public List<Compliant> searchCompliant(String keyword) {
        return compliantRepo.searchCompliants(keyword);
    }

    public List<Compliant> getAllCompliantsByHandler(Long id) {
        return compliantRepo.findByAssignedTo_Id(id);
    }

    public List<Compliant> searchHandlerCompliant(String keyword, Long username) {
        return compliantRepo.searchHandlerCompliants(keyword, username);
    }
}