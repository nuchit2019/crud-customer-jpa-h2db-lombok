package com.janawat.customer.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CustomerServiceImplTest {

    // Mocked customer repository
    @MockBean
    private CustomerRepository customerRepository;

    // Instance of the service to be tested
    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    // Test case for creating a customer
    @Test
    void testCreateCustomer() {
        // Arrange
        // Create a sample customer
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1L);
        customer.setName("Name");

        // Mock the behavior of the customer repository save method
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);

        // Create another instance of the customer
        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1L);
        customer2.setName("Name");

        // Act
        Customer actualCreateCustomerResult = customerServiceImpl.createCustomer(customer2);

        // Assert
        // Invoke the method under test
        verify(customerRepository).save(isA(Customer.class));

        // Assert that the returned customer is the same as the one saved
        assertSame(customer, actualCreateCustomerResult);
    }

    // Test case for retrieving a customer by ID
    @Test
    void testGetCustomerById() {
        // Arrange
        // Create a sample customer
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1L);
        customer.setName("Name");

        // Mock the behavior of the customer repository findById method
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        // Invoke the method under test
        Customer actualCustomerById = customerServiceImpl.getCustomerById(1L);

        // Assert
        // Verify that findById was called with the correct ID
        verify(customerRepository).findById(eq(1L));
        // Assert that the returned customer is the same as the one retrieved
        assertSame(customer, actualCustomerById);
    }

    @Test
    void testUpdateCustomer() {
        // Arrange
        // Create a sample customer
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1L);
        customer.setName("Name");
        // Mock the behavior of the customer repository save method to return the customer
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);
        // Mock the behavior of the customer repository existsById method to return true
        when(customerRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Create another instance of the customer
        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1L);
        customer2.setName("Name");

        // Act
        // Invoke the method under test
        Customer actualUpdateCustomerResult = customerServiceImpl.updateCustomer(1L, customer2);

        // Assert
        // Verify that existsById was called with the correct ID
        verify(customerRepository).existsById(eq(1L));
        // Verify that save was called with a customer object
        verify(customerRepository).save(isA(Customer.class));
        // Assert that the ID of the updated customer is correct
        assertEquals(1L, customer2.getId().longValue());
        // Assert that the returned customer is the same as the one saved
        assertSame(customer, actualUpdateCustomerResult);
    }


    @Test
    void testUpdateCustomer2() {
        // Arrange
        // Mock the behavior of the customer repository existsById method to return false
        when(customerRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        // Create a sample customer
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1L);
        customer.setName("Name");

        // Act
        // Invoke the method under test
        Customer actualUpdateCustomerResult = customerServiceImpl.updateCustomer(1L, customer);

        // Assert
        // Verify that existsById was called with the correct ID
        verify(customerRepository).existsById(eq(1L));
        // Assert that the actual updated customer is null
        assertNull(actualUpdateCustomerResult);
    }


    @Test
    void testDeleteCustomer() {
        // Arrange
        // Mock the behavior of the customer repository deleteById method to do nothing
        doNothing().when(customerRepository).deleteById(Mockito.<Long>any());

        // Act
        // Invoke the method under test
        Boolean actualDeleteCustomerResult = customerServiceImpl.deleteCustomer(1L);

        // Assert
        // Verify that deleteById was called with the correct ID
        verify(customerRepository).deleteById(eq(1L));
        // Assert that the actual result of deleting the customer is true
        assertTrue(actualDeleteCustomerResult);
    }


    @Test
    void testGetCustomerAll() {
        // Arrange
        // Create an empty list to simulate the list of customers returned by repository
        ArrayList<Customer> customerList = new ArrayList<>();
        // Mock the behavior of the customer repository findAll method to return the empty list
        when(customerRepository.findAll()).thenReturn(customerList);

        // Act
        // Invoke the method under test to retrieve all customers
        List<Customer> actualCustomerAll = customerServiceImpl.getCustomerAll();

        // Assert
        // Verify that findAll was called
        verify(customerRepository).findAll();
        // Assert that the returned list of customers is empty
        assertTrue(actualCustomerAll.isEmpty());
        // Assert that the actual list of customers is the same instance as the one returned by repository
        assertSame(customerList, actualCustomerAll);
    }

}
