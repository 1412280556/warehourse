package com.cc.api.biz.service;

import com.cc.api.biz.vo.ScannerVO;
import com.cc.api.common.pojo.biz.Scanner;
import com.cc.api.system.vo.PageVO;

import java.io.IOException;

public interface ScannerService {

    PageVO<ScannerVO> findScannerList(Integer pageNum, Integer pageSize, ScannerVO scannerVO);

    void generateQrCode(Long id) throws IOException;

    void submit(Long id);

    Scanner queryById(Long id);
}
