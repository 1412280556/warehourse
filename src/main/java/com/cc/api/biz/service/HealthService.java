package com.cc.api.biz.service;

import com.cc.api.common.pojo.biz.Health;
import com.cc.api.biz.vo.HealthVO;
import com.cc.api.system.vo.PageVO;

public interface HealthService {

    /**
     * 健康上报
     * @param healthVO
     */
    void report(HealthVO healthVO);

    /**
     * 今日是否已经报备
     * @param id
     * @return
     */
    Health isReport(Long id);

    /**
     * 签到记录
     * @return
     */
    PageVO<Health> history(Long id,Integer pageNum,Integer pageSize);
}
