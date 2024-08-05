package com.ilshan.autosell.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "autosell", name = "t_car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_name")
    @NotNull
    @Size(min = 2,  max = 50)
    private String name;

    @Column(name = "c_description")
    @Size(max = 1000)
    private String description;

    @Column(name = "c_price")
    @Positive
    private Integer price;
}
