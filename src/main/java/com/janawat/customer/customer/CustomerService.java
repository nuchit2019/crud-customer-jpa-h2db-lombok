package com.janawat.customer.customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);
    Customer getCustomerById(Long id);
    List<Customer> getCustomerAll();
    Customer updateCustomer(Long id,Customer customer);
    void deleteCustomer(Long id);

}
