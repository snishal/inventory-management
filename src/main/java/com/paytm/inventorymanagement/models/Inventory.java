package com.paytm.inventorymanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "inventories")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class Inventory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer quantity;
}
