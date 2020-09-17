package com.hong.util;

import cn.hutool.core.util.StrUtil;
import com.hong.entity.LayuiDataVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author LJH
 * @CreateTime 2020/07/14 15:03
 */
public class LogUtils {


    /**
     * 日志名字根据JS文件名，动态指定
     *
     * @param logName 是日志文件的名字
     *
     */
//    public static void setLogName(String logName){
//        String userPath = "E:\\logs\\";
//        System.setProperty("FilePath",userPath);
//        System.setProperty("logFileName",logName+".log");//logFileName在log4j2.xml中使用
//
//        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
//
//        File filePath = new File("src\\main\\resources\\log4j2-spring.xml");
//        URI configURI = filePath.toURI();
//        System.out.println("configURI = " + configURI.toString());//configURI = file:/E:/workspace4J2EE/KMSTool/log4j2.xml
//        ctx.setConfigLocation(configURI);
//
//
////		ctx.reconfigure();//如果log4j2.xml在默认路径(src目录)下的情况，就不用获取xml文件的路径了
//    }

    /**
     * 获取某文件下的父文件夹以及子文件夹的所有文件
     * @param path
     * @param fileList
     */
    public static void getFiles(String path, List<File> fileList) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File fileIndex : files) {
            if (!fileIndex.exists()) {
                throw new NullPointerException("Cannot find " + fileIndex);
            } else if (fileIndex.isFile()) {
                fileList.add(fileIndex);
            } else {
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getAbsolutePath(), fileList);
                }
            }
        }
    }

    /**
     * 获取IP地址
     *
     * @return IP地址
     */
    public static String getIpAdress(){
        String serverIP = "" ;
        try{
            serverIP = InetAddress.getLocalHost().getHostAddress();
        }catch(Exception e){
            e.printStackTrace() ;
        }
        return serverIP ;
    }


    /**
     * 获取日志内容信息然后获取内容中的参数
     * @param str  日志内容
     * @return
     */
    public static String[] getLogParms(String str){
//        String str = "select * from order where createdUser = [22] and  depart = [55] and status = 'VALID' and A = [A] or B = [BB] AND jj = [PP]";
        String reg = "\\[[a-zA-Z0-9]+\\]";//定义正则表达式
//        String reg = "\\[(.*?)\\]";
        Pattern patten = Pattern.compile(reg);//编译正则表达式
        Matcher matcher = patten.matcher(str);// 指定要匹配的字符串

        List<String> matchStrs = new ArrayList<>();

        while (matcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
            matchStrs.add(matcher.group());//获取当前匹配的值
        }

        String[] newStr = null;
        String  strCom = "";
        for (int i = 0; i < matchStrs.size(); i++) {
            strCom += StrUtil.strip(matchStrs.get(i),"[","]")+",";
            newStr = strCom.split(",");

        }
        return  newStr;

    }

    public static void main(String[] args) {
        File file = new File("D:\\SGGZpro\\SGBCP0\\Client\\MM\\EC.dll   ");
        Long lastModified = file.lastModified();
        Date date = new Date(lastModified);
        System.out.println(date);
    }

    /**
     * 获取文件最后修改时间并进行转化
     * @param time
     * @return
     */
    public static String getTime(long time){
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date(time));
    }

    /**
     * Layui数据返回格式处理
     * @param list
     * @param count
     * @return
     */
    public static LayuiDataVo buildLayuiGridJsonString(List<?> list, int count) {
            LayuiDataVo grid = new LayuiDataVo();
            grid.setCode(0);
            grid.setMsg("success");
            grid.setData(list);
            grid.setCount(count);
            return grid;
    }

    /**
     * Layui数据返回格式处理
     * @return
     */
    public static LayuiDataVo messageTip(int code,String msg) {
        LayuiDataVo grid = new LayuiDataVo();
        grid.setCode(code);
        grid.setMsg(msg);
        return grid;
    }
}
