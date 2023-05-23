package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public CustomerDTO saveCustomer(CustomerDTO customerDTO){
        Customer tempCustomer = customerRepository.save(convertDtoToEntity(customerDTO));
        return convertEntityToDto(tempCustomer);
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id){
        return customerRepository.getOne(id);
    }

    public Customer convertDtoToEntity(CustomerDTO customerDto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }

    public CustomerDTO convertEntityToDto(Customer customer){
        CustomerDTO customerDto = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }
}
