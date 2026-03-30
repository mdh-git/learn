package com.mdh.project.showfield;

public enum CoordinateTypeEnum {

    DIAGONAL("对角坐标"),
    RANGE("范围"),
    TEXT("文字"),
    NONE("没有坐标"),
    LINE("线"),
    DIAGONAL_ANGLE("对角坐标弧度");

    private String name;

    public String getName() {
        return this.name;
    }

    private CoordinateTypeEnum(final String name) {
        this.name = name;
    }
}
