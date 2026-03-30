package com.mdh.project.showfield;

import cn.hutool.core.util.IdUtil;

public class IdGenerator {
    public static String identity() {
        return IdUtil.getSnowflakeNextIdStr();
    }
}
