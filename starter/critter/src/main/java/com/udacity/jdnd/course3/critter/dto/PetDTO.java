package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.Enum.PetType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinTable;
import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
@Data
@Getter
@Setter
public class PetDTO {
    private long id;

    @JoinTable(name = "pet_type")
    private PetType type;
    private String name;
    private long ownerId;
    private LocalDate birthDate;
    private String notes;
}
