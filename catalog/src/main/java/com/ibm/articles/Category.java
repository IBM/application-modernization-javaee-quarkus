package com.ibm.articles;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Cacheable
public class Category extends PanacheEntity {

    public Category() {}
   
    public String name;

}
