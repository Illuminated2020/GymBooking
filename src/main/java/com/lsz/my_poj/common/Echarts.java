package com.lsz.my_poj.common;

import lombok.Data;

@Data
public class Echarts {
    private String name;
    private Integer value;

    public Echarts(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public Echarts() {
    }
}
