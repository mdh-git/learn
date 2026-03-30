package com.mdh.project.showfield;

import java.util.ArrayList;
import java.util.List;

public class SubItem {

    private List<SubAxis> subAxis;

    public List<VarTreeDTO> convertShowVar() {
        List<VarTreeDTO> varTreeList = new ArrayList<>();
        varTreeList.addAll(ShowVarHandler.getShowVar(subAxis, "轴网平面", null));
        return varTreeList;
    }
}
