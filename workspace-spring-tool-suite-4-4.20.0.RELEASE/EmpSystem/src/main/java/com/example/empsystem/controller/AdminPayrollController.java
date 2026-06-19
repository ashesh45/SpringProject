package com.example.empsystem.controller;

import java.awt.Color;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empsystem.dto.PayrollDto;
import com.example.empsystem.dto.request.CreatePayrollRequest;
import com.example.empsystem.service.PayrollService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/payroll")
public class AdminPayrollController {

    @Autowired
    private PayrollService payrollService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPayroll(@RequestBody CreatePayrollRequest request) {
        PayrollDto saved = payrollService.createPayroll(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PayrollDto>> getAllPayrolls() {
        return ResponseEntity.ok(payrollService.getAllPayrolls());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/export/pdf")
    public void exportPdf(HttpServletResponse response) throws Exception {
        List<PayrollDto> list = payrollService.getAllPayrolls();

        response.addHeader("Content-Disposition", "attachment;filename=payroll.pdf");
        response.setContentType("application/pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        HeaderFooter header = new HeaderFooter(new Phrase("PAYROLL PDF VIEW"), false);
        header.setAlignment(Element.ALIGN_CENTER);
        document.setHeader(header);

        HeaderFooter footer = new HeaderFooter(new Phrase(new Date() + " (C) bway, Page # "), true);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.setFooter(footer);

        document.open();

        Font titleFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLD, Color.RED);
        Paragraph title = new Paragraph("PAYROLL DATA", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(20.0f);
        title.setSpacingAfter(25.0f);
        document.add(title);

        Font tableHead = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLUE);
        PdfPTable table = new PdfPTable(9);
        table.addCell(new Phrase("ID", tableHead));
        table.addCell(new Phrase("EMPLOYEE NAME", tableHead));
        table.addCell(new Phrase("BASIC SALARY", tableHead));
        table.addCell(new Phrase("OVERTIMEHOURS", tableHead));
        table.addCell(new Phrase("OVERTIMERATE", tableHead));
        table.addCell(new Phrase("BONUS", tableHead));
        table.addCell(new Phrase("DEDUCTION", tableHead));
        table.addCell(new Phrase("NETSALARY", tableHead));
        table.addCell(new Phrase("SALARYMONTH", tableHead));

        for (PayrollDto p : list) {
            table.addCell(String.valueOf(p.getId()));
            table.addCell(p.getEmployeeName() != null ? p.getEmployeeName() : "");
            table.addCell(String.valueOf(p.getBasicSalary()));
            table.addCell(String.valueOf(p.getOvertimeHours()));
            table.addCell(String.valueOf(p.getOvertimeRate()));
            table.addCell(String.valueOf(p.getBonus()));
            table.addCell(String.valueOf(p.getDeduction()));
            table.addCell(String.valueOf(p.getNetSalary()));
            table.addCell(String.valueOf(p.getSalaryMonth()));
        }

        document.add(table);
        document.close();
    }
}
