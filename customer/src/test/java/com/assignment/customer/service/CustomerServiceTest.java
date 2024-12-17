package com.assignment.customer.service;

import com.assignment.customer.entity.Customer;
import com.assignment.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        customerId = UUID.randomUUID();
        customer = new Customer("John", "A", "Doe", "john.doe@example.com", "123-456-7890");
        customer.setId(customerId);
    }

    @Test
    void testCreateCustomer() {
        // Given
        when(customerRepository.existsByEmailAddress(customer.getEmailAddress())).thenReturn(false);
        when(customerRepository.save(customer)).thenReturn(customer);

        // When
        Customer createdCustomer = customerService.createCustomer(customer);

        // Then
        assertNotNull(createdCustomer);
        assertEquals(customer.getEmailAddress(), createdCustomer.getEmailAddress());
        verify(customerRepository, times(1)).save(customer); // Verify save method is called
    }

    @Test
    void testCreateCustomer_EmailAlreadyExists() {
        // Given
        when(customerRepository.existsByEmailAddress(customer.getEmailAddress())).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(customer));
        assertEquals("Email address already exists.", exception.getMessage());
        verify(customerRepository, times(0)).save(customer); // Ensure save is not called
    }

    @Test
    void testGetAllCustomers() {
        // Given
        Customer anotherCustomer = new Customer("Jane", "B", "Smith", "jane.smith@example.com", "987-654-3210");
        anotherCustomer.setId(UUID.randomUUID());
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer, anotherCustomer));

        // When
        var customers = customerService.getAllCustomers();

        // Then
        assertNotNull(customers);
        assertEquals(2, customers.size());
        assertTrue(customers.contains(customer));
        assertTrue(customers.contains(anotherCustomer));
    }

    @Test
    void testGetCustomerById() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // When
        Optional<Customer> retrievedCustomer = customerService.getCustomerById(customerId);

        // Then
        assertTrue(retrievedCustomer.isPresent());
        assertEquals(customerId, retrievedCustomer.get().getId());
    }

    @Test
    void testGetCustomerById_NotFound() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // When
        Optional<Customer> retrievedCustomer = customerService.getCustomerById(customerId);

        // Then
        assertFalse(retrievedCustomer.isPresent());
    }

    @Test
    void testUpdateCustomer() {
        // Given
        Customer updatedCustomer = new Customer("John", "A", "Doe", "john.doe@example.com", "555-555-5555");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(updatedCustomer);

        // When
        Customer result = customerService.updateCustomer(customerId, updatedCustomer);

        // Then
        assertNotNull(result);
        assertEquals(updatedCustomer.getPhoneNumber(), result.getPhoneNumber());
        verify(customerRepository, times(1)).save(customer); // Verify save method is called
    }

    @Test
    void testUpdateCustomer_NotFound() {
        // Given
        Customer updatedCustomer = new Customer("John", "A", "Doe", "john.doe@example.com", "555-555-5555");
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.updateCustomer(customerId, updatedCustomer);
        });
        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository, times(0)).save(customer); // Ensure save is not called
    }

    @Test
    void testDeleteCustomer() {
        // Given
        when(customerRepository.existsById(customerId)).thenReturn(true);

        // When
        customerService.deleteCustomer(customerId);

        // Then
        verify(customerRepository, times(1)).deleteById(customerId); // Verify deleteById is called
    }

    @Test
    void testDeleteCustomer_NotFound() {
        // Given
        when(customerRepository.existsById(customerId)).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.deleteCustomer(customerId);
        });
        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository, times(0)).deleteById(customerId); // Ensure deleteById is not called
    }
}
