package com.codemonkey.test.report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.report.ReportService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
	"classpath*:spring/applicationContext-datasource.xml", 
	"classpath*:spring/applicationContext-dao.xml",
	"classpath*:spring/applicationContext-globle.xml"
})
@Transactional
public class ReportServiceTest {

	@Autowired ReportService reportService;
	
	@Test
	public void testCreatePdf(){
//		reportService.pdf("userReport.pdf", "reports/user_report.jasper");
//		File f = new File("userReport.pdf");
//		assertTrue(f.exists());
	}
	
	@Test
	public void testCreateXml(){
//		reportService.xml("userReport.xml", "reports/user_report.jasper");
//		File f = new File("userReport.xml");
//		assertTrue(f.exists());
	}
}
