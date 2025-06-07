package com.example.corpCartServer.service.user;

import com.example.corpCartServer.models.user.Customer;
import com.example.corpCartServer.models.user.User;
import com.example.corpCartServer.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public User createCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

}