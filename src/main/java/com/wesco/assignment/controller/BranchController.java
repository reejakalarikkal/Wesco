package com.wesco.assignment.controller;

import com.wesco.assignment.model.Branch;
import com.wesco.assignment.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/branches")
@Slf4j
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/addBranch")
    public ResponseEntity<String> addBranch(@RequestBody Branch branch){
        branchService.addBranch(branch);
        return new ResponseEntity<>("Branch added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getBranch/id/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable("id") Integer id){
        Branch branch=branchService.getBranchById(id);
        return new ResponseEntity<>(branch,HttpStatus.OK);
    }

    @GetMapping("/getAllBranches")
    public ResponseEntity<List<Branch>> getAllBranches(){
        return new ResponseEntity<>(branchService.getAllBranches(),HttpStatus.OK);
    }

    @PutMapping("/updateBranch/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Integer id, @RequestBody Branch branch) {
        branchService.updateBranch(branch,id);
        return new ResponseEntity<>("Successfully updated the branch",HttpStatus.OK);

    }

    @DeleteMapping("/deleteBranch/id/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        branchService.deleteProduct(id);
        return new ResponseEntity<>("Successfully deleted the branch",HttpStatus.OK);
    }

    @GetMapping("/getAllBranchesByZipAndLocation")
    public List<Branch> getAllBranchesByZipAndLocation( @RequestParam(required = false) String zip, @RequestParam(required = false) String location) {
        return branchService.getAllBranchesByZipAndLocation(zip, location);
    }



}
