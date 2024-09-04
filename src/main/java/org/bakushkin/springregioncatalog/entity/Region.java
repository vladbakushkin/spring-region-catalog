package org.bakushkin.springregioncatalog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Region {
    private Long id;
    private String name;
    private String shortName;
}
