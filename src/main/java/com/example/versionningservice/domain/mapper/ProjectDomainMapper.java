package com.example.versionningservice.domain.mapper;

import com.example.versionningservice.data.entity.ProjectEntity;
import com.example.versionningservice.domain.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectDomainMapper {

    private final GroupDomainMapper groupDomainMapper;

    @Autowired
    public ProjectDomainMapper( GroupDomainMapper groupDomainMapper) {
        this.groupDomainMapper = groupDomainMapper;
    }

    public Project convertEntityToModel(ProjectEntity projectEntity) {
        if (projectEntity == null) {
            return null;
        }
        Project project =  new Project();
        project.setId(projectEntity.getId());
        project.setName(projectEntity.getName());
        project.setCreationDate(projectEntity.getCreationDate());
        project.setVisibility(projectEntity.isVisibility());
        project.setGroup(groupDomainMapper.convertEntityToModel(projectEntity.getGroupEntity()));
        project.setCommit_conflict(projectEntity.isCommit_conflict());
        project.setMerge_conflict(projectEntity.isMerge_conflict());
        project.setRevert_conflict(projectEntity.isMerge_conflict());

        return project;

    }

    public ProjectEntity convertModelToEntity(Project project) {
        if (project == null) {
            return null;
        }
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(project.getId());
        projectEntity.setGroupEntity(groupDomainMapper.convertModelToEntity(project.getGroup()));
        projectEntity.setName(project.getName());
        projectEntity.setCreationDate(project.getCreationDate());
        projectEntity.setVisibility(project.getVisibility());
        return projectEntity;
    }

}