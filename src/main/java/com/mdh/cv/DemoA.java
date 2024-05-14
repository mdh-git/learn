package com.mdh.cv;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DemoA {

    private String objId;

    public DemoB1 demoB1;

    public DemoB2 demoB2;

    public DemoB3 demoB3;

}
