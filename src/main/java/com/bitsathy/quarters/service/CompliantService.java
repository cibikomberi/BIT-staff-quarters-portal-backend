package com.bitsathy.quarters.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Compliant;
import com.bitsathy.quarters.repo.CompliantRepo;

@Service
public class CompliantService {

    @Autowired
    private CompliantRepo compliantRepo;

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

    public Compliant addCompliant(Compliant compliant) throws Exception{
        if (compliant.getIssuedBy() == null) {
            throw new Exception("No compliant issuer name");
        }
        if (compliant.getStatus() == null) {
            compliant.setStatus("Initiated");
        }
        compliant.setIssuedOn(LocalDateTime.now());
        return compliantRepo.save(compliant);
    }

    public List<Compliant> getAllCompliantsByUser(String username) {
        return compliantRepo.findByIssuedBy(username);
    }

    public List<Compliant> searchCompliant(String keyword) {
        return compliantRepo.searchCompliants(keyword);
    }
}