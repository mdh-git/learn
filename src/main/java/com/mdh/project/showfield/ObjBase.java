package com.mdh.project.showfield;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "jsonId")
public class ObjBase {

    @Setter
    protected String objId;

    public ObjBase(String objId) {
        if (StrUtil.isNotBlank(objId)) this.objId = objId;
        else this.objId = IdGenerator.identity();
    }
}
