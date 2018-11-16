package cn.ctyun.core.base.ftp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ctyun.core.base.utils.PropertyUtils;

public class FTPUtils {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtils.class);

    private volatile static FTPClient ftpClient;

    private volatile static String server = "192.168.13.201";
    private volatile static String username = "ctyun_ftp";
    private volatile static String password = "ctyun_ftp";

    private FTPUtils() {

    }

    private static FTPClient getInstance() {
        if (ftpClient == null || !ftpClient.isConnected()) {
            synchronized (FTPUtils.class) {
                if (ftpClient == null || !ftpClient.isConnected()) {
                    server = PropertyUtils.getStringValue("FTP_SERVER", "SERVER_IP");
                    username = PropertyUtils.getStringValue("FTP_SERVER", "USERNAME");
                    password = PropertyUtils.getStringValue("FTP_SERVER", "PASSWORD");
                    try {
                        ftpClient = new FTPClient();
                        ftpClient.setControlEncoding("UTF-8");
                        ftpClient.connect(server);
                        ftpClient.login(username, password);
                    } catch (IOException e) {
                        logger.error("FTP client error occurred:", e);
                    }
                }
            }
        }
        return ftpClient;
    }

    public static boolean completePendingCommand() {
        if (ftpClient.isConnected()) {
            try {
                return ftpClient.completePendingCommand();
            } catch (IOException e) {
                logger.error("FTP completePendingCommand error occurred:", e);
            }
        }
        return false;
    }

    /**
     * 登出 FTP并断开连接
     */
    public static void logout() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                disconnect();
            } catch (IOException e) {
                logger.error("FTP logout error occurred:", e);
            }
        }
    }

    /**
     * 断开FTP连接
     */
    public static void disconnect() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error("FTP disconnect error occurred:", e);
            }
        }
    }

    /**
     * 下载文件
     * 
     * @param filePath
     *            文件路径
     * @return
     */
    public static byte[] download(String filePath) {
        try {
            ftpClient = FTPUtils.getInstance();
            InputStream inputStream = ftpClient.retrieveFileStream(filePath);
            byte[] fileByte = IOUtils.toByteArray(inputStream);
            inputStream.close();
            ftpClient.completePendingCommand();
            return fileByte;
        } catch (IOException e) {
            logger.error("FTP download error occurred:", e);
        }
        return null;
    }

    public static void main(String[] args) {
        FTPUtils.getInstance();
    }

}
