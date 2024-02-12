package com.io.tech.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BranchNameList {
    @JsonProperty("current_branch")
    private String currentBranch;

    @JsonProperty("branch_list")
    private List<String> branchNameList;
}
