package com.peazone.core.base.utils;

import io.minio.MinioClient;
import io.minio.errors.MinioException;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OOSClient {

    private static final Logger logger = LoggerFactory.getLogger(OOSClient.class);

    private volatile static MinioClient minioClient;
    private volatile static String endpoint = "http://192.168.13.201:9000";
    private volatile static String accessKey = "PE25WZVM32PAMT1NH2K1";
    private volatile static String secretKey = "vRYMXrYGNrRcEggRw3fYAzU+dmyws6L/G59bgMQg";

    private OOSClient() {
        System.out.println("Singleton has loaded");
    }

    private static MinioClient getInstance() {
        if (minioClient == null) {
            synchronized (OOSClient.class) {
                if (minioClient == null) {
                    endpoint = PropertyUtils.getStringValue("MINIO_SERVER", "ENDPOINT");
                    accessKey = PropertyUtils.getStringValue("MINIO_SERVER", "ACCESS_KEY");
                    secretKey = PropertyUtils.getStringValue("MINIO_SERVER", "SECRET_KEY");
                    try {
                        minioClient = new MinioClient(endpoint, accessKey, secretKey);
                    } catch (MinioException e) {
                        System.out.println("Error occurred: " + e);
                        logger.info("Minio client error occurred:", e);
                    }
                }
            }
        }
        return minioClient;
    }

    /**
     * 创建 Bucket
     * 
     * @param bucketName
     * @return
     */
    public static boolean makeBucket(String bucketName) {
        boolean result;
        minioClient = OOSClient.getInstance();
        try {
            boolean isExist = minioClient.bucketExists(bucketName);
            if (isExist) {
                logger.info("Bucket already exists, BucketName: {}", bucketName);
                result = false;
            } else {
                minioClient.makeBucket(bucketName);
                result = true;
            }
        } catch (Exception e) {
            logger.info("Minio client error occurred:", e);
            result = false;
        }

        return result;
    }

    /**
     * 移除 Bucket
     * 
     * @param bucketName
     * @return
     */
    public static boolean removeBucket(String bucketName) {
        boolean result;
        minioClient = OOSClient.getInstance();
        try {
            boolean isExist = minioClient.bucketExists(bucketName);
            if (isExist) {
                minioClient.removeBucket(bucketName);
                result = true;
            } else {
                logger.info("Bucket does not exist, BucketName: {}", bucketName);
                result = false;
            }
        } catch (Exception e) {
            logger.info("Minio client error occurred:", e);
            result = false;
        }

        return result;
    }

    /**
     * 上传文件
     * 
     * @param bucketName
     *            BucketName
     * @param fileName
     *            存储文件名
     * @param filePath
     *            上传文件绝对路径
     * @return
     */
    public static boolean upload(String bucketName, String fileName, String filePath) {
        boolean uploadResult;
        minioClient = OOSClient.getInstance();
        try {
            boolean isExist = minioClient.bucketExists(bucketName);
            if (isExist) {
                minioClient.putObject(bucketName, fileName, filePath);
                uploadResult = true;
            } else {
                logger.info("Bucket not exists, BucketName: {}", bucketName);
                uploadResult = false;
            }
        } catch (Exception e) {
            logger.info("Minio client error occurred:", e);
            uploadResult = false;
        }
        return uploadResult;
    }

    /**
     * 上传文件流
     * 
     * @param bucketName
     *            BucketName
     * @param fileName
     *            存储文件名
     * @param inputStream
     *            文件流
     * @return
     */
    public static boolean upload(String bucketName, String fileName, InputStream inputStream) {
        boolean uploadResult;
        minioClient = OOSClient.getInstance();
        try {
            boolean isExist = minioClient.bucketExists(bucketName);
            if (isExist) {
                minioClient.putObject(bucketName, fileName, inputStream, "application/octet-stream");
                uploadResult = true;
            } else {
                logger.info("Bucket not exists, BucketName: {}", bucketName);
                uploadResult = false;
            }
        } catch (Exception e) {
            logger.info("Minio client error occurred:", e);
            uploadResult = false;
        }
        return uploadResult;
    }

    /**
     * 下载文件
     * 
     * @param bucketName
     *            BucketName
     * @param fileName
     *            存储文件名
     * @return
     */
    public static InputStream download(String bucketName, String fileName) {
        minioClient = OOSClient.getInstance();
        try {
            InputStream stream = minioClient.getObject(bucketName, fileName);
            return stream;
        } catch (Exception e) {
            logger.info("Minio client error occurred:", e);
        }
        return null;
    }

    /**
     * 移除文件
     * 
     * @param bucketName
     *            BucketName
     * @param fileName
     *            存储文件名
     * @return
     */
    public static boolean remove(String bucketName, String fileName) {
        boolean result;
        minioClient = OOSClient.getInstance();
        try {
            minioClient.removeObject(bucketName, fileName);
            result = true;
        } catch (Exception e) {
            logger.info("Minio remove object error occurred:", e);
            result = false;
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            //OOSClient.makeBucket("test");
            //OOSClient.upload("cdr-upload-file", "/txt/20180913/aa.txt", "D:\\DevelopCode\\BSS_DOC\\read.txt");
            OOSClient.remove("test", "/txt/20180913/aa.txt");
            /*OOSClient.remove("test", "/txt/20180913/aa.txt");
            OOSClient.removeBucket("test");
            InputStream inStream = OOSClient.download("test", "aa.txt");
            FileOutputStream fs = new FileOutputStream(new File("D:\\aa.txt"));
            byte[] buffer = new byte[1204];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                len += len;
                fs.write(buffer, 0, len);
            }*/
        } catch (Exception e) {
        }
    }
}
