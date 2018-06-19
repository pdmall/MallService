package com.yang.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 * 
 * @author a55660000
 *
 */
@CrossOrigin
@Controller
@RequestMapping("app/files/")
public class FileUploadController {
	/**
	 * 文件保存路径
	 */
	private String FILEURL = "C:\\uploadFiles\\uploadFiles";
	private String FILEURLs = "C:";

	@RequestMapping(value = "upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String upload(@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
			HttpServletResponse response) throws Exception {

		String filePath = getFilePath(uploadFile.getOriginalFilename());
		String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, FILEURLs), "\\", "/");
		File newFile = new File(filePath);
		// 写文件到磁盘
		uploadFile.transferTo(newFile);
		return picUrl;
	}

	private String getFilePath(String sourceFileName) {
		String baseFolder = FILEURL + File.separator + "file";
		Date nowDate = new Date();
		// yyyy/MM/dd
		String fileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy") + File.separator
				+ new DateTime(nowDate).toString("MM") + File.separator + new DateTime(nowDate).toString("dd");
		File file = new File(fileFolder);
		if (!file.isDirectory()) {
			// 如果目录不存在，则创建目录
			file.mkdirs();
		}
		// 生成新的文件名
		String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS") + RandomUtils.nextInt(100, 9999) + "."
				+ StringUtils.substringAfterLast(sourceFileName, ".");
		return fileFolder + File.separator + fileName;
		// return fileName;

	}

}
