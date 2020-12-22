package com.cc;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@EnableTransactionManagement  //开启事务管理
@MapperScan("com.cc.api.*.mapper") //扫描mapper
@SpringBootApplication
@Import(FdfsClientConfig.class)
public class WareHourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WareHourseApplication.class, args);
    }

}
