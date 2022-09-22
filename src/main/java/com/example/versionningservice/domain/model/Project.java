package com.example.versionningservice.domain.model;

import java.util.Date;

public class Project {

    private Long id;
    private String name;
    private Date creationDate;
    private boolean visibility;
    private Group group;
    private boolean commit_conflict;
    private boolean revert_conflict;
    private boolean merge_conflict;

    public Project(Long id, String name, Date creationDate, boolean visibility, Group group, boolean commit_conflict, boolean revert_conflict, boolean merge_conflict) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.visibility = visibility;
        this.group = group;
        this.commit_conflict = commit_conflict;
        this.revert_conflict = revert_conflict;
        this.merge_conflict = merge_conflict;
    }

    public Project() {
    }

    public boolean isVisibility() {
        return visibility;
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public Group getGroup() {
        return group;
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

    public void setGroup(Group group) {
        this.group = group;
    }
}