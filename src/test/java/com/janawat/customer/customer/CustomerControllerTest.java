package com.janawat.customer.customer;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;


    @Test
    void testCreateCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1L);
        customer.setName("Name");
        when(customerService.createCustomer(Mockito.<Customer>any())).thenReturn(customer);

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1L);
        customer2.setName("Name");

        String content = (new ObjectMapper()).writeValueAsString(customer2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":200,\"message\":\"Added success\",\"data\":[{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example"
                                        + ".org\"}]}"));
    }


    @Test
    void testCreateCustomer2() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1L);
        customer.setName("Name");
        when(customerService.createCustomer(Mockito.<Customer>any())).thenReturn(customer);

        Customer customer2 = new Customer();
        customer2.setEmail("U.U.U");
        customer2.setId(1L);
        customer2.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customer2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":400,\"message\":\"Validation error(s):\",\"data\":[\"email Invalid email format\"]}"));
    }


    @Test
    void testGetCustomerById() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1L);
        customer.setName("Name");
        when(customerService.getCustomerById(Mockito.<Long>any())).thenReturn(customer);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":200,\"message\":\"Retrieved customer success\",\"data\":[{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe"
                                        + "@example.org\"}]}"));
    }


    @Test
    void testUpdateCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1L);
        customer.setName("Name");
        when(customerService.updateCustomer(Mockito.<Long>any(), Mockito.<Customer>any())).thenReturn(customer);

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1L);
        customer2.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customer2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customer/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":200,\"message\":\"Updated customer\",\"data\":[{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example"
                                        + ".org\"}]}"));
    }


    @Test
    void testUpdateCustomer2() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setId(1L);
        customer.setName("Name");
        when(customerService.updateCustomer(Mockito.<Long>any(), Mockito.<Customer>any())).thenReturn(customer);

        Customer customer2 = new Customer();
        customer2.setEmail("U.U.U");
        customer2.setId(1L);
        customer2.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customer2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customer/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":400,\"message\":\"Validation error(s):\",\"data\":[\"email Invalid email format\"]}"));
    }


    @Test
    void testDeleteCustomer() throws Exception {
        // Arrange
        when(customerService.deleteCustomer(Mockito.<Long>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customer/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":200,\"message\":\"Deleted success\",\"data\":null}"));
    }


    @Test
    void testDeleteCustomer2() throws Exception {
        // Arrange
        when(customerService.deleteCustomer(Mockito.<Long>any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customer/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":404,\"message\":\"Customer not found\",\"data\":null}"));
    }


    @Test
    void testGetAllCustomer() throws Exception {
        // Arrange
        when(customerService.getCustomerAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":200,\"message\":\"Retrieved all customers success\",\"data\":[]}"));
    }
}
