package com.ilshan.autosell.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private Integer id;
    private String name;
    private String description;
    private Integer price;
}
