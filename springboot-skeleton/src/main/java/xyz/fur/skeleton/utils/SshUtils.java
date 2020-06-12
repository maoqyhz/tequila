package xyz.fur.skeleton.utils;

import com.google.common.base.Strings;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.mozilla.universalchardet.UniversalDetector;
import xyz.fur.skeleton.entity.support.CmdBuilder;
import xyz.fur.skeleton.entity.support.CmdOutput;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * ssh utils
 *
 * @author Fururur
 * @date 2020-03-20-15:20
 */
@Slf4j
public class SshUtils implements Closeable {

    private Session session;
    private ChannelSftp channelSftp;

    /**
     * 超时数,一分钟
     */
    private final static int TIMEOUT = 60_000;
    private final static int BYTE_LENGTH = 1024;

    public SshUtils(String username, String password, String hostname, Integer port) {
        try {
            JSch jSch = new JSch();
            session = jSch.getSession(username, hostname, port);
            if (null != password) {
                session.setPassword(password);
            }
            session.setTimeout(TIMEOUT);
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            session.setConfig(properties);
        } catch (Exception e) {
            log.error("init host:{},username:{},password:{} error:{}", hostname, username, password, e);
        }
    }

    /**
     * 获得服务器连接 注意：操作完成务必调用close方法回收资源
     *
     * @return
     * @see SshUtils#close()
     */
    public boolean connect() {
        try {
            if (!isConnected()) {
                session.connect();
                channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect();
                log.debug("connected to host:{},username:{}", session.getHost(), session.getUserName());
            }
            return true;
        } catch (JSchException e) {
            log.info("connection to host:{} error:{}", session.getHost(), e.toString());
            e.printStackTrace();
            return false;
        }
    }


    public CmdOutput exec(String cmd) {
        try {
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(cmd);
            InputStream in = channel.getInputStream();
            InputStream err = channel.getExtInputStream();
            log.info("ssh exec {}", cmd);
            channel.connect();
            String inStr = IOUtils.toString(in, StandardCharsets.UTF_8);
            String errStr = IOUtils.toString(err, StandardCharsets.UTF_8);
            if (!Strings.isNullOrEmpty(errStr))
                log.error(errStr);
            return new CmdOutput(inStr, errStr);
        } catch (IOException | JSchException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从sftp服务器下载指定文件到本地指定目录
     *
     * @param remoteFile 文件的绝对路径+fileName
     * @param localPath  本地临时文件路径
     * @return
     */
    public boolean get(String remoteFile, String localPath) {
        if (isConnected()) {
            try {
                channelSftp.get(remoteFile, localPath);
                return true;
            } catch (SftpException e) {
                log.error("get remoteFile:{},localPath:{}, error:{}", remoteFile, localPath, e);
            }
        }
        return false;
    }

    /**
     * 读取sftp上指定文件数据
     *
     * @param remoteFile
     * @return
     */
    public byte[] getFileByte(String remoteFile) {
        byte[] fileData;
        try (InputStream inputStream = channelSftp.get(remoteFile)) {
            byte[] ss = new byte[BYTE_LENGTH];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int rc = 0;
            while ((rc = inputStream.read(ss, 0, BYTE_LENGTH)) > 0) {
                byteArrayOutputStream.write(ss, 0, rc);
            }
            fileData = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("getFileData remoteFile:{},error:{}", remoteFile, e);
            fileData = null;
        }
        return fileData;
    }

    /**
     * 读取sftp上指定（文本）文件数据,并按行返回数据集合
     *
     * @param remoteFile
     * @param charsetName
     * @return
     */
    public List<String> getFileLines(String remoteFile, String charsetName) {
        List<String> fileData;
        try {
            InputStream inputStream = channelSftp.get(remoteFile);
            InputStream copy = IOUtils.toBufferedInputStream(inputStream);
            UniversalDetector detector = new UniversalDetector(null);
            byte[] buf = new byte[4096];
            int nread;
            while ((nread = copy.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            detector.reset();
            if (encoding == null)
                encoding = "UTF-8";
            InputStreamReader inputStreamReader = new InputStreamReader(channelSftp.get(remoteFile), encoding);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            fileData = new ArrayList<>();
            while ((str = bufferedReader.readLine()) != null) {
                fileData.add(str);
            }
        } catch (Exception e) {
            log.error("getFileData remoteFile:{},error:{}", remoteFile, e);
            fileData = null;
        }
        return fileData;
    }


    public static String codeString(InputStream inputStream) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(inputStream);
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        //其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            case 0x5c75:
                code = "ANSI|ASCII";
                break;
            default:
                code = "GBK";
        }
        log.info("file encoding: {}", code);
        return code;
    }

    /**
     * 上传本地文件到sftp服务器指定目录
     *
     * @param localFile
     * @param remoteFile
     * @return
     */
    public boolean put(String localFile, String remoteFile) {
        if (isConnected()) {
            try {
                String root = remoteFile.substring(0, remoteFile.lastIndexOf("/"));
                this.exec(CmdBuilder.mkdir("-p", root));
                channelSftp.put(localFile, remoteFile);
                return true;
            } catch (SftpException e) {
                log.error("put localPath:{}, remoteFile:{},error:{}", localFile, remoteFile, e.toString());
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 从sftp服务器删除指定文件
     *
     * @param remoteFile
     * @return
     */
    public boolean delFile(String remoteFile) {
        if (isConnected()) {
            try {
                channelSftp.rm(remoteFile);
                return true;
            } catch (SftpException e) {
                log.error("delFile remoteFile:{} , error:{}", remoteFile, e);
            }
        }
        return false;
    }

    /**
     * 列出指定目录下文件列表
     *
     * @param remotePath
     * @return
     */
    public Vector<ChannelSftp.LsEntry> ls(String remotePath) {
        Vector<ChannelSftp.LsEntry> vector = null;
        if (isConnected()) {
            try {
                vector = channelSftp.ls(remotePath);
            } catch (SftpException e) {
                log.error("ls remote: {} , SftpException: {}", remotePath, e.toString());
                e.printStackTrace();
            }
        }
        return vector;
    }

    /**
     * 列出指定目录下文件列表
     *
     * @param remotePath
     * @param filenamePattern
     * @return 排除./和../等目录和链接,并且排除文件名格式不符合的文件
     */
    public List<ChannelSftp.LsEntry> lsFiles(String remotePath, Pattern filenamePattern) {
        List<ChannelSftp.LsEntry> lsEntryList = null;
        if (isConnected()) {
            try {
                Vector<ChannelSftp.LsEntry> vector = channelSftp.ls(remotePath);
                if (vector != null) {
                    lsEntryList = vector.stream().filter(x -> {
                        boolean match = true;
                        if (filenamePattern != null) {
                            Matcher mtc = filenamePattern.matcher(x.getFilename());
                            match = mtc.find();
                        }
                        if (match && !x.getAttrs().isDir() && !x.getAttrs().isLink()) {
                            return true;
                        }
                        return false;
                    }).collect(Collectors.toList());
                }
            } catch (SftpException e) {
                lsEntryList = null;
                log.error("lsFiles remotePath:{} , error:{}", remotePath, e);
            }
        }
        return lsEntryList;
    }

    /**
     * 判断链接是否还保持
     *
     * @return
     */
    public boolean isConnected() {
        if (session.isConnected() && channelSftp.isConnected()) {
            return true;
        }
        log.debug("server:{} is not connected", session.getHost());
        return false;
    }

    /**
     * 关闭连接资源
     */
    @Override
    public void close() throws IOException {
        if (channelSftp != null && channelSftp.isConnected()) {
            channelSftp.quit();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
        log.debug("session and channel is closed");
    }

    public static void main(String[] args) throws IOException {
    }

}
