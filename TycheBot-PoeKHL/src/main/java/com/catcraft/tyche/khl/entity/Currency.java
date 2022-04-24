package com.catcraft.tyche.khl.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "translated_name")
    private String translatedName;
    @Column(name = "common_name")
    private String commonName;
    @Column(name = "common_name_2")
    private String commonName2;
    @Column(name = "common_name_3")
    private String commonName3;
    @Column(name = "item_info")
    private String itemInfo;
    @Column(name = "chaos_value")
    private double chaosValue;
    @Column(name = "exalted_value")
    private double exaltedValue;
    @Column(name = "listing_count")
    private Integer listingCount;

}
