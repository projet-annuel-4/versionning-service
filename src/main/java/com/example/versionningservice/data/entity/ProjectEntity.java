package com.example.versionningservice.data.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_seq")
    private Long id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    private boolean visibility;
    @ManyToOne(fetch = FetchType.LAZY)
    private GroupEntity groupEntity;
    private boolean deleted;
    private boolean commit_conflict;
    private boolean revert_conflict;
    private boolean merge_conflict;

    public ProjectEntity(Long id, String name, Date creationDate, boolean visibility, GroupEntity groupEntity, boolean deleted, boolean commit_conflict, boolean revert_conflict, boolean merge_conflict) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.visibility = visibility;
        this.groupEntity = groupEntity;
        this.deleted = deleted;
        this.commit_conflict = commit_conflict;
        this.revert_conflict = revert_conflict;
        this.merge_conflict = merge_conflict;
    }

    public ProjectEntity() {

    }

    public boolean isCommit_conflict() {
        return commit_conflict;
    }

    public void setCommit_conflict(boolean commit_conflict) {
        this.commit_conflict = commit_conflict;
    }

    public boolean isRevert_conflict() {
        return revert_conflict;
    }

    public void setRevert_conflict(boolean revert_conflict) {
        this.revert_conflict = revert_conflict;
    }

    public boolean isMerge_conflict() {
        return merge_conflict;
    }

    public void setMerge_conflict(boolean merge_conflict) {
        this.merge_conflict = merge_conflict;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean isVisibility() {
        return visibility;
    }

    @Override
    public String toString() {
        return "ProjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", visibility=" + visibility +
                ", groupEntity=" + groupEntity.toString() +
                '}';
    }
}