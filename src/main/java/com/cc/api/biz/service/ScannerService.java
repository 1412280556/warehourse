package com.cc.api.biz.service;

import com.cc.api.biz.vo.ScannerVO;
import com.cc.api.common.pojo.biz.QrcodeDownload;
import com.cc.api.common.pojo.biz.Scanner;
import com.cc.api.system.vo.PageVO;

import java.io.IOException;
import java.util.List;

public interface ScannerService {

    PageVO<ScannerVO> findScannerList(Integer pageNum, Integer pageSize, ScannerVO scannerVO);

    void generateQrCode(Long id) throws IOException;

    void submit(Long id,Integer status);

    Scanner queryById(Long id);
    
    List<Scanner> queryByPid(Long pid);
    
    List<Scanner> findAll();
    
    List<QrcodeDownload> findImage();
}
