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

    public void addBranch(Branch branch) {
        try {
            String sql = "INSERT INTO t_branch (branch_id,branch_name, branch_loc, zip, product_id,qty_on_hand) VALUES (? ,?, ?, ? ,?,?)";
            jdbcTemplate.update(sql, branch.getBranchId(), branch.getBranchName(), branch.getBranchLoc(), branch.getZip(), branch.getProductId(), branch.getQtyOnHand());
            log.info("Branch added successfully: {}", branch);
        } catch (Exception e) {
            log.error("Failed to add branch. Error: {}", e.getMessage());
            throw e;
        }
    }

    public Branch getBranchById(Integer id) {
        try {
            String sql = "SELECT * FROM t_branch b WHERE b.id = ? ";
            Branch branch = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Branch.class));
            log.info("Branch retrieved successfully: {}", branch);
            return branch;
        } catch (Exception e) {
            log.error("Failed to retrieve branch by id {}. Error: {}", id, e.getMessage());
            throw e;
        }
    }

    public List<Branch> getAllBranches() {
        try {
            String sql = "SELECT * FROM t_branch";
            List<Branch> branches = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Branch.class));
            log.info("Retrieved {} branches", branches.size());
            return branches;
        } catch (Exception e) {
            log.error("Failed to retrieve all branches. Error: {}", e.getMessage());
            throw e;
        }
    }

    public void updateBranch(Branch branch) {
        try {
            String sql = "UPDATE t_branch SET branch_id= ? ,branch_name= ? , branch_loc= ?,zip= ? ,product_id= ? ,qty_on_hand = ? WHERE id = ? ";
            jdbcTemplate.update(sql, branch.getBranchId(), branch.getBranchName(), branch.getBranchLoc(), branch.getZip(), branch.getProductId(), branch.getQtyOnHand(), branch.getId());
            log.info("Branch updated successfully: {} ", branch);
        } catch (Exception e) {
            log.error("An unexpected error occurred in method updateBranch {}", e.getMessage());
            throw e;
        }
    }

    public void deleteBranch(Integer id) {
        try {
            String sql = "DELETE FROM t_branch WHERE id = ?";
            jdbcTemplate.update(sql, id);
            log.info("Branch deleted successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Failed to delete branch with id {}. Error: {}", id, e.getMessage());
            throw e;
        }
    }


    public List<Branch> getAllBranchesByZipAndLocation(String zip, String location) {
        try {
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
            List<Branch> branches = jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<>(Branch.class), queryParams);
            log.info("Retrieved {} branches with zip: {} and location: {}", branches.size(), zip, location);
            return branches;
        } catch (Exception e) {
            log.error("Failed to retrieve branches by zip: {} and location: {}. Error: {}", zip, location, e.getMessage());
            throw e;
        }

    }

}

