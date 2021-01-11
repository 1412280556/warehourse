package com.cc.api.biz.service.imp;

import com.cc.api.biz.converter.ScannerConverter;
import com.cc.api.biz.mapper.ScannerMapper;
import com.cc.api.biz.service.ScannerService;
import com.cc.api.biz.vo.ScannerVO;
import com.cc.api.common.bean.ActiveUser;
import com.cc.api.common.exception.ErrorCodeEnum;
import com.cc.api.common.exception.ServiceException;
import com.cc.api.common.pojo.biz.QrcodeDownload;
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
import java.util.ArrayList;
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
    public void submit(Long id,Integer status) {
        //根据id查出单行所有的数据
        Scanner scanner = scannerMapper.selectByPrimaryKey(id);
        System.out.println(status);
        //查出user操作用户
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Long userId  =  activeUser.getUser().getId();
        System.out.println(userId);;
        
        User user =  userService.findUserById(userId);
        
        String username = user.getUsername();
        
//        Integer scanStatus = scanner.getScanStatus();

        //判断，如果scan_status == 0 则是入库扫描，如果scan_status == 1 则为出库扫描
        if(status == 0 && scanner.getInstockstatus() == 0) {
        	scanner.setInstockstatus(1);
        	scanner.setInstocktime(new Date());
        	scanner.setUserName(username);
        	scannerMapper.updateByPrimaryKey(scanner);
        }else if(status == 1 && scanner.getOutstockstatus() == 0) {
        	scanner.setOutstockstatus(1);
        	scanner.setOutstocktime(new Date());
        	scanner.setUserName(username);
        	scannerMapper.updateByPrimaryKey(scanner);
        }else if (scanner.getOutstockstatus() == 1 || scanner.getInstockstatus() == 1) {
        	throw new ServiceException(ErrorCodeEnum.QR_CODE_ALREADY_USERD);
        }

    }

    @Override
    public Scanner queryById(Long id) {
        //根据id查询数据，查出一个scanner
        Scanner scanner =  scannerMapper.selectByPrimaryKey(id);
        if(scanner == null){
            throw new ServiceException(ErrorCodeEnum.QUERY_DATA_NULL_EXCEPTION);
        }
        return scanner;
    }

	@Override
	public List<Scanner> queryByPid(Long pid) {
		Scanner scanner = new Scanner();
		scanner.setPid(pid);
		
		
		List<Scanner> scannerList = scannerMapper.select(scanner);
		
		if(scannerList.isEmpty()){
            throw new ServiceException(ErrorCodeEnum.QUERY_DATA_NULL_EXCEPTION);
        }
		return scannerList;
	}

	@Override
	public List<Scanner> findAll() {
		return scannerMapper.selectAll();
	}

	@Override
	public List<QrcodeDownload> findImage() {
		
		List<QrcodeDownload> list = new ArrayList<QrcodeDownload>();
		
	
		List<Scanner> scanners = scannerMapper.selectAll();
		
		for(Scanner scanner : scanners) {
			QrcodeDownload qrcodeDownload = new QrcodeDownload();
			qrcodeDownload.setId(scanner.getId()); ;
			qrcodeDownload.setImageUrl(scanner.getImageUrl()); 
			list.add(qrcodeDownload);
		}
		return list;
	}
}
