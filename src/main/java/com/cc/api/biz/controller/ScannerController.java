package com.cc.api.biz.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cc.api.biz.service.ScannerService;
import com.cc.api.biz.vo.ScannerVO;
import com.cc.api.common.annotation.ControllerEndpoint;
import com.cc.api.common.bean.ResponseBean;
import com.cc.api.common.pojo.biz.QrcodeDownload;
import com.cc.api.common.pojo.biz.Scanner;
import com.cc.api.common.utils.ExcelBase64Util;
import com.cc.api.system.vo.PageVO;
import com.wuwenze.poi.ExcelKit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
        return ResponseBean.success("生成成功!");
    }

    /**
     * App的提交接口
     */
    @ControllerEndpoint(exceptionMessage = "提交", operation = "提交")
    @ApiOperation(value = "提交数据", notes = "提交")
    @GetMapping("/submit/{id}/{status}")
    public ResponseBean subimt(@PathVariable Long id,@PathVariable Integer status){
        scannerService.submit(id,status);
        return ResponseBean.success("提交成功!");
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
    
    /**
     * 根据pid查询
     * @param id
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "根据Pid查询", operation = "查询")
    @ApiOperation(value = "根据Pid查询", notes = "查询")
    @GetMapping("/queryByPid/{pid}")
    public ResponseBean queryByPid(@PathVariable Long pid){
        Scanner scanner = scannerService.queryByPid(pid);
        return ResponseBean.success(scanner);
    }
    
    /**
     * 下载 二维码
     */
    @PostMapping("/downloadExcel")
    public void export(HttpServletResponse response) {
        List<QrcodeDownload> list = this.scannerService.findImage();
        System.out.println(list);
        ExcelKit.$Export(QrcodeDownload.class, response).downXlsx(list, false);
        //ExcelBase64Util.$Export(QrcodeDownload.class, response).downXlsx(list, false);
    }
    
    /**
     * 导出excel
     * @param response
     */
    @ApiOperation(value = "导出excel", notes = "导出所有物资的excel表格")
    @PostMapping("/excel")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败",operation = "导出物资excel")
    public void export1(HttpServletResponse response) {
    	List<Scanner> scanner = this.scannerService.findAll();
        ExcelKit.$Export(Scanner.class, response).downXlsx(scanner, false);

        
        
    }
    
    
    
    
}
