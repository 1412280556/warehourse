package com.cc.api.system.service;

import com.cc.api.common.pojo.system.ImageAttachment;
import com.cc.api.system.vo.ImageAttachmentVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadService {
    String uploadImage(MultipartFile file) throws IOException; //图片上传
    List<ImageAttachment> findImageList(ImageAttachmentVO imageAttachmentVO); //图片列表
    void delete(Long id); //删除图片
}
