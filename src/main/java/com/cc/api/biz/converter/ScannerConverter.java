package com.cc.api.biz.converter;

import com.cc.api.biz.vo.ScannerVO;
import com.cc.api.common.pojo.biz.Scanner;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ScannerConverter {

    /**
     * 转voList
     * @param Scanners
     * @return
     */
    public static List<ScannerVO> converterToVOList(List<Scanner> Scanners) {
        List<ScannerVO> ScannerVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(Scanners)){
            for (Scanner Scanner : Scanners) {
                ScannerVO ScannerVO = converterToScannerVO(Scanner);
                ScannerVOS.add(ScannerVO);
            }
        }
        return ScannerVOS;
    }


    /***
     * 转VO
     * @param Scanner
     * @return
     */
    public static ScannerVO converterToScannerVO(Scanner Scanner) {
        ScannerVO ScannerVO = new ScannerVO();
        BeanUtils.copyProperties(Scanner,ScannerVO);
        return ScannerVO;
    }
}
