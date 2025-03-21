package com.ucentral.software.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String CC;

    private String pin;

    private String name;
    
    private List<CreditCard> creditCards;
    
}
