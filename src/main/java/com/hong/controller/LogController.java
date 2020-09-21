package com.hong.controller;

import com.hong.entity.FileObject;
import com.hong.entity.LogObject;
import com.hong.util.LogUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.hong.util.LogUtils.getFiles;


/**
 * @Author LJH
 * @CreateTime 2020/07/09 10:18
 */
@Controller
@RequestMapping("/log")
public class LogController {

    //引入日志时需要导入的Jar是：import org.slf4j.Logger;和import org.slf4j.LoggerFactory;
    private static Logger log = LoggerFactory.getLogger(LogController.class);


    @RequestMapping("/")
    public String index() {

        return "index";

    }

    public static void main(String[] args) {
        log.info("CCCCC[{}]","BBBBB");
    }
    /**
     * 循环拆分日志内容 记录到list集合
     * */
    @ResponseBody
    @RequestMapping(value = "/logInfo")
    public Object splitlogInfo(String fileName) {
        List<LogObject> list = new ArrayList<LogObject>(); //存入集合
        String[] arr = null;
        int i = 0;


        if(StringUtils.isEmpty(fileName))
            fileName=" ";

        try {
//            BufferedReader in = new BufferedReader(new FileReader(fileName));
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                LogObject logObject = new LogObject();
                arr = str.split("@"); //分割每行日志
                logObject.setLogTime(arr[0]);
                logObject.setHappenAddress(arr[1]);
                logObject.setLogInfo(arr[2]);
                logObject.setIp(LogUtils.getIpAdress());
                logObject.setParms(LogUtils.getLogParms(arr[2]));//getLogParms 将日志信息传入此方法可解析分解参数出来
                list.add(logObject);
                i++;
            }
        } catch (IOException e) {
        }
        return LogUtils.buildLayuiGridJsonString(list,i);
    }

    @RequestMapping("/getFileAttr")
    @ResponseBody
    public Object getFileAttr(@RequestParam(value = "fileName", defaultValue = "") String fileName) {
        List<File> fileList = new ArrayList<>();
        getFiles("E://logs//", fileList);
        List<FileObject> list = new ArrayList<FileObject>(); //存入集合
        int i = 0;
        for (File all : fileList){
            FileObject fileObject =new FileObject();
            fileObject.setFileName(all.getName());
            fileObject.setFileSize(all.length());
            fileObject.setFileLastModTime(LogUtils.getTime(all.lastModified()));
            fileObject.setAbsolutePath(all.getAbsolutePath());
            if(StringUtils.isEmpty(fileName))
            {
                list.add(fileObject);
            }else{
                //查询需要的日志文件
                if(fileName.equals(all.getName().toString())) {
                    list.add(fileObject);
                }
            }
            i++;
        }

        return  LogUtils.buildLayuiGridJsonString(list,i);
    }


}

