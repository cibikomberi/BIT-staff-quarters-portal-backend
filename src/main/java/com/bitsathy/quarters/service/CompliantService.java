package com.bitsathy.quarters.service;

import java.util.List;

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
        return compliantRepo.save(compliant);
    }

    public List<Compliant> getAllCompliantsByUser(String username) {
        return compliantRepo.findByIssuedBy(username);
    }
}
