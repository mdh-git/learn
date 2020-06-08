package com.mdh.streams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author madonghao
 * @create 2020-06-08 10:24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScore {

    private String StuName;
    private String Subject;
    private Integer Score;
}
