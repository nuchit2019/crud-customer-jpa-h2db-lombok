package com.janawat.customer.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
       return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
       return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
       
        if(customerRepository.existsById(id)){
            customer.setId(id);
            return customerRepository.save(customer);
        }

        return null;
    }

    @Override
    public void deleteCustomer(Long id) {
       
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> getCustomerAll() {
         return customerRepository.findAll();
    }


}
