package com.example.versionningservice.service;

import com.example.versionningservice.data.entity.GroupEntity;
import com.example.versionningservice.data.repository.GroupRepository;
import com.example.versionningservice.domain.mapper.GroupDomainMapper;
import com.example.versionningservice.domain.model.Group;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupDomainMapper groupDomainMapper;

    public GroupService(GroupRepository groupRepository, GroupDomainMapper groupDomainMapper) {
        this.groupRepository = groupRepository;
        this.groupDomainMapper = groupDomainMapper;
    }

    public GroupEntity findGroupById(Long id){
        Optional<GroupEntity> groupEntity = groupRepository.findById(id);
        if( groupEntity.isEmpty() ){
            throw new RuntimeException("no group found");
        }
        return groupEntity.get();
    }

    public Group saveGroup(GroupEntity groupModel) {
        var group= groupRepository.save(groupModel);
        return groupDomainMapper.convertEntityToModel(group);
    }

//    public void createGroup(GroupEvent groupModel) {
//        GroupEntity groupEntity = new GroupEntity();
//        groupEntity.setId(groupModel.getId());
//        groupEntity.setName(groupModel.getName());
//        saveGroup(groupEntity);
//
//    }
}
