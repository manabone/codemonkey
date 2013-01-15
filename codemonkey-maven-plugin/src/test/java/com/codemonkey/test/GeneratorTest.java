package com.codemonkey.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.junit.Test;

public class GeneratorTest extends AbstractMojoTestCase {

	MavenProject project;
	
	
	Map<String , Object> binding = new HashMap<String , Object>();
	
	@Test
	public void test(){
		
	}
	
	@Override
    protected void setUp() throws Exception {
		
		super.setUp();
		
		project = new MavenProject();
		project.setArtifactId("test-project");
		project.setGroupId("test.project");
		project.getBasedir();
    }
	
	@Override
    protected void tearDown() throws Exception {
		
	}
	
//	private boolean checkExists(String path) {
//		return new File(path).exists();
//	}
//
//	private void deleteDirectory(String path) {
//		File f = new File(path);
//		if(f.exists()){
//			f.delete();
//		}
//	}
}
