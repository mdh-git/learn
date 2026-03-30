package com.mdh.project.showfield;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Loc {
    private String itemName;
    private String frameId;
    private CoordinateTypeEnum coordinateTypeEnum;

    private String locJson;

    public Loc(String itemName) {
        this.itemName = itemName;
        this.coordinateTypeEnum = CoordinateTypeEnum.NONE;
    }

    public Loc(String itemName, String frameId) {
        this.itemName = itemName;
        this.coordinateTypeEnum = CoordinateTypeEnum.NONE;
        this.frameId = frameId;
    }


    public Loc(String frameId, CoordinateTypeEnum coordinateTypeEnum, String locJson) {
        this.frameId = frameId;
        this.coordinateTypeEnum = coordinateTypeEnum;
        this.locJson = locJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loc loc = (Loc) o;
        return Objects.equals(itemName, loc.itemName) && Objects.equals(frameId, loc.frameId) && coordinateTypeEnum == loc.coordinateTypeEnum && Objects.equals(locJson, loc.locJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, frameId, coordinateTypeEnum, locJson);
    }


}
