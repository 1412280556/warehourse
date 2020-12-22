package com.cc.api.system.converter;

import com.cc.api.common.pojo.system.Department;
import com.cc.api.system.vo.DepartmentVO;
import org.springframework.beans.BeanUtils;

public class DepartmentConverter {


    /**
     * 转vo
     * @return
     */
    public static DepartmentVO converterToDepartmentVO(Department department){
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(department,departmentVO);
        return departmentVO;
    }
}
