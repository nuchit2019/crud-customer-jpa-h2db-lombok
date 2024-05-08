package com.janawat.customer.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
          return new ResponseEntity<>(ApiResponse.errorResponse(HttpStatus.BAD_REQUEST,"Validation error(s):",getErrorMessages(result)),HttpStatus.BAD_REQUEST);
      }
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(ApiResponse.successResponse(HttpStatus.OK,"Added success",new Object[]{createdCustomer}), HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<?> getAllCustomer() {
        List<Customer> customers = customerService.getCustomerAll();
        return new ResponseEntity<>(ApiResponse.successResponse(HttpStatus.OK,"Retrieved all customers success",customers.toArray()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        Customer customer =customerService.getCustomerById(id);
        if(customer==null)
            return new ResponseEntity<>(ApiResponse.errorResponse(HttpStatus.NOT_FOUND,"Customer not found",null),HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ApiResponse.successResponse(HttpStatus.OK,"Retrieved customer success",new Object[]{customer} ),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer, BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>(ApiResponse.errorResponse(HttpStatus.BAD_REQUEST, "Validation error(s):", getErrorMessages(result)), HttpStatus.BAD_REQUEST);
        }
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        if (updatedCustomer == null) {
            return new ResponseEntity<>(ApiResponse.errorResponse(HttpStatus.NOT_FOUND, "Customer not found", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.successResponse(HttpStatus.OK, "Updated customer", new Object[]{updatedCustomer}), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        boolean deleted = customerService.deleteCustomer(id);
        if (!deleted) {
            return new ResponseEntity<>(ApiResponse.errorResponse(HttpStatus.NOT_FOUND, "Customer not found", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.successResponse(HttpStatus.OK, "Deleted success", null), HttpStatus.OK);

    }



    private String[] getErrorMessages(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .toArray(String[]::new);
    }

}
