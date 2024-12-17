package com.assignment.customer;

import com.assignment.customer.entity.Customer;
import com.assignment.customer.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")  // Ensure Spring uses 'application-test.properties'
@Transactional  // Ensures the test will run in a transaction and any changes will be rolled back after the test
@TestMethodOrder(OrderAnnotation.class)  // Ensures the tests run in order of @Order annotations
class CustomerApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EntityManager entityManager;
    private static UUID customerId;
    private static Customer customer;

    @BeforeEach
    void setUp() throws InterruptedException {

        customerRepository.deleteAll();  // Clear existing data before each test
//        Thread.sleep(10);
        entityManager.clear();
    }

    @Test
    @Order(1)
    void testCreateCustomer() {
        customer = new Customer("Rahul", "B", "Kr", "rahul.kr.b@example.com", "555-565-5555");

        ResponseEntity<Customer> response = restTemplate.postForEntity("/api/customers", customer, Customer.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        customerId = response.getBody().getId();
        assertEquals("Rahul", response.getBody().getFirstName());
    }

    @Test
    @Order(2)
    void testGetAllCustomers() {
        ResponseEntity<List> response = restTemplate.exchange("/api/customers", HttpMethod.GET, null, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());  // After creating a customer, total should be 1
    }

    @Test
    @Order(3)
    void testGetCustomerById() {
        ResponseEntity<Customer> response = restTemplate.getForEntity("/api/customers/" + customerId, Customer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customerId, response.getBody().getId());
    }

    @Test
    @Order(4)
    void testUpdateCustomer() {
        customer.setPhoneNumber("999-999-9900");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);

        ResponseEntity<Customer> response = restTemplate.exchange("/api/customers/" + customerId, HttpMethod.PUT, entity, Customer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("999-999-9900", response.getBody().getPhoneNumber());
        assertEquals(customerId, response.getBody().getId());
    }

    @Test
    @Order(5)
    void testDeleteCustomer() {
        ResponseEntity<Void> response = restTemplate.exchange("/api/customers/" + customerId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(6)
    void testDeleteCustomer_NotFound() {
        ResponseEntity<Void> response = restTemplate.exchange("/api/customers/" + UUID.randomUUID(), HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(7)
    void testGetAllCustomersAfterDelete() {
        ResponseEntity<List> response = restTemplate.exchange("/api/customers", HttpMethod.GET, null, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());  // After delete, none remain
    }
}
