package com.wesco.assignment.servicetest;

import com.wesco.assignment.model.Branch;
import com.wesco.assignment.repository.BranchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesco.assignment.service.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchService branchService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Branch loadBranchFromJson(String filename) throws IOException {
        return objectMapper.readValue(Files.readString(Paths.get("src/test/resources/" + filename)), Branch.class);
    }

    private List<Branch> loadBranchesFromJson(String filename) throws IOException {
        return objectMapper.readValue(Files.readString(Paths.get("src/test/resources/" + filename)),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Branch.class));
    }

    @Test
    public void testAddBranch() throws Exception {
        Branch branch = loadBranchFromJson("branch.json");
        branchService.addBranch(branch);
        verify(branchRepository, times(1)).addBranch(branch);

    }

    @Test
    public void testGetBranchById() throws Exception {
        Branch expectedBranch = loadBranchFromJson("branch.json");
        when(branchRepository.getBranchById(1)).thenReturn(expectedBranch);
        Branch result = branchService.getBranchById(1);

        assertThat(result).isEqualTo(expectedBranch);
    }

    @Test
    public void testGetAllBranches() throws Exception {
        List<Branch> expectedBranches = loadBranchesFromJson("branches.json");
        when(branchRepository.getAllBranches()).thenReturn(expectedBranches);
        List<Branch> result = branchService.getAllBranches();
        assertThat(result).isEqualTo(expectedBranches);
    }

    @Test
    public void testUpdateBranch() throws Exception {
        Branch branch = loadBranchFromJson("branch.json");
        branchService.updateBranch(branch);
        verify(branchRepository, times(1)).updateBranch(branch);

    }

    @Test
    public void testDeleteBranch() throws Exception {
        branchService.deleteProduct(1);
        verify(branchRepository, times(1)).deleteBranch(1);

    }

    @Test
    public void testGetAllBranchesByZipAndLocation() throws Exception {
        List<Branch> expectedBranches = loadBranchesFromJson("branches.json");
        when(branchRepository.getAllBranchesByZipAndLocation("12345", "Location 1")).thenReturn(expectedBranches);
        List<Branch> result = branchService.getAllBranchesByZipAndLocation("12345", "Location 1");
        assertThat(result).isEqualTo(expectedBranches);
    }
}
