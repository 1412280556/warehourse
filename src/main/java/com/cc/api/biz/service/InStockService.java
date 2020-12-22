package com.cc.api.biz.service;

import com.cc.api.biz.vo.InStockDetailVO;
import com.cc.api.biz.vo.InStockVO;
import com.cc.api.common.pojo.biz.InStock;
import com.cc.api.system.vo.PageVO;

import java.io.IOException;
import java.util.List;

public interface InStockService {

    /**
     * 提交按钮 提交记录到数据库 改变状态
     */
    void submitQrCode(Long id);

    /**
     * 入库单列表
     * @param pageNum
     * @param pageSize
     * @param inStockVO
     * @return
     */
    PageVO<InStockVO> findInStockList(Integer pageNum, Integer pageSize, InStockVO inStockVO);


    /**
     * 入库单明细
     * @param id
     * @return
     */
    InStockDetailVO detail(Long id,int pageNo,int pageSize) throws IOException;

    /**
     * 删除入库单
     * @param id
     */
    void delete(Long id);

    /**
     * 物资入库
     * @param inStockVO
     */
    void addIntoStock(InStockVO inStockVO);

    /**
     * 移入回收站
     * @param id
     */
    void remove(Long id);

    /**
     * 还原从回收站中
     * @param id
     */
    void back(Long id);

    /**
     * 入库审核
     * @param id
     */
    void publish(Long id);


    List<InStock> findAll();

//    void generate(String s);
}
