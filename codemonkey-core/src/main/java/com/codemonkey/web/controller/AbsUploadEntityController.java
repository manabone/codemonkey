package com.codemonkey.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.codemonkey.domain.AbsUploadEntity;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.ResourceUtils;

@Controller
public abstract class AbsUploadEntityController<T extends AbsUploadEntity> extends AbsExtController<T> {

	@Autowired private ResourceUtils resourceUtils;
	
	/**
	  * 方法描述：上传文件
	  * @param: UploadItem uploadItem 文件
	  * @param: HttpSession session
	  * @return: String
	  */
	@RequestMapping("upload")
	@ResponseBody 
	public String upload(UploadItem uploadItem, HttpSession session){
		return handleUpload(uploadItem , session);
	}

	private String handleUpload(UploadItem uploadItem , HttpSession session) {
		JSONObject result = new JSONObject();
		result.put(ExtConstant.SUCCESS, true);
		
		try {
			
			T t = service().createEntity();
			
			saveFile(uploadItem , t);

			service().save(t);
			
			result.put("id", t.getId());

		} catch (Exception e) {
			result.put(ExtConstant.SUCCESS, false);
			result.put(ExtConstant.ERROR_MSG, e.getMessage());
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	public String saveFile(UploadItem uploadItem , T t) throws IOException, FileNotFoundException {
		String filePath = null;
		MultipartFile file = uploadItem.getFileData();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		if (file.getSize() > 0) {
			inputStream = file.getInputStream();

			String uploadPath = resourceUtils.getAppSetting("uploadDir");
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				FileUtils.forceMkdir(uploadDir);
			}

			filePath = uploadPath + File.separator + (new Date()).getTime()
					+ "_" + file.getOriginalFilename();
			outputStream = new FileOutputStream(filePath);

			int readBytes = 0;
			byte[] buffer = new byte[UploadFileController.TEN_THOUSAND];
			while ((readBytes = inputStream.read(buffer, 0,
					UploadFileController.TEN_THOUSAND)) != -1) {
				outputStream.write(buffer, 0, readBytes);
			}
			outputStream.close();
			inputStream.close();
			String fileName = uploadItem.getFilename();
			t.setFileName(fileName);
			t.setFilePath(filePath);
			if(fileName.lastIndexOf('.') > 0){
				t.setFileType(fileName.substring(fileName.lastIndexOf('.')));
			}
			t.setFileSize(file.getSize());
		}
		return filePath;
	}
	
	/**
	  * 方法描述：下载文件
	  * @param: BsUploadInfo bsUploadInfo 附件Id
	  * @param: HttpServletResponse response
	  * @return: String
	  */
	@RequestMapping("download")
	public void download(@RequestParam Long id , HttpServletResponse response){
		
		File file = getFile(id);
		
		if (file.isFile()) {
			try {
			  response.setCharacterEncoding("utf-8");    
			  response.setContentType("application/octet-stream");    
			  response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(),"utf-8"));

			  InputStream is = new FileInputStream(file);
		      ServletOutputStream out = response.getOutputStream();
		      IOUtils.copy(is, out);
		      is.close();
	          out.close();
	          out.flush();
		    } catch (Exception ex) {
		      throw new RuntimeException("IOError writing file to output stream");
		    }
		}
	}
	
	public File getFile(Long id){
		T t = service().get(id);
		String path = t.getFilePath();
		File file = new File(path);
		return file;
	}
	
	/**
	  * 方法描述：删除文件
	  * @param: List<Long> uploadIds 附件Id
	  * @return: String
	  */
	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestParam(required = false) List<Long> uploadIds){
		JSONObject result = new JSONObject();
		result.put(ExtConstant.SUCCESS, true);
		
		if(CollectionUtils.isNotEmpty(uploadIds)){
			for(Long id : uploadIds){
				File f = getFile(id);
				
				if (f.exists()) {
					f.delete();
				}
			}
			service().doDelete(uploadIds);
		}
		
		return result.toString();
	}

}
