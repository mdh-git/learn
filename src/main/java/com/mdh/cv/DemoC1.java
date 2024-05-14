package com.mdh.cv;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemoC1 {

    private String objId;

    private DemoD1 demoD1;

    private DemoD2 demoD2;

    private DemoD3 demoD3;
}
