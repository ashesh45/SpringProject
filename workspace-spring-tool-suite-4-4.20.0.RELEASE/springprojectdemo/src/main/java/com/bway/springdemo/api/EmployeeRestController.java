package com.bway.springdemo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bway.springdemo.model.Employee;
import com.bway.springdemo.model.FinanceResponse;
import com.bway.springdemo.model.Product;
import com.bway.springdemo.model.Story;
import com.bway.springdemo.repository.FinanceRepository;
import com.bway.springdemo.repository.ProductRepository;
import com.bway.springdemo.service.EmployeeService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
public class EmployeeRestController {

    @Autowired
    private EmployeeService empService;
    
    @Autowired
    private ProductRepository ProductRepo;
    
    
    @Autowired
    private FinanceRepository financeRepo;

    @PersistenceContext
    private EntityManager entityManager;


    @GetMapping("/api/emp/list")
    public List<Employee> getAll() {

        return empService.getAllEmp();
    }
    
    @GetMapping("api/emp/{id}")
    public Employee getone(@PathVariable("id") long id) {
    	
    	return empService.getEmpById(id);
    }
    
    @PostMapping("api/emp/add")
    public String add(@RequestBody Employee emp) {

    empService.addEmp(emp);
        return "added success";
}

    @DeleteMapping("api/emp/delete/{id}")
    public String delete(@PathVariable("id") int id) {

        empService.deleteEmp(id);
        return "delete success";
    }
    
    @PutMapping("api/emp/update")
    public String update(@RequestBody Employee emp) {

   empService.updateEmp(emp);
   return "update success";
}
    
    @GetMapping("api/load")
    public String loadProduct()
    {
    	RestTemplate temp = new RestTemplate();
    	Product[] prods = temp.getForObject("https://fakestoreapi.com/products", Product[].class);				
    	
         ProductRepo.saveAll(List.of(prods));
    	return "success";
    }
    

    @GetMapping("api/load-finance")
    public String loadFinance() {

        RestTemplate restTemplate = new RestTemplate();

        FinanceResponse response =
                restTemplate.getForObject(
                        "https://kchakhabar.com/api/v1/today/finance.json",
                        FinanceResponse.class
                );

        if (response != null) {
            financeRepo.save(response);
            return "success";
        }

        return "failed to load finance data";
    }
        
}