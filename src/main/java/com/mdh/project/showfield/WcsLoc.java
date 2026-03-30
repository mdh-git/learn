package com.mdh.project.showfield;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class WcsLoc {
    double left;
    double top;
    double right;
    double bottom;

    public WcsLoc(double x1, double y1, double x2, double y2) {
        if (x1 < x2) {
            this.left = x1;
            this.right = x2;
        } else {
            this.right = x1;
            this.left = x2;
        }
        if (y1 > y2) {
            this.top = y1;
            this.bottom = y2;
        } else {
            this.bottom = y1;
            this.top = y2;
        }
    }

    public List<Point> toDiagonal() {
        return new ArrayList<Point>() {{
            add(new Point(left, top));
            add(new Point(right, bottom));
        }};
    }
}
