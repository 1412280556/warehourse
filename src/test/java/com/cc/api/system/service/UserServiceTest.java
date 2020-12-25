package com.cc.api.system.service;

import com.cc.api.biz.mapper.InstockItemMapper;
import com.cc.api.biz.service.InStockService;
import com.cc.api.biz.vo.InStockDetailVO;
import com.cc.api.biz.vo.InStockItemVO;
import com.cc.api.common.pojo.system.User;
import com.cc.api.common.utils.QrCodeUtil;
import com.cc.api.common.utils.WordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private InStockService inStockService;

    @Test
    void findUserByName() {
        User user = userService.findUserByName("admin");
        log.info("user={}",user);
    }

    @Test
    void code() throws IOException {


        System.out.println(QrCodeUtil.createQRCode("this is a code",100,100,"111"));

    }

    @Test
    void CodeUtils(){

        String content = "www.baidu.com";
        CodeUtils.productQrCodeWithLog(content,100,100,true, String.valueOf(63));
    }

    @Test
    void freemarker() throws Exception {

//
//        Map<String, Object> dataMap = new HashMap<String, Object>();
//        //查出来列表中的所有的id,和ImageURL。（base64的图片，需要除去头）;
//        InStockDetailVO detailVO = inStockService.detail(127L,1,3);
//        System.out.println(detailVO);
//        for (InStockItemVO detail : detailVO.getItemVOS()){
//            System.out.println(detail.getImageUrl());
//            String ImageUrl = detail.getImageUrl().substring(22);
//            //日期
//            dataMap.put("date", new Date());
//
//            //图片
//            dataMap.put("image", ImageUrl);
//
//            WordUtil wordUtil = new WordUtil();
//
//            wordUtil.createWord(dataMap, "test.ftl", "D:/", "a.doc");
//        }
    }

}
