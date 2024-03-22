package com.example.hairshop.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("Hair")
@Getter
public class Hair extends Style {

    private Gender gender;
    private Length length;
}
