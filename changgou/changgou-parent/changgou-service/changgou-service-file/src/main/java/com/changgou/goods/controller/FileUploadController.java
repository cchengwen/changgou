package com.changgou.goods.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author WEN
 * @Date 2020/11/18 22:24
 */
@RestController
@RequestMapping("/upload")
@CrossOrigin // 支持跨域
public class FileUploadController {

    public Result upload(@RequestParam("file")MultipartFile file)throws Exception{
        // 封装文件信息
        FastDFSFile fastDFSF = new FastDFSFile(
                file.getOriginalFilename(), // 文件名字， 如：1.jpg
                file.getBytes(), // 文件字节数组
                StringUtils.getFilenameExtension(file.getOriginalFilename()) // 获取文件扩展名
        );
        // 调用FastDFSUtil工具类将文件传入到FastDFS中
        String[] uploads = FastDFSUtil.upload(fastDFSF);
        // 拼接访问地址 url = “http://192.168.2.106:8080/group1/M00/00/00/wKjj5.jpg”
//        String url = new StringBuilder("http://192.168.2.106:8080/").append(uploads[0]).append("/").append(uploads[1]).toString();
        String url = FastDFSUtil.getTrackerInfo() + uploads[0] + "/" + uploads[1];
        return new Result(true, StatusCode.OK, "上传成功!", url);
    }
}
