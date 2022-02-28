package com.zinkworks.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AtmClientRequest {
    @NotNull(message = "pin: may be not null.")
    @NotEmpty(message = "pin: may be not empty.")
    @Size(min = 4, max = 4, message = "pin: must be of 4 digits number.")
    private String pin;

    @NotNull(message = "money: may be not null.")
    @NotEmpty(message = "money: may be not empty.")
    @Size(min = 1, max = 4, message = "money: must be up to 4 digits number.")
    private String money;
}
