package com.example.versionningservice.utils;

public final class GitCommand {

    public final static String INIT = "git init \"%s\"";
    public final static String ADD = "git -C \"%s\" add .";
    public final static String ADD_FILE = "git -C \"%s\" add \"%s\"";
    public final static String RM_FILE = "git -C \"%s\" rm \"%s\"";
    public final static String STATUS = "git -C \"%s\" status";


    public final static String COMMIT = "git -C \"%s\" commit -m \"%s\"";
    public final static String LIST_COMMIT = "git -C \"%s\" log ";
    public final static String LIST_COMMIT_FORMAT = "--format=\"%C(auto) %h %s\" ";
    public final static String REVERT_COMMIT = "git -C \"%s\" revert --no-commit %s";
    public final static String REVERT_COMMIT_CONTINUE = "git -C \"%s\" revert --continue --no-commit";
    public final static String REVERT_COMMIT_ABORT = "git -C \"%s\" revert --abort";

    public final static String MERGE = "git -C \"%s\" merge --no-commit \"%s\"";
    public final static String MERGE_ABORT = "git -C \"%s\" merge --abort";
    public final static String MERGE_CONTINUE = "git -C \"%s\" merge \"%s\"";
    public final static String CREATE_BRANCH = "git -C \"%s\" checkout -b \"%s\"";
    public final static String ACTUAL_BRANCH = "git -C \"%s\" branch --show-current";
    public final static String DELETE_BRANCH = "git -C \"%s\" branch -D \"%s\"";
    public final static String CHECKOUT = "git -C \"%s\" checkout \"%s\"";
    public final static String LIST_BRANCHES = "git -C \"%s\" branch";
    public final static String LIST_FILES = "git -C \"%s\" ls-files";

    public final static String ACTIVE_DIR = "C:\\Users\\LILOKE\\Desktop\\PC_save_1\\4eme_AL\\Projet Annuel\\versionning";
}
