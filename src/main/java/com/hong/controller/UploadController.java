package com.hong.controller;

import com.hong.entity.FileObject;
import com.hong.entity.bean.Menu;
import com.hong.entity.bean.UserMenu;
import com.hong.service.UserMenuService;
import com.hong.util.FTPTools;
import com.hong.util.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author LJH
 * @CreateTime 2020/07/28 15:06
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    @Autowired
    private UserMenuService userMenuService;

    private int i=0;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.hostname}")
    private String hostName;
    @Value("${ftp.password}")
    private String passWord;
    @Value("${ftp.workingPath}")
    private String workingPath;
    @Value("${ftp.username}")
    private String userName;




    @RequestMapping("")
    public String index(){
        return "upload";
    }


    @RequestMapping("/uploadFile")
    @ResponseBody
    public Object upload(@RequestParam("file") MultipartFile file,HttpSession session)  {

        boolean isFlag = false; //上传标记
//        boolean adminFlag =false;  //判断admin标记 是否是admin 是则可以任意上传

        String fileName = file.getOriginalFilename();//上传到服务器的文件名
        if (file.isEmpty()) {
           return null;
        }

        //判断用户是否有权限
        String uploadUserName = session.getAttribute("userName").toString();
        String menuName = fileName.substring(0,2).toUpperCase();
        List<UserMenu> list =   userMenuService.findUserAndMenuListByName(uploadUserName);



        if(uploadUserName.equals("admin"))  {
            for(Menu m:userMenuService.findMenuList()){
                if(menuName.equals(m.getMenuName())){
                    isFlag = true;
                    break;
                }
            }

        }else{
            for(UserMenu u:list){
                if(uploadUserName.equals(u.getUser().getUserName())&& menuName.equals(u.getMenu().getMenuName()) && u.getState() == 1 ){
                    isFlag = true;
                    break;
                }
            }
        }



        String tarPath ="";
        //存放文件的根目录
        String filePath ="F:\\ftpfile\\fileupload\\";

        if(isFlag) {
            //匹配服务器是否存在文件 start
            List<FileObject> listFile = new ArrayList<FileObject>();
            UploadController lxf = new UploadController();

            //声明一个List，用于存放所有得到的XML文件
            List<File> fileList = new ArrayList<>();
            lxf.getUpLoadFiles(filePath, fileList);
            for (File f : fileList) {
                //思路：根据文件名 在读取全部文件的时候一个个比对。如果文件或文件夹不存在则创建文件夹，
                if (fileName.equals(f.getName())) {
                    //获取文件地址
                    System.out.println("找到了 路径为" + f.getParentFile() + " || name :" + f.getName() + " || 分割后 :" + f.getName().substring(0, 2));
                } else {
                    if (!new File(filePath + fileName.substring(0, 2).toUpperCase()).exists()) {
                        new File(filePath + fileName.substring(0, 2).toUpperCase()).mkdirs();
                    }

                }
                tarPath = workingPath + fileName.substring(0, 2).toUpperCase();
            }
            //end

//        File dest = new File(filePath +"\\"+ fileName);
            try {
                //上传到本地
                //   file.transferTo(dest);
                //上传到ftp服务器
              boolean f= FTPTools.upload(hostName, port, userName, passWord, tarPath, file.getInputStream(), file.getOriginalFilename());
              if(f)
                  return LogUtils.messageTip(200, "上传成功！");
              else
                  return LogUtils.messageTip(500, "上传失败！");

            } catch (Exception e) {
                return LogUtils.messageTip(500, "上传失败！");
            }
        }
        return LogUtils.messageTip(500, "用户["+uploadUserName+"]没有此模块["+menuName+"]权限,或菜单不存在此模块。");
    }


    /**
     * 获取所有XML文件
     * @param path 文件路径
     * @param fileList 存放所有XML文件的列表
     */
    public void getUpLoadFiles(String path, List<File> fileList) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File fileIndex : files) {

            if (!fileIndex.exists()) {
                throw new NullPointerException("Cannot find " + fileIndex);
            } else if (fileIndex.isFile()) {
                fileList.add(fileIndex);
            } else {
                if (fileIndex.isDirectory()) {
                    getUpLoadFiles(fileIndex.getAbsolutePath(), fileList);
                }
            }
        }
    }





    /**
     * 服务器文件遍历
     * @return
     */
    @RequestMapping("/serviceFile")
    @ResponseBody
    public Object serviceFileList(){
        //声明一个HashMap，用于存放XML文件，格式：<文件名，文件>，以文件名为KEY，可以得到整个文件所在的路径和文件名
        List<FileObject> listFile = new ArrayList<FileObject>();
        UploadController lxf = new UploadController();
        //存放xml文件的根目录
        String filePath ="F:\\ftpfile\\fileupload";
        //声明一个List，用于存放所有得到的XML文件
        List<File> fileList = new ArrayList<>();
        lxf.getUpLoadFiles(filePath, fileList);
        for (File f : fileList) {
            FileObject fileObject =new FileObject();


            fileObject.setFileSize(f.length());
            fileObject.setFileName(f.getName());
            fileObject.setFileLastModTime(LogUtils.getTime(f.lastModified()));
            fileObject.setAbsolutePath(f.getAbsolutePath());
            listFile.add(fileObject);
            i++;
        }
        return  LogUtils.buildLayuiGridJsonString(listFile,i);
    }



//    /**
//     * 本地文件遍历
//     * @return
//     */
//    @RequestMapping("/localFile")
//    @ResponseBody
//    public Object localFileList(){
//        //声明一个HashMap，用于存放XML文件，格式：<文件名，文件>，以文件名为KEY，可以得到整个文件所在的路径和文件名
//        List<FileObject> listFile = new ArrayList<FileObject>();
//        UploadController lxf = new UploadController();
//        //存放xml文件的根目录
//        String filePath ="F:\\ftpfile\\localfile";
//        //声明一个List，用于存放所有得到的XML文件
//        List<File> fileList = new ArrayList<>();
//        lxf.getUpLoadFiles(filePath, fileList);
//        for (File f : fileList) {
//
//            FileObject fileObject =new FileObject();
////            //思路：根据文件名 在读取全部文件的时候一个个比对。
////            if("Mono.Cecil.dll".equals(f.getName())){
////                //获取文件地址
////                System.out.println("找到了 路径为"+f.getParentFile()+" || name :"+f.getName());
////            }
//
//            fileObject.setFileSize(f.length());
//            fileObject.setFileName(f.getName());
//            fileObject.setFileLastModTime(LogUtils.getTime(f.lastModified()));
//            fileObject.setAbsolutePath(f.getAbsolutePath());
//            listFile.add(fileObject);
//            i++;
//        }
//        return  LogUtils.buildLayuiGridJsonString(listFile,i);
//    }

}
