package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public PetDTO savePet(PetDTO petDTO) {
        Customer customer = customerRepository.getById(petDTO.getOwnerId());
        Pet pet = convertDtoToEntity(petDTO);
        pet.setCustomer(customer);
        Pet savedPet = petRepository.save(pet);
        if (customer.getPets() == null) {
            List<Pet> petList = new ArrayList<>();
            customer.setPets(petList);
        }
        customer.getPets().add(savedPet);
        customerRepository.save(customer);
        return convertEntityToDto(savedPet);
    }

    public Pet getPetById(Long id){
        Pet pet = petRepository.getById(id);
        return pet;
    }

    public List<PetDTO> getAllPets(){
        List<Pet> petList = petRepository.findAll();
        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet pet : petList){
            petDTOList.add(convertEntityToDto(pet));
        }
        return petDTOList;
    }

    public List<Pet> getPetsByIds(List<Long> ids){
        List<Pet> petList = new ArrayList<>();
        for (Long id : ids){
            petList.add(petRepository.getById(id));
        }
        return petList;
    }

    public List<PetDTO> getPetsByCustomerId(Long customerId){
        List<Pet> petList = petRepository.getPetsByOwnerId(customerId);
        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet pet : petList){
            petDTOList.add(convertEntityToDto(pet));
        }
        return petDTOList;
    }

    public Pet convertDtoToEntity(PetDTO petDTO){
        Pet pet = new Pet();

        BeanUtils.copyProperties(petDTO, pet, "ownerId", "type");

        //Adds the customer to the Pet instance
        Customer customer = customerRepository.getById(petDTO.getOwnerId());
        pet.setCustomer(customer);
        pet.setPetType(petDTO.getType());

        return pet;
    }

    public PetDTO convertEntityToDto(Pet pet){
        PetDTO petDTO = new PetDTO();

        BeanUtils.copyProperties(pet, petDTO, "customer", "petType");

        Customer customer = customerRepository.getById(pet.getCustomer().getId());
        petDTO.setOwnerId(customer.getId());
        petDTO.setType(pet.getPetType());

        return petDTO;
    }
}
