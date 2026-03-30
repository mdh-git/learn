package com.mdh.project.showfield;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ConvertBase extends ObjBase {

    @Setter
    private List<Loc> locList;

    public ConvertBase(String objId, List<Loc> locList) {
        super(objId);
        this.locList = new ArrayList<>();
        if (locList != null) {
            this.locList.addAll(locList);
        }
    }
}
