package com.catcraft.tyche.khl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "vouch")
public class Vouch {
    @Id
    private int id;
    @Column(name = "origin_id")
    private String originId;
    @Column(name = "vouch_id")
    private String vouchId;
    @Column(name = "vouch_time")
    private Date VouchTime;
}
