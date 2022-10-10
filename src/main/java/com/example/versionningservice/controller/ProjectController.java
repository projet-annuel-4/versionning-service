package com.example.versionningservice.controller;

import com.example.versionningservice.domain.model.Project;
import com.example.versionningservice.dto.request.CreateProjectRequest;
import com.example.versionningservice.dto.request.UpdateProjectRequest;
import com.example.versionningservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v2/project")
public class ProjectController {

    private final ProjectService projectService;


    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/createProject")
    public ResponseEntity<Project> createProject(@RequestBody CreateProjectRequest request) throws IOException {
        Project projectCreated = projectService.createProject(request);
        return ResponseEntity.ok(projectCreated);
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateProject/{id}")
    public ResponseEntity<?> updateProject(@RequestBody UpdateProjectRequest request, @PathVariable("id") Long id) {
        projectService.updateProject(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{groupId}/getProjects")
    public ResponseEntity<List<Project>> getProjectByGroupId(@PathVariable("groupId") Long groupId){
        return ResponseEntity.ok(projectService.getProjectByGroupId(groupId));
    }

}

