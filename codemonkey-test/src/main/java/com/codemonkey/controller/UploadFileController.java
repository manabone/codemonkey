package com.codemonkey.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.codemonkey.utils.ExtConstant;
import com.codemonkey.web.controller.AbsUploadEntityController;
import com.codemonkey.web.controller.UploadItem;

@Controller
@RequestMapping(value = "/ext/uploadFile/**")
public class UploadFileController {

	private String uploadFolderPath;
	private ServletConfig config;

	public String getUploadFolderPath() {
		return uploadFolderPath;
	}

	public void setUploadFolderPath(String uploadFolderPath) {
		this.uploadFolderPath = uploadFolderPath;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getUploadForm(Model model) {
		model.addAttribute(new UploadItem());
		return AbsUploadEntityController.UPLOAD_FILE;
	}

	@RequestMapping("create")
	@ResponseBody 
	public String create(UploadItem uploadItem, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		
		JSONObject result = new JSONObject();
		result.put(ExtConstant.SUCCESS, true);
		
		if (bindingResult.hasErrors()) {
			StringBuffer buffer = new StringBuffer();
			
			for (ObjectError error : bindingResult.getAllErrors()) {
				buffer.append(error.getCode());
				buffer.append(" - ");
				buffer.append(error.getDefaultMessage());
			}
			
			result.put(ExtConstant.SUCCESS, false);
			result.put(ExtConstant.ERROR_MSG, buffer.toString());
		}

		try {
			MultipartFile file = uploadItem.getFileData();
			String fileName = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			if (file.getSize() > 0) {
				inputStream = file.getInputStream();
				
				String uploadPath = session.getServletContext().getRealPath("") + "/upload";
				File uploadDir = new File(uploadPath);
				if(!uploadDir.exists()){
					FileUtils.forceMkdir(uploadDir);
				}
				
				fileName = uploadPath + File.separator + file.getOriginalFilename();
				outputStream = new FileOutputStream(fileName);

				int readBytes = 0;
				byte[] buffer = new byte[AbsUploadEntityController.TEN_THOUSAND];
				while ((readBytes = inputStream.read(buffer, 0, AbsUploadEntityController.TEN_THOUSAND)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
			}

		} catch (Exception e) {
			result.put(ExtConstant.SUCCESS, false);
			result.put(ExtConstant.ERROR_MSG, e.getMessage());
			e.printStackTrace();
		}
		return result.toString();
	}

	public ServletConfig getConfig() {
		return config;
	}

	public void setConfig(ServletConfig config) {
		this.config = config;
	}

}
