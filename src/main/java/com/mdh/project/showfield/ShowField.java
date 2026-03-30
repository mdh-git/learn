package com.mdh.project.showfield;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ShowField {

    // 自定义展示的信息
    String label() default "";

    // 对象中的字段
    String labelField() default "";

    // 展示对象或者list处理
    boolean child() default false;

    // 坐标展示
    CoordinateTypeEnum coordinateType() default CoordinateTypeEnum.NONE;

    boolean isMap() default false;
}
