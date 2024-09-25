package com.bitsathy.quarters.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import com.bitsathy.quarters.model.Checkouts;
import com.bitsathy.quarters.model.Faculty;
import com.bitsathy.quarters.repo.CheckoutRepo;
import com.bitsathy.quarters.repo.FacultyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Innmate;
import com.bitsathy.quarters.repo.InnmateRepo;

@Service
public class InnmateService {

    public static List<String> relations = Arrays.asList("Father", "Mother", "Wife", "Husband", "Son", "Daughter",
            "Other");

    @Autowired
    private InnmateRepo innmateRepo;
    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    private FacultyRepo facultyRepo;

    private boolean verifyInnmateDetails(Innmate innmate) throws Exception {
        if (innmate.getName() == null || innmate.getName().trim().equals("")) {
            throw new Exception("Invalid name");
        }
        if (!relations.contains(innmate.getRelation())) {
            throw new Exception("Invalid relation");
        }
        if (innmate.getBloodGroup() == null || innmate.getBloodGroup().trim().equals("")) {
            throw new Exception("Invalid Blood group");
        }
        if (innmate.getAge() == null || !(innmate.getAge() > 0 && innmate.getAge() < 130)) {
            throw new Exception("Invalid age");
        }
        if (innmate.getAadhar() == null
                || !(innmate.getAadhar() > 100000000000L && innmate.getAadhar() < 999999999999L)) {
            throw new Exception("Invalid aadhar");
        }
        if (innmate.getIsWorking() == null) {
            throw new Exception("Invalid work status");
        }

        return true;
    }

    public List<Innmate> getInnmates() {
        return innmateRepo.findAll();
    }

    public List<Innmate> updateInnmates(List<Innmate> innmates, Long id) throws Exception {

        for (Innmate innmate : innmates) {
            verifyInnmateDetails(innmate);
            if (innmate.getId() == null) {
                throw new Exception("Innmate id is not present");
            }
            if (innmate.getFaculty().getId() != id) {
                throw new Exception("Faculty id is not valid");
            }
        }

        Faculty faculty = facultyRepo.findById(id).get();
        for (Innmate innmate : innmates) {
            if (!(faculty.getInnmates()
                .stream()
                .anyMatch(existingInnmate -> 
                        existingInnmate.getId()
                        .equals(innmate.getId())))) {
                            throw new Exception("Innmate id is not matching with faculty");
            }
        }
        return innmateRepo.saveAll(innmates);
    }

    public Innmate addInnmates(Innmate innmate, Long id) throws Exception {

        verifyInnmateDetails(innmate);

        // Create dummy faculty with same id
        Faculty faculty = Faculty.builder().id(id).build();
        innmate.setFaculty(faculty);

        return innmateRepo.save(innmate);
    }

    public List<Innmate> getInnmatesByUser(Long id) {
        return facultyRepo.findById(id).get().getInnmates();
    }

    public List<Innmate> searchInnmate(String keyword) {
        return innmateRepo.searchInnmates(keyword);
    }

    public void innmatesCheckout(List<Long> innmates, Long facultyId) {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000000);
        String pin = String.format("%06d", num);

        innmates.forEach(id -> {
            Innmate innmate = innmateRepo.findById(id).get();
            if (innmate.getFaculty().getId() == facultyId) {
                checkoutRepo.save(Checkouts.builder()
                        .name(innmate.getName())
                        .type("Innmate")
                        .pin(pin)
                        .faculty(innmate.getFaculty())
                        .build());
                innmateRepo.delete(innmate);
            }
        });
    }
}
