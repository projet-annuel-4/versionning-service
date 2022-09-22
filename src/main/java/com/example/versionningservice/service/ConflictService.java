package com.example.versionningservice.service;

import com.example.versionningservice.domain.model.Conflict;
import com.example.versionningservice.domain.model.ConflictType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictService {


    public List<String> getConflict(List<String> outputs){
        boolean start = false;
        List<String> conflict = new ArrayList<>();
        for(String output : outputs){
            System.out.println(" /ICI/ : " + output + " --------- " + output.length());
            if( output.contains("Unmerged paths:")){
                start = true;
            } else if (start && output.length() != 0) {
                if( !output.contains("(use \"git restore --staged <file>...\" to unstage)") &&
                        !output.contains("(use \"git add/rm <file>...\" as appropriate to mark resolution)") &&
                        !output.contains("(use \"git add <file>...\" to mark resolution)")
                ){
                    conflict.add(output);
                }
            } else if (output.length() == 0 && start){
                break;
            }
        }
        return conflict;
    }

    public List<Conflict> conflictsStringToModel(List<String> conflictsString){
        List<Conflict> conflicts = new ArrayList<>();
        for( String conflictString : conflictsString){
            Conflict conflict = new Conflict();
            if( conflictString.contains("deleted") ){
                conflict.setType(ConflictType.DELETED_CREATED);
            } else {
                conflict.setType(ConflictType.MODIFIED);
            }
            conflict.setFilenameUrl(conflictString.trim().split(":",2)[1].trim());
            conflicts.add(conflict);
        }
        return conflicts;
    }
}
