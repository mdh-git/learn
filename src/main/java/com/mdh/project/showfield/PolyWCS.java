package com.mdh.project.showfield;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PolyWCS implements Serializable {

    private List<Point> points;

    public PolyWCS(List<Point> points) {
        this.points = points;
    }
}
