package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PictureService;
import com.taotao.utils.FtpUtil;
import com.taotao.utils.IDUtils;

@Service
public class PictureServiceImpl implements PictureService {

	// 从配置文件中读取变量的值
	@Value("${FTP_ADDRESS}")
	private String host;
	@Value("${FTP_PORT}")
	private Integer port;
	@Value("${FTP_USERNAME}")
	private String username;
	@Value("${FTP_PASSWORD}")
	private String password;
	@Value("${FTP_BASE_PATH}")
	private String basePath;
	@Value("${IMAGE_BASE_URL}")
	private String image_bath_url;

	@Override
	public Map uploadFile(MultipartFile uploadFile) {
		Map resultMap = new HashMap();
		try {
			String oldName = uploadFile.getOriginalFilename();
			String newName = IDUtils.genImageName() + oldName.substring(oldName.lastIndexOf("."));

			String imagePath = new DateTime().toString("yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, imagePath, newName,
					uploadFile.getInputStream());
			// 上传失败
			if (!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			// 上传成功
			resultMap.put("error", 0);
			resultMap.put("url", image_bath_url + "/" + imagePath + "/" + newName); // 图片url/home/ftpuser/www/images/yyyy/MM/dd...
			System.out.println(image_bath_url + "/" + imagePath + "/" + newName);
			return resultMap; 
		} catch (Exception e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

}
