package com.codemonkey.report;

public interface ReportService {

	void pdf(String destinationFile, String templatePath);

	void xml(String destinationFile, String templatePath);

}