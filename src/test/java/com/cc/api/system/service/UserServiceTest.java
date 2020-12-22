package com.cc.api.system.service;

import com.cc.api.common.pojo.system.User;
import com.cc.api.common.utils.QrCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    private UserService userService;

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

}
