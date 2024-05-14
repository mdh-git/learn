package com.mdh.project;

import lombok.Data;

@Data
public class SubFrame {

    private String objId;

    private Position position;

    private String title;

    private Integer scale;

    private SubPlan subPlan;

    private SubFacade subFacade;

    private SubSection subSection;

    private SubDetailed subDetailed;

    private String errorMsg;

}
