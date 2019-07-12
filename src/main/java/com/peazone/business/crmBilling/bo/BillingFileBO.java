package com.peazone.business.crmBilling.bo;

import java.io.InputStream;

import com.peazone.core.base.ftp.FTPUtils;
import com.peazone.core.base.utils.OOSClient;

public class BillingFileBO {

    private static final String bucketName = "cdr-upload-file";

    public static String uploadFile(String fileName, InputStream inputStream) {
        OOSClient.upload(BillingFileBO.bucketName, fileName, inputStream);
        return fileName;
    }

    /**
     * 从FTP下载文件
     * 
     * @param filePath
     *            文件全路径
     * @return
     */
    public static byte[] downloadFtpFile(String filePath) {
        return FTPUtils.download(filePath);
    }

    /**
     * 从OOS下载文件
     * 
     * @param filePath
     *            文件路径
     * @return
     */
    public static InputStream download(String filePath) {
        return OOSClient.download(BillingFileBO.bucketName, filePath);
    }

}
