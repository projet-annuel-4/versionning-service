package com.example.versionningservice.service;

import com.example.versionningservice.data.entity.ProjectEntity;
import com.example.versionningservice.data.repository.ProjectRepository;
import com.example.versionningservice.domain.mapper.ProjectDomainMapper;
import com.example.versionningservice.domain.model.Project;
import com.example.versionningservice.request.CreateProjectRequest;
import com.example.versionningservice.request.UpdateProjectRequest;
import com.example.versionningservice.utils.GitCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectDomainMapper projectDomainMapper;
    private final CommandExecutorService commandExecutorService;
    //    private final CreatedProjectProducer createdProjectProducer;
//    private final DeletedProjectProducer deletedProjectProducer;
    private final GroupService groupService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectDomainMapper projectDomainMapper, CommandExecutorService commandExecutorService, GroupService groupService) {
        this.projectRepository = projectRepository;
        this.projectDomainMapper = projectDomainMapper;
        this.commandExecutorService = commandExecutorService;
        this.groupService = groupService;
    }

    public Project createProject(CreateProjectRequest request) throws IOException {
        Project project = saveProject(request);
        String dirCreatedPath = commandExecutorService.createDir(project.getId().toString(), GitCommand.ACTIVE_DIR);
        commandExecutorService.execute(String.format(GitCommand.INIT, dirCreatedPath));
        return project;

    }

    public void createProjectDirectory(Long projectId){

    }

    public Project saveProject(CreateProjectRequest request){
        ProjectEntity projectToSave = new ProjectEntity();
        projectToSave.setName(request.getName());
        projectToSave.setVisibility(request.isVisibility());
        projectToSave.setCreationDate(new Date());
        projectToSave.setGroupEntity(groupService.findGroupById(request.getGroupEntity()));
        projectToSave.setDeleted(false);
        projectToSave.setCommit_conflict(false);
        projectToSave.setMerge_conflict(false);
        projectToSave.setRevert_conflict(false);
        projectRepository.saveAndFlush(projectToSave);
        Project project = projectDomainMapper.convertEntityToModel(projectToSave);
        //createdProjectProducer.projectCreated(project);
        return project;
    }

    public List<Project> getProjectByGroupId(Long groupId){
        List<ProjectEntity> projectEntities = projectRepository.getAllByGroupEntity_IdAndDeletedFalse(groupId);
        return projectEntities.stream().map(projectDomainMapper::convertEntityToModel).collect(Collectors.toList());
    }


    @Transactional
    public void deleteProject(Long id) {
        var project = projectRepository.findById(id);
        ProjectEntity projectEntity = project.get();
        projectEntity.setDeleted(true);
        projectRepository.save(projectEntity);
        //deletedProjectProducer.projectDeleted(projectDomainMapper.convertEntityToModel(project.get()));
    }

    public void updateProject(UpdateProjectRequest request, Long id) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(id);
        if (projectEntityOptional.isEmpty()) {
            return; // throw error;
        }
        Project updatedProject = projectDomainMapper.convertEntityToModel(projectEntityOptional.get());
        updatedProject.setName(request.getName());
        updatedProject.setVisibility(request.isVisibility());
        projectRepository.save(projectDomainMapper.convertModelToEntity(updatedProject));

    }
}
