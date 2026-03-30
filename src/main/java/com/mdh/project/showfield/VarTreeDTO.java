package com.mdh.project.showfield;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VarTreeDTO {

    private String frameId;

    private String label;

    private String itemName;

    private CoordinateTypeEnum coordinateType;


    private String coordinate;

    private String attribute;

    private List<VarTreeDTO> children = new ArrayList<>();
}
