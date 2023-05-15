package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer){
        Customer tempCustomer = customerRepository.save(customer);
        return tempCustomer;
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.getAllCustomer();
    }

    public Customer convertDtoToCustomer(CustomerDTO customerDto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }

    public CustomerDTO convertCustomerToDto(Customer customer){
        CustomerDTO customerDto = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }
}
