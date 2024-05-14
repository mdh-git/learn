package com.mdh.project;

import lombok.Data;

@Data
public class Frame {

    private String objId;

    private Position position;

    private String frame_id;

    private String frame_index;

    private String frame_name;

    private String item;

    private SubFrame subFrame;

    private Holes holes;

}
