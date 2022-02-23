package com.houseagent.utils;

import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@Setter
public class VuePageable
{
    private Integer page = 1;
    private Integer limit= 20;
    public Pageable getPageable(){
        return PageRequest.of(this.page-1, this.limit);
    }
}
