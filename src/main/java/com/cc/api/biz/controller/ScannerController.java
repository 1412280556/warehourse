package com.cc.api.biz.controller;


import com.cc.api.biz.service.ScannerService;
import com.cc.api.biz.vo.ScannerVO;
import com.cc.api.common.annotation.ControllerEndpoint;
import com.cc.api.common.bean.ResponseBean;
import com.cc.api.common.pojo.biz.Scanner;
import com.cc.api.system.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = "扫描二维码接口")
@RestController
@RequestMapping("/scanner")
public class ScannerController {

    @Autowired
    private ScannerService scannerService;

    @ApiOperation(value = "入库单列表")
    @GetMapping("/findScannerList")
    public ResponseBean findInStockList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            ScannerVO scannerVO) {
        PageVO<ScannerVO> scannerList = scannerService.findScannerList(pageNum, pageSize, scannerVO);
        return ResponseBean.success(scannerList);
    }


    /**
     * 根据id生成二维码，扫描出来的结果为id号
     */
    @ControllerEndpoint(exceptionMessage = "生成二维码失败", operation = "生成二维码")
    @ApiOperation(value = "生成二维码", notes = "生成二维码")
    @GetMapping("/generateQrCode/{id}")
    public ResponseBean qrCodeGenerate(@PathVariable Long id) throws IOException {
        scannerService.generateQrCode(id);
        return ResponseBean.success("OKay!");
    }

    /**
     * App的提交接口
     */
    @ControllerEndpoint(exceptionMessage = "提交", operation = "提交")
    @ApiOperation(value = "提交数据", notes = "提交")
    @GetMapping("/submit/{id}")
    public ResponseBean subimt(@PathVariable Long id){
        scannerService.submit(id);
        return ResponseBean.success("OKay!");
    }

    /**
     * 查询结果，根据id查询出数据
     */
    @ControllerEndpoint(exceptionMessage = "提交", operation = "提交")
    @ApiOperation(value = "提交数据", notes = "提交")
    @GetMapping("/queryById/{id}")
    public ResponseBean queryById(@PathVariable Long id){
        Scanner scanner = scannerService.queryById(id);
        return ResponseBean.success(scanner);
    }
}
