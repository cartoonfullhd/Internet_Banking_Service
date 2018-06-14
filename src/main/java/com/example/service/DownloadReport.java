package com.example.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Component
public class DownloadReport
{
	Connection connection = null;
	String dbUrl = "jdbc:h2:file:./Internet-Banking-h2";
	String dbUname = "sa";
	String dbPwd = "";
	
	public byte[] generateReport(String accountID)
	{
		byte[] bytes = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("accountID", accountID);
			
			connection = DriverManager.getConnection(dbUrl, dbUname, dbPwd);
			
			// Get Compiled Report File
			Resource resource = new ClassPathResource("com/jasper/templates/TxnReport.jasper");
			File reportFile = resource.getFile();
			
			//File reportFile = new File("C:/Users/USER/JaspersoftWorkspace/MyReports/TxnReport.jasper");

			 // 1. JRPdfExporter 
			 /*JRPdfExporter exporter = new JRPdfExporter();
			 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			 exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportName + ".pdf");
			 exporter.exportReport();
			 bytes = baos.toByteArray();*/

			
			 // 2. JasperExportManager
			 FileInputStream inputStream = new FileInputStream(reportFile); 
			 JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, connection);
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
			 JasperExportManager.exportReportToPdfStream(jasperPrint, baos); 
			 // Export to PDF.
		     JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\USER\\Desktop\\"+"TxnReport.pdf");
			 bytes = baos.toByteArray();
			 
			// 3. JasperRunManager
			 /*FileInputStream inputStream = new FileInputStream(reportFile); 
			 bytes = JasperRunManager.runReportToPdf(inputStream, params, connection);*/
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return bytes;
	}
}
