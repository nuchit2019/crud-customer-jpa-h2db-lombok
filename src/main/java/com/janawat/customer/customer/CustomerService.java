package com.janawat.customer.customer;

public interface CustomerService {

    Customer createCustomer(Customer customer);
    Customer getCustomerById(Long id);
    Customer updateCustomer(Long id,Customer customer);
    void deleteCustomer(Long id);

}
