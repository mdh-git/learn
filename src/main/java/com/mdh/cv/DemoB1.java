package com.mdh.cv;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemoB1 {

    private String objId;

    private DemoC1 demoC1;

    private DemoC2 demoC2;

    private DemoC3 demoC3;
}
