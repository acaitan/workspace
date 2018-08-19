package com.nowcoder.service;

import com.nowcoder.util.ToutiaoUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Service

public class QiniuService {
    //构造一个带指定Zone对象的配置类
    //Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释

    private static final Logger logger = LoggerFactory.getLogger(ToutiaoUtil.class);
    //...生成上传凭证，然后准备上传
    String accessKey = "xCHEA3CBCoJr1ukXi2hvYAvoeylJJT7v9EdGYLMi";
    String secretKey = "xCHEA3CBCoJr1ukXi2hvYAvoeylJJT7v9EdGYLMi";
    String bucket = "nowcoder";
    //如果是Windows情况下，格式是 D:\\qiniu\\test.png
    //String localFilePath = "/home/qiniu/test.png";
    //默认不指定key的情况下，以文件内容的hash值作为文件名
    //String key = null;

    Auth auth = Auth.create(accessKey, secretKey);
    String upToken = auth.uploadToken(bucket);
    Configuration cfg = new Configuration(Zone.zone0());
    //...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);
    public String saveImage(MultipartFile file){

        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0){
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos+1).toLowerCase();

        if (!ToutiaoUtil.isPicture(fileExt)) {
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;

        try {
            Response res = uploadManager.put(file.getBytes(), fileName, upToken);
            //解析上传成功的结果
           // DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.print(res.bodyString());
            return null;
        } catch (QiniuException ex) {
            logger.error("七牛异常"+ex.getMessage());
            return null;
        }catch (IOException e){
            logger.error("七牛异常"+e.getMessage());
            return null;
        }

    }


}
