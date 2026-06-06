package com.example.empsystem.utils;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.example.empsystem.model.Payroll;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AllPayrollpdfView extends AbstractPdfView {

	@Override
	protected void buildPdfMetadata(
			Map<String, Object> model, 
			Document document, HttpServletRequest request)
	{
		HeaderFooter header = new HeaderFooter(new Phrase("PAYROLL PDF VIEW"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);
		
		HeaderFooter footer = new HeaderFooter(new Phrase(new Date()+" (C) bway, Page # "), true);
		footer.setAlignment(Element.ALIGN_CENTER);
		document.setFooter(footer);
	}

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception {
		
		//download PDF with a given filename
		response.addHeader("Content-Disposition", "attachment;filename=payroll.pdf");

		//read data from controller
		@SuppressWarnings("unchecked")
		List<Payroll> list = (List<Payroll>) model.get("list");
		
		//create element
		//Font (Family, Size, Style, Color)
		Font titleFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLD, Color.RED);
		Paragraph title = new Paragraph("DEPARTMENT DATA",titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(20.0f);
		title.setSpacingAfter(25.0f);
		//add to document
		document.add(title);
		
		Font tableHead = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLUE);
		PdfPTable table = new PdfPTable(9);// no.of columns
		table.addCell(new Phrase("ID",tableHead));
		table.addCell(new Phrase("EMPLOYEE NAME",tableHead));
		table.addCell(new Phrase("BASIC SALARY",tableHead));
		table.addCell(new Phrase("OVERTIMEHOURS",tableHead));
		table.addCell(new Phrase("OVERTIMERATE",tableHead));
		table.addCell(new Phrase("BONUS",tableHead));
		table.addCell(new Phrase("DEDUCTION",tableHead));
		table.addCell(new Phrase("NETSALARY",tableHead));
		table.addCell(new Phrase("SALARYMONTH",tableHead));
		
		for(Payroll p : list ) {
			table.addCell(String.valueOf(p.getId()));
			 table.addCell(p.getEmployee().getFname());
			    table.addCell(String.valueOf(p.getBasicSalary()));
			    table.addCell(String.valueOf(p.getOvertimeHours()));
			    table.addCell(String.valueOf(p.getOvertimeRate()));
			    table.addCell(String.valueOf(p.getBonus()));
			    table.addCell(String.valueOf(p.getDeduction()));
			    table.addCell(String.valueOf(p.getNetSalary()));
			    table.addCell(String.valueOf(p.getSalaryMonth()));
		}
		//add to document
		document.add(table);
	}
}
