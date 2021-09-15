package com.mdh.tool.xmind;

import lombok.Data;

import java.util.List;

/**
 * @Author: madonghao
 * @Date: 2021/9/13 10:42 上午
 */
@Data
public class OrgTree {

    private String label;

    private String name;

    private String job;

    private List<OrgTree> children;
}
