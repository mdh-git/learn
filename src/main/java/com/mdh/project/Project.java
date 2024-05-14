package com.mdh.project;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;

@Data
public class Project {

    private String objId;

    private String binUrl;

    private DbData dbData;

}
