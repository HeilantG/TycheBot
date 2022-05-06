package com.catcraft.tyche.khl.repository;

import com.catcraft.tyche.khl.entity.Currency;
import com.catcraft.tyche.khl.entity.Vouch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface VouchRepository extends JpaRepository<Vouch, Long>, JpaSpecificationExecutor<Vouch>, Serializable {
    int countByVouchId(String vouchId);

    Vouch findByOriginIdAndVouchId(String originId,String vouchId);

}
