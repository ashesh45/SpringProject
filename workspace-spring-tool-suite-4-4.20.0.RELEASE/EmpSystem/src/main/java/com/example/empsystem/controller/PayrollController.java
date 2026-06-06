package com.example.empsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.empsystem.model.Employee;
import com.example.empsystem.model.Payroll;
import com.example.empsystem.repository.PayrollRepository;
import com.example.empsystem.service.EmployeeService;
import com.example.empsystem.service.PayrollService;
import com.example.empsystem.utils.AllPayrollpdfView;

import jakarta.servlet.http.HttpSession;

@Controller
public class PayrollController {
	
	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private  PayrollService payrollService;
    
	@Autowired
	private PayrollRepository payrollrepo;

	
    @GetMapping("/payroll")
    public String payrollForm(Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "login";
        }

        model.addAttribute("payroll", new Payroll());
        model.addAttribute("Emplist", empService.getAllEmp());
        return "payroll-form";
    }

    
    
    @PostMapping("/payroll")
    public String savePayroll(@ModelAttribute Payroll payroll,
                              @RequestParam("employeeId") Long employeeId,
                              Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "login";
        }

        Employee employee = empService.getEmpById(employeeId);
        payroll.setEmployee(employee);
        payrollService.calculateAndSave(payroll);

        model.addAttribute("msg", "Payroll processed successfully");
        model.addAttribute("payroll", new Payroll());
        model.addAttribute("Emplist", empService.getAllEmp());
        return "payroll-form";
    }

    // list payroll
    @GetMapping("/payroll/list")
    public String payrollList(Model model) {
        model.addAttribute("list", payrollService.getAll());
        return "payroll-list";
    }
    
	  @GetMapping("/mypayroll")
	  public String myPayroll(HttpSession session, Model model) {

	      Employee emp = (Employee) session.getAttribute("loggedInUser");

	      if (emp == null) {
	          return "login";
	      }

	      List<Payroll> payrolls =
	              payrollrepo.findByEmployeeId(emp.getId());

	      model.addAttribute("payrolls", payrolls);

	      return "mypayroll";
	  }
	  
	  @GetMapping("/pdf")
	  public ModelAndView getpdf(){
			ModelAndView mv = new ModelAndView();
	    	mv.addObject("list", payrollService.getAll());
	    	mv.setView(new AllPayrollpdfView());
	    	
	    	return mv;
		 
	  }
    
}
