package com.cc.api.biz.service.imp;

import com.cc.api.biz.converter.ScannerConverter;
import com.cc.api.biz.mapper.ScannerMapper;
import com.cc.api.biz.service.ScannerService;
import com.cc.api.biz.vo.ScannerVO;
import com.cc.api.common.bean.ActiveUser;
import com.cc.api.common.exception.ErrorCodeEnum;
import com.cc.api.common.exception.ServiceException;
import com.cc.api.common.pojo.biz.Scanner;
import com.cc.api.common.pojo.system.User;
import com.cc.api.common.utils.QrCodeUtil;
import com.cc.api.system.service.UserService;
import com.cc.api.system.vo.PageVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class ScannerServiceImpl implements ScannerService {

    @Autowired
    private ScannerMapper scannerMapper;
    
    @Autowired
    private UserService userService;


    @Override
    public PageVO<ScannerVO> findScannerList(Integer pageNum, Integer pageSize, ScannerVO scannerVO) {
        PageHelper.startPage(pageNum,pageSize);
        Example o = new Example(Scanner.class);
        Example.Criteria criteria = o.createCriteria();
        o.setOrderByClause("id desc");
        if(scannerVO.getPid()!=null&&!"".equals(scannerVO.getPid())){
            criteria.andLike("pid","%"+scannerVO.getPid()+"%");
        }
        if(scannerVO.getNumber()!=null){
            criteria.andEqualTo("number",scannerVO.getNumber());
        }
        if(scannerVO.getInstocktime()!=null){
            criteria.andEqualTo("Scannertime",scannerVO.getInstocktime());
        }
        if(scannerVO.getOutstocktime()!=null){
            criteria.andEqualTo("outstocktime",scannerVO.getOutstocktime());
        }
        List<Scanner> Scanners = scannerMapper.selectByExample(o);
        List<ScannerVO> scannerVOS= ScannerConverter.converterToVOList(Scanners);
        PageInfo<Scanner> ScannerPageInfo = new PageInfo<>(Scanners);
        return new PageVO<>(ScannerPageInfo.getTotal(),scannerVOS);

    }

    @Override
    public void generateQrCode(Long id) throws IOException {

        String QrCodeBase64 = QrCodeUtil.createQRCode(id+"",100,100,String.valueOf(id));
        System.err.println(id);
        Scanner scanner = scannerMapper.selectByPrimaryKey(id);
        scanner.setImageUrl(QrCodeBase64);
        scannerMapper.updateByPrimaryKey(scanner);
    }

    @Override
    public void submit(Long id) {
        //根据id查出单行所有的数据
        Scanner scanner = scannerMapper.selectByPrimaryKey(id);
        
        
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Long userId  =  activeUser.getUser().getId();
        System.out.println(userId);;
        
        User user =  userService.findUserById(userId);
        
        String username = user.getUsername();
        System.out.println(username);

        if(scanner.getInstockstatus() == null || scanner.getInstockstatus().equals("") || scanner.getInstockstatus()==0){
            //设定1 为已入库
            scanner.setInstockstatus(1);
            //设定当前时间
            scanner.setInstocktime(new Date());
          
            scanner.setUserName(username);

            //更新数据库
            scannerMapper.updateByPrimaryKey(scanner);
        }else if(scanner.getOutstockstatus() == null || scanner.getOutstockstatus().equals("") || scanner.getOutstockstatus()==0){
            //设定1 为已入库
            scanner.setOutstockstatus(1);
            //设定当前时间
            scanner.setOutstocktime(new Date());
            scanner.setUserName(username);
            //更新数据库
            scannerMapper.updateByPrimaryKey(scanner);
        }
    }

    @Override
    public Scanner queryById(Long id) {
        //根据id查询数据，查出一个scanner
        Scanner scanner =  scannerMapper.selectByPrimaryKey(id);
        if(scanner == null){
            throw new ServiceException(ErrorCodeEnum.Query_DATA_NULL_EXCEPTION);
        }
        return scanner;
    }
}
