package com.wesco.assignment.controllertest;

import com.wesco.assignment.controller.BranchController;
import com.wesco.assignment.model.Branch;
import com.wesco.assignment.service.BranchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(BranchController.class)
public class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BranchService branchService;

    @Autowired
    private ObjectMapper objectMapper;

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

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.post("/api/branches/addBranch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branch)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Branch added successfully"))
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo("Branch added successfully");

    }

    @Test
    public void testGetBranchById() throws Exception {
        Branch branch = loadBranchFromJson("branch.json");

        when(branchService.getBranchById(1)).thenReturn(branch);

        String responseBody = mockMvc.perform(get("/api/branches/getBranch/id/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(branch)))
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(branch));
    }

    @Test
    public void testGetAllBranches() throws Exception {
        List<Branch> branches = loadBranchesFromJson("branches.json");

        when(branchService.getAllBranches()).thenReturn(branches);

        String responseBody = mockMvc.perform(get("/api/branches/getAllBranches"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(branches)))
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(branches));
    }

    @Test
    public void testUpdateBranch() throws Exception {
        Branch branch = loadBranchFromJson("branch.json");

        String responseBody = mockMvc.perform(put("/api/branches/updateBranch/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branch)))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully updated the branch"))
                .andReturn().getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo("Successfully updated the branch");
    }

    @Test
    public void testDeleteBranch() throws Exception {

        String responseBody = mockMvc.perform(delete("/api/branches/deleteBranch/id/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully deleted the branch"))
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo("Successfully deleted the branch");
    }

    @Test
    public void testGetAllBranchesByZipAndLocation() throws Exception {
        List<Branch> branches = loadBranchesFromJson("branches.json");

        when(branchService.getAllBranchesByZipAndLocation("12345", "Location 1")).thenReturn(branches);

        String responseBody = mockMvc.perform(get("/api/branches/getAllBranchesByZipAndLocation")
                        .param("zip", "12345")
                        .param("location", "Location 1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(branches)))
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(branches));
    }
}
