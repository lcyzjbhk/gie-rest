package cn.whjg.taotao.common.utils;

import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SFtpUtils {
	static Session session = null;
	static Channel channel = null;

	//建立连接
	public static ChannelSftp getChannel(String hostname, int port, String username, String password, int timeout)
			throws JSchException {
		JSch jsch = new JSch(); // 创建JSch对象
		session = jsch.getSession(username, hostname, port); // 根据用户名，主机ip，端口获取一个Session对象
		session.setPassword(password); // 设置密码
		
		//设置不进行秘钥检查
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		
		session.setTimeout(timeout); // 设置timeout时间
		session.connect(); // 通过Session建立链接
		channel = session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		return (ChannelSftp) channel;
	}

	//关闭连接
	public static void closeChannel() throws Exception {
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}
	
	//上传文件
	public static void uploadFile(String hostname, int port, String username, String password, int timeout,
			InputStream input, String filePath, String fileName) throws Exception {
		ChannelSftp con = getChannel(hostname, port, username, password, timeout);
		createDir(filePath, con);
		con.put(input, filePath+fileName);
		closeChannel();
	}
	//创建文件夹
	public static void createDir(String createpath, ChannelSftp sftp) {
		try {
			if (isDirExist(createpath, sftp)) {
				sftp.cd(createpath);
				return;
			}
			String pathArry[] = createpath.split("/");
			StringBuffer filePath = new StringBuffer("/");
			for (String path : pathArry) {
				if (path.equals("")) {
					continue;
				}
				filePath.append(path + "/");
				if (isDirExist(filePath.toString(), sftp)) {
					sftp.cd(filePath.toString());
				} else {
					// 建立目录
					sftp.mkdir(filePath.toString());
					// 进入并设置为当前目录
					sftp.cd(filePath.toString());
				}
			}
				sftp.cd(createpath);
		} catch (SftpException e) {
			
		}
	}
	//判断文件夹是否存在
	public static boolean isDirExist(String directory, ChannelSftp sftp) {
		boolean isDirExistFlag = false;
		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = true;
			return sftpATTRS.isDir();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExistFlag = false;
			}
		}
		return isDirExistFlag;
	}

}