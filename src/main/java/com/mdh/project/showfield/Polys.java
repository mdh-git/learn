package com.mdh.project.showfield;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Polys implements Serializable {
    List<PolyWCS> polys;
}
