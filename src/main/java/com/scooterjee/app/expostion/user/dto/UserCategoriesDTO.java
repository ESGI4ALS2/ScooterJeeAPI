package com.scooterjee.app.expostion.user.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserCategoriesDTO {

    @NotNull
    public List<Long> list;

    public UserCategoriesDTO() {
    }

    public UserCategoriesDTO(List<Long> list) {
        this.list = list;
    }
}
