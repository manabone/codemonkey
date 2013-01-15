package com.codemonkey.mojo;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import com.codemonkey.annotation.Label;
import com.codemonkey.generator.FileExporter;
import com.codemonkey.utils.ClassHelper;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal gen
 * @phase process-sources
 * @requiresDependencyResolution compile+runtime
 */
public class GenMojo extends AbstractMojo {
	
	/**
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;
	
	/**
	* Base directory of the project.
	* @parameter expression="${basedir}"
	*/
	private File baseDirectory;

	/**
	* Any Object to print out.
	* @parameter
	*   expression="${templateDir}"
	*   default-value="default"
	*/
	private String templateDir;
	
	/**
	* Any Object to print out.
	* @parameter
	*   expression="${entityName}"
	*   default-value="default"
	*/
	private String entityName;
	
	/**
	* Any Object to print out.
	* @parameter
	*   expression="${entityPackage}"
	*   default-value="com.codemonkey.domain"
	*/
	private String entityPackage;
	
	private static final String TEMPLATE_ROOT = "code-templates"; 
	
	public void execute() throws MojoExecutionException {
		
		getLog().info(baseDirectory.getAbsolutePath());
		getLog().info(templateDir);
		getLog().info(entityName);
		
		
		Map<String, Object> binding = buildBinding();
		
		String templateRoot = baseDirectory.getAbsolutePath() + File.separator + TEMPLATE_ROOT +  File.separator + templateDir ;
		FileExporter exporter = new FileExporter(templateRoot , baseDirectory.getPath());
		exporter.run(entityName, binding);
	}

	protected Map<String, Object> buildBinding() {
		Map<String, Object> binding = new HashMap<String, Object>();
		
		binding.put("entityName", StringUtils.uncapitalize(entityName));
		binding.put("EntityName", StringUtils.capitalize(entityName));
		binding.put("entityname", entityName.toLowerCase());
		
		binding.put("artifactId" , project.getArtifactId());
		binding.put("groupId" , project.getGroupId());
		
		binding.put("entityPackage" , entityPackage);
		
		Class<?> clazz = loadClass(entityName);
		List<Field> fields = ClassHelper.getAllFields(clazz);
		binding.put("fields", fields);
		binding.put("labels", buildLabels(fields));
		
		
		return binding;
	}
	
	private Map<String , String> buildLabels(List<Field> fields) {
		Map<String , String> map = new HashMap<String , String>();
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				String label = label(f);
				map.put(f.getName(), label);
			}
		}
		return map;
	}

	public String label(String fieldName) {
		
		String regex = "[A-Z]{1}[a-z]*[0-9]*";
		String label = fieldName;
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(fieldName);
		while(m.find()){
			String part = m.group();
			label = label.replace(part, " " + StringUtils.uncapitalize(part));
		}
		return label;
	}
	
	public String label(Field f) {
		Label label = null;
		if(f.getAnnotation(Label.class) != null){
			label = f.getAnnotation(Label.class);
		}else if(f.getType().getAnnotation(Label.class) != null){
			label = f.getType().getAnnotation(Label.class);
		}
		if(label != null ){
			return label.value();
		}
		return label(f.getName());
	}
	
	Class<?> loadClass(String className){
		Class<?> clazz = null;
		String classFullName = entityPackage + "." + StringUtils.capitalize(entityName);
		try{
			List<?> runtimeClasspathElements = project.getRuntimeClasspathElements();
			URL[] runtimeUrls = new URL[runtimeClasspathElements.size()];
			for (int i = 0; i < runtimeClasspathElements.size(); i++) {
			  String element = (String) runtimeClasspathElements.get(i);
			  runtimeUrls[i] = new File(element).toURI().toURL();
			}
			
			URLClassLoader newLoader = new URLClassLoader(runtimeUrls, Thread.currentThread().getContextClassLoader());
			clazz = newLoader.loadClass(classFullName);
		}catch(Exception e){
			e.printStackTrace();
		}
		return clazz;
	}

	public String unescapeUnicode(String str){
        StringBuffer sb=new StringBuffer();
        Matcher matcher = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(str);
        while(matcher.find()){
            matcher.appendReplacement(sb, (char)Integer.parseInt(matcher.group(1),16)+"");  
        }
        matcher.appendTail(sb);
        return sb.toString().replace("\\", "");
    }
	
	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	public File getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(File baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public String getTemplateDir() {
		return templateDir;
	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}
	
}
