package com.github.platform.sf.common.util.io;

import com.github.platform.sf.common.BaseConstants;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author 张剑军
 * @Description
 * @date 2019/1/29 22:20
 **/
public class CompressUtil {

    private static Logger logger = LoggerFactory.getLogger(CompressUtil.class);


    public static void unzip(File srcZipFile, String destDirPath) {
        if(!srcZipFile.exists()){
            throw new RuntimeException("zip文件不存在");
        }
        if(!new File(destDirPath).exists()){
            new File(destDirPath).mkdirs();
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcZipFile);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                if(entry.isDirectory()){
                    String dirPath = destDirPath + BaseConstants.PATH_SPLIT + entry.getName();
                    new File(dirPath).mkdirs();
                }else{
                    File targetFile = new File(destDirPath + entry.getName());
                    if(!targetFile.getParentFile().exists()){
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    try {
                        IOUtils.copy(is, fos);
                    }catch (Exception e){
                        logger.error("", e);
                    }finally {
                        try {
                            is.close();
                        }catch (Exception e){
                            logger.error("", e);
                        }
                        try {
                            fos.close();
                        }catch (Exception e){
                            logger.error("", e);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            if(zipFile != null){
               try {
                   zipFile.close();
               }catch (Exception e){
                   logger.error("", e);
               }
            }
        }
    }

    public static void main(String[] args) {
        CompressUtil.unzip(new File("D:\\hnswak\\02 开发\\文档\\公文传输系统\\00000000000001.zip"),
                "D:\\hnswak\\02 开发\\文档\\公文传输系统\\");
    }
}
