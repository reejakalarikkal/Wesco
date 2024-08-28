package com.wesco.assignment.repository;

import com.wesco.assignment.model.Branch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class BranchRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addBranch(Branch branch){
        String sql="INSERT INTO t_branch (branch_id,branch_name, branch_loc, zip, product_id,qty_on_hand) VALUES (? ,?, ?, ? ,?,?)";
        jdbcTemplate.update(sql,branch.getBranchId(),branch.getBranchName(),branch.getBranchLoc(),branch.getZip(),branch.getProductId(),branch.getQtyOnHand());
        log.info("Branch added successfully: {}", branch);
    }

    public Branch getBranchById(Integer id){
        String sql="SELECT * FROM t_branch b WHERE b.id = ? ";
        Branch branch= jdbcTemplate.queryForObject(sql,new Object[]{id},new BeanPropertyRowMapper<>(Branch.class));
        log.info("Branch retrieved successfully: {}", branch);
        return branch;
    }

    public List<Branch> getAllBranches(){
        String sql = "SELECT * FROM t_branch";
        List<Branch> branches= jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Branch.class));
        log.info("Retrieved {} branches", branches.size());
        return branches;
    }

    public void updateBranch(Branch branch,Integer id){
        String sql="UPDATE t_branch SET branch_id= ? ,branch_name= ? , branch_loc= ?,zip= ? ,product_id= ? ,qty_on_hand = ? WHERE id = ? ";
        jdbcTemplate.update(sql,branch.getBranchId(),branch.getBranchName(),branch.getBranchLoc(),branch.getZip(),branch.getProductId(),branch.getQtyOnHand(),id);
        log.info("Branch updated successfully: {} with id: {}", branch, id);
    }

    public void deleteBranch(Integer id){
        String sql = "DELETE FROM t_branch WHERE id = ?";
        jdbcTemplate.update(sql, id);
        log.info("Branch deleted successfully with id: {}", id);
    }


    public List<Branch> getAllBranchesByZipAndLocation(String zip ,String location) {
        StringBuilder query = new StringBuilder("SELECT * FROM t_branch WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (zip != null && !zip.isEmpty()) {
            query.append(" AND zip = ?");
            params.add(zip);
        }
        if (location != null && !location.isEmpty()) {
            query.append(" AND branch_loc = ?");
            params.add(location);
        }
        Object[] queryParams = params.toArray();
        List<Branch> branches = jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<>(Branch.class),queryParams);
        log.info("Retrieved {} branches with zip: {} and location: {}", branches.size(), zip, location);
        return branches;

    }

    }

