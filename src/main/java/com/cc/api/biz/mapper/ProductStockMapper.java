package com.cc.api.biz.mapper;

import com.cc.api.common.pojo.biz.ProductStock;
import com.cc.api.biz.vo.ProductStockVO;
import com.cc.api.biz.vo.ProductVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProductStockMapper extends Mapper<ProductStock> {

    /**
     * 库存列表
     * @param productVO
     * @return
     */
    List<ProductStockVO> findProductStocks(ProductVO productVO);

    /**
     * 库存信息(饼图使用)
     * @return
     */
    List<ProductStockVO> findAllStocks(ProductVO productVO);
}
