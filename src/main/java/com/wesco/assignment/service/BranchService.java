package com.wesco.assignment.service;

import com.wesco.assignment.model.Branch;
import com.wesco.assignment.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {
    @Autowired
    private BranchRepository branchRepository;

    public void addBranch(Branch branch){
        branchRepository.addBranch(branch);
    }

    public Branch getBranchById(Integer id){
        return branchRepository.getBranchById(id);
    }

    public List<Branch> getAllBranches(){
        return branchRepository.getAllBranches();
    }

    public void updateBranch(Branch branch){
        branchRepository.updateBranch(branch);
    }

    public void deleteProduct(Integer id) {
        branchRepository.deleteBranch(id);
    }

    public List<Branch> getAllBranchesByZipAndLocation(String zip, String location) {
        return branchRepository.getAllBranchesByZipAndLocation(zip,location);
    }

}
