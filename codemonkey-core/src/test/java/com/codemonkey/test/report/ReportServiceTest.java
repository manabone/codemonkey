package com.codemonkey.test.report;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.report.ReportService;
import com.codemonkey.test.service.AbsServiceTest;

public class ReportServiceTest extends AbsServiceTest{

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
