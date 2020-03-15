package com.gmail.petrikov05.app.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ItemDTO {

    @NotNull
    @Size(min = 3, max = 40, message = "Name must be between 2 and 40 characters")
    private String name;
    @NotNull
    @Size(min = 3, max = 30, message = "Status must be between 2 and 30 characters")
    private String status;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
