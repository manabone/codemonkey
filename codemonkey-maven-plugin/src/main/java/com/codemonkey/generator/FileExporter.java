package com.codemonkey.generator;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.codemonkey.CUtils;
import com.codemonkey.Constant;
import com.codemonkey.DirFilter;
import com.codemonkey.FreeMakerGenerator;
import com.codemonkey.TemplateFilter;

public class FileExporter {

	private Log log = LogFactory.getLog(FileExporter.class);
	
	private String tempRoot;
	
	private String targetRoot;
	
	public FileExporter(String tempRoot , String targetRoot ){
		this.tempRoot = tempRoot.replace("\\", "/");
		this.targetRoot = targetRoot.replace("\\", "/");
	}
	
	public void run(String entityName , Map<String, Object> binding){
		File tempRootFile = new File(this.tempRoot);
		run(entityName , tempRootFile , binding);
	}
	
	private void run(String entityName , File tempRootFile , Map<String, Object> binding){
		try{
			CUtils.print(tempRootFile.getAbsolutePath());
			
			File[] templates = tempRootFile.listFiles(new TemplateFilter());
			
			if(templates != null && templates.length > 0 ){
				for(int i = 0 ; i < templates.length ; i++ ){
					String tempPath =templates[i].getPath();
//					CUtils.print("processing " + tempPath);
					log.info("processing " + tempPath);
					String targetName = getDestinationFile(tempPath , binding);
//					CUtils.print("writing to " + targetName);
					log.info("writing to " + targetName);
					FreeMakerGenerator.export(tempPath, binding, targetName);
				}
			}
			
			File[] subtempRoots = tempRootFile.listFiles(new DirFilter());
			if(subtempRoots != null && subtempRoots.length > 0 ){
				for(int i = 0 ; i < subtempRoots.length ; i++){
					run(entityName , subtempRoots[i] , binding );
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getDestinationFile(String tempPath , Map<String, Object> binding) {
		String targetPath = tempPath.substring(tempRoot.length());
		targetPath = targetPath.substring(0, targetPath.indexOf(Constant.TEMPLATE_SUBFIX));
		
		Pattern p = Pattern.compile("\\$\\{(\\w+)\\}");
		Matcher m = p.matcher(targetPath);
		while (m.find()){  
	      String val = m.group();
	      String key = val.substring(2 , val.length() - 1).trim();
	      String propValue = (String) binding.get(key);
	      targetPath = targetPath.replace(val , propValue);
	    }
		return targetRoot + targetPath;
	}
	
}
