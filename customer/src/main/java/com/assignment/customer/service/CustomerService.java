package com.assignment.customer.service;

import com.assignment.customer.entity.Customer;
import com.assignment.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmailAddress(customer.getEmailAddress())) {
            logger.warn("Customer's email already exists linked with ID: {}", customer.getId());
            throw new IllegalArgumentException("Email address already exists.");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(UUID id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setMiddleName(updatedCustomer.getMiddleName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmailAddress(updatedCustomer.getEmailAddress());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            return customerRepository.save(existingCustomer);
        }).orElseThrow(() ->
        {
            logger.warn("Customer doesn't exists with ID: {}", id);
            return new IllegalArgumentException("Customer not found");
        });
    }

    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            logger.warn("Customer doesn't exists with ID: {}", id);
            throw new IllegalArgumentException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
}

