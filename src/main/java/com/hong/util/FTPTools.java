package com.hong.util;

import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

public class FTPTools {


    //设置私有不能实例化
    private FTPTools() {

    }

    /**
     * 上传
     *
     * @param hostname ip或域名地址
     * @param port  端口
     * @param username 用户名
     * @param password 密码
     * @param workingPath 服务器的工作目
     * @param inputStream 要上传文件的输入流
     * @param saveName    设置上传之后的文件名
     * @return
     */
    public static boolean upload(String hostname, int port, String username, String password, String workingPath, InputStream inputStream, String saveName) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        //1 测试连接
        if (connect(ftpClient, hostname, port, username, password)) {
            try {
                //2 检查工作目录是否存在
                if (ftpClient.changeWorkingDirectory(workingPath)) {
                    // 3 检查是否上传成功
                    if (storeFile(ftpClient, saveName, inputStream)) {
                        flag = true;
                        disconnect(ftpClient);
                    }
                }
            } catch (IOException e) {
                System.out.println("工作目录不存在");
                e.printStackTrace();
                disconnect(ftpClient);
            }
        }
        return flag;
    }

    /**
     * 断开连接
     *
     * @param ftpClient
     * @throws Exception
     */
    public static void disconnect(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
                System.out.println("已关闭连接");
            } catch (IOException e) {
                System.out.println("没有关闭连接");
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试是否能连接
     *
     * @param ftpClient
     * @param hostname  ip或域名地址
     * @param port      端口
     * @param username  用户名
     * @param password  密码
     * @return 返回真则能连接
     */
    public static boolean connect(FTPClient ftpClient, String hostname, int port, String username, String password) {
        boolean flag = false;
        try {
            //ftp初始化的一些参数
            ftpClient.connect(hostname, port);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("UTF-8");
            if (ftpClient.login(username, password)) {
                System.out.println("连接ftp成功");
                flag = true;
            } else {
                System.out.println("连接ftp失败，可能用户名或密码错误");
                try {
                    disconnect(ftpClient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("连接失败，可能ip或端口错误");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param ftpClient
     * @param saveName        全路径。如/home/public/a.txt
     * @param fileInputStream 要上传的文件流
     * @return
     */
    public static boolean storeFile(FTPClient ftpClient, String saveName, InputStream fileInputStream) {
        boolean flag = false;
        try {
            if (ftpClient.storeFile(saveName, fileInputStream)) {
                flag = true;
                System.out.println("上传成功");
                disconnect(ftpClient);
            }
        } catch (IOException e) {
            System.out.println("上传失败");
            disconnect(ftpClient);
            e.printStackTrace();
        }
        return flag;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String hostname = "192.168.67.1";
        int port = 21;
        String username = "anonymous";
        String password = "";
        String workingPath = "/";
        String str = "F:\\nginx-1.15.0\\localfile\\ssss\\Mono.Cecil.dll";
        String saveName = "sadas.dll";
        InputStream fileInputStream = new FileInputStream(new File(str));
        System.out.println(FTPTools.upload( hostname, port, username, password, workingPath, fileInputStream, saveName));
    }
}
