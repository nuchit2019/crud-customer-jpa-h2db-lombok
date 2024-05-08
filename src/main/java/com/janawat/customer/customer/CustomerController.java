package com.janawat.customer.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid  @RequestBody Customer customer, BindingResult result) {
      if(result.hasErrors()){
          return new ResponseEntity<>(ApiResponse.formatValidationErrorResponse(result), HttpStatus.BAD_REQUEST);
      }
      Customer createdCustomer = customerService.createCustomer(customer);

        return new ResponseEntity<>(ApiResponse.formatSuccessResponse(new Object[]{createdCustomer}),HttpStatus.CREATED);
    }



    @GetMapping()
    public List<Customer> getAllCustomer() {
        return customerService.getCustomerAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer, BindingResult result) {

        if(result.hasErrors()){
            return  new ResponseEntity<>(ApiResponse.formatValidationErrorResponse(result), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(customerService.updateCustomer(id, customer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);
    }


}
