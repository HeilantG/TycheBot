package com.catcraft.tyche.khl.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user")
public class User {
    @Id
    private String id;
    @Column(name = "poe_id")
    private String poeId;
    @Column(name = "ban")
    private boolean ban;

}
