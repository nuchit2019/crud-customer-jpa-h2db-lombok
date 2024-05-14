package com.janawat.customer.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    private  Customer customer;

    @BeforeEach
    void setUp(){
        customerRepository.deleteAll();
        customer  = new Customer();
        customer.setName("Nuchit Janawat");
        customer.setEmail("nuchit@thanachart.co.th");
        customer = customerRepository.save(customer);
    }


    @Test
    void testCreateCustomer1() throws Exception {
        Customer newCustomer = new Customer();
        newCustomer.setName("Jane Doe");
        newCustomer.setEmail("jane.doe@example.com");

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data[0].name").value("Jane Doe"));
    }

    @Test
    void testgetAllCustomers() throws  Exception{
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data",hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Nuchit Janawat"));

    }

    @Test
    void testGetCustomerById() throws  Exception{
        mockMvc.perform(get("/customer/{id}",customer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("Nuchit Janawat"));
    }

    @Test
    void testUpdateCustomer() throws Exception{
        customer.setName("Nuchit Janawat");

        mockMvc.perform(put("/customer/{id}",customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("Nuchit Janawat"));

    }

    @Test
    void testDeleteCustomer() throws Exception{
        mockMvc.perform(delete("/customer/{id}",customer.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/customer/{id}",customer.getId()))
                .andExpect(status().isNotFound());
    }

}
