package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 实现FastDFS文件管理
 * 功能有：
 *      1、文件上传
 *      2、文件删除
 *      3、文件下载
 *      4、文件信息获取
 *      5、Storage信息获取
 *      6、Tracker信息获取
 * @Author WEN
 * @Date 2020/11/17 21:49
 */
public class FastDFSUtil {

    /**
     * 加载Tracker连接信息
     */
    static {
        try {
            // 查询classpath下的文件路径
            String fileName = new ClassPathResource("fdfs_client.conf").getPath();
            // 加载Tracker连接信息
            ClientGlobal.init(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     * @param fastDFSFile 上传的文件信息封装
     * @return
     */
    public static String[] upload(FastDFSFile fastDFSFile)throws Exception{
        // 附加信息（可用可不用）
        NameValuePair[] meta_list = new NameValuePair[2];
        meta_list[0] = new NameValuePair("拍摄地址", "北京");
        meta_list[1] = new NameValuePair("author", fastDFSFile.getAuthor());

//        // 创建一个Tracker访问的客户端对象TrackerClient
//        TrackerClient trackerClient = new TrackerClient();
//        // 通过TrackerClient访问TrackerServer服务
//        TrackerServer trackerServer = trackerClient.getConnection();

        // 获取 TrackerServer
        TrackerServer trackerServer = getTrackerServer();
        // 通过TrackerServer的连接信息可以获取Storage的连接信息，创建StorageClient对象存储Storage的连接信息
//        StorageClient storageClient = new StorageClient(trackerServer, null);

        // 获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);
        /**
         * 通过StorageClient访问Storage，实现文件上传，并且获取文件上传后的存储信息
         *  参数说明：
         *      参数1：上传文件的字节数组
         *      参数2：文件的扩展名，如：jpg，png等
         *      参数3：附加参数，比如：拍摄地址：北京
         *
         * 返回参数uploads说明（注：此返回的是一个String[]数组）：
         *      数组1(uploads[0])：文件上传所存储的Storage的组名字，如：group1
         *      数组2(uploads[1])：文件存储到Storage上的文件名字，如：M00/02/44/1.jpg
         */
        String[] uploads = storageClient.upload_appender_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        return uploads;
    }

    /**
     * 获取文件信息
     * @param groupName 文件的组名，如：group1
     * @param remoteFileName 文件的存储路径名字，如：M00/00/00/wKjj5.jpg
     * @return
     * @throws Exception
     */
    public static FileInfo getFile(String groupName, String remoteFileName)throws Exception{
        // 获取StorageClient
        StorageClient storageClient = getStorageClient(getTrackerServer());
        // 获取文件信息
        FileInfo file_info = storageClient.get_file_info(groupName, remoteFileName);
        return file_info;
    }

    /**
     * 文件下载
     * @param groupName 文件的组名，如：group1
     * @param remoteFileName 文件的存储路径名字，如：M00/00/00/wKjj5.jpg
     * @throws Exception
     */
    public static InputStream downloadFile(String groupName, String remoteFileName)throws Exception{
        // 获取StorageClient
        StorageClient storageClient = getStorageClient(getTrackerServer());
        // 文件下载
        byte[] bytes = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 文件删除
     * <p>注意：在删除文件时，如果是nginx配置的会有缓存，在nginx.conf的访问图片资源配置下添加：add_header Cache-Control no-store</p>
     * @param groupName 文件的组名，如：group1
     * @param remoteFileName 文件的存储路径名字，如：M00/00/00/wKjj5.jpg
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFileName)throws Exception{
        // 获取StorageClient
        StorageClient storageClient = getStorageClient(getTrackerServer());
        // 删除文件
        storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     * 获取Storage信息
     * @return
     * @throws Exception
     */
    public static StorageServer getStorageServer()throws Exception{
        // 创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        // 通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 获取Storage信息
        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     * 获取Storage的IP和端口信息
     * @param groupName 文件的组名，如：group1
     * @param remoteFileName 文件的存储路径名字，如：M00/00/00/wKjj5.jpg
     * @return
     */
    public static ServerInfo[] getServerInfos(String groupName, String remoteFileName)throws Exception{
        // 创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        // 通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 获取Storage的IP和端口信息
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    /**
     * 获取Tracker信息，IP与端口
     * @throws Exception
     * @return 返回IP与端口
     */
    public static String getTrackerInfo()throws Exception{
        // 获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();
        // Tracker的IP，Http端口
        String ip = trackerServer.getInetSocketAddress().getHostString(); // ip地址
        int tracker_http_port = ClientGlobal.getG_tracker_http_port(); // 8080
        String url = ip +":"+tracker_http_port+"/";
        return url;

    }

    /**
     * 获取Trackerserver
     * @return
     * @throws Exception
     */
    private static TrackerServer getTrackerServer()throws Exception{
        // 创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        // 通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /**
     * 获取StorageClient
     * @param trackerServer
     * @return
     */
    private static StorageClient getStorageClient(TrackerServer trackerServer){
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }


    public static void main(String[] args) throws Exception{
//        FileInfo fileInfo = getFile("group1", "M00/00/00/wKjj5.jpg");
//        System.out.println(fileInfo.getSourceIpAddr());
//        System.out.println(fileInfo.getFileSize());

//         // 文件下载
//        InputStream is = downloadFile("group1", "M00/00/00/wKjj5.jpg");
//        // 将文件写入到本地磁盘
//        FileOutputStream os = new FileOutputStream("E:\\迅雷下载");
//        // 定义一个缓冲区
//        byte[] bytes = new byte[1024];
//        while (is.read(bytes) != -1){
//            os.write(bytes);
//        }
//        // 刷新缓冲区
//        os.flush();
//        os.close();
//        is.close();

        /**
         * 文件删除
         * 注意：在删除文件时，如果是nginx配置的会有缓存，在nginx.conf的访问图片资源配置下添加：add_header Cache-Control no-store
         */
//        deleteFile("group1", "M00/00/00/wKjj5.jpg");

        // 获取Storage信息
//        StorageServer storageServer = getStorageServer();
//        System.out.println(storageServer.getStorePathIndex()); // 输出Storage下标
//        System.out.println(storageServer.getInetSocketAddress().getHostString()); // 输出StorageIP地址

        // 获取Storage的IP和端口信息
        ServerInfo[] serverInfos = getServerInfos("group1", "M00/00/00/wKjj5.jpg");
        for (ServerInfo serverInfo : serverInfos) {
            System.out.println(serverInfo.getIpAddr()+":"+serverInfo.getPort());
        }
    }



}
