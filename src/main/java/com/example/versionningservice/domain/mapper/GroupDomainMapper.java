package com.example.versionningservice.domain.mapper;

import com.example.versionningservice.data.entity.GroupEntity;
import com.example.versionningservice.domain.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupDomainMapper {


    @Autowired
    public GroupDomainMapper() {

    }

    public Group convertEntityToModel(GroupEntity groupEntity) {
        if (groupEntity == null) {
            return null;
        }
        Group group = new Group();
        group.setId(groupEntity.getId());
        group.setName(groupEntity.getName());
        return group;
    }

    public GroupEntity convertModelToEntity(Group group) {
        if (group == null) {
            return null;
        }
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(group.getId());
        groupEntity.setName(group.getName());
        return groupEntity;
    }
}
