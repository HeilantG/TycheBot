package com.catcraft.tyche.crawler.repository;

import com.catcraft.tyche.crawler.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface CurrencyRepository extends JpaRepository<Currency, Long>, JpaSpecificationExecutor<Currency>, Serializable {

    /**
     * 通过英文名查询
     * @param name 英文名
     * @return
     */
    Currency findByName(String name);

    /**
     * 通过特殊物品译名查询
     * @param translatedName 译名
     * @return
     */
    Currency findByTranslatedName(String translatedName);

    /**
     * 通过特殊物品别名查询
     * @param commonName 别名
     * @return
     */
    Currency findByCommonName(String commonName);
    /**
     * 通过特殊物品别名查询2
     * @param commonName 别名
     * @return
     */
    Currency findByCommonName2(String commonName);
    /**
     * 通过特殊物品别名查询3
     * @param commonName 别名
     * @return
     */
    Currency findByCommonName3(String commonName);

}
