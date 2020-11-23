package com.ibm.articles;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;


public class CategoryOutput {

    public CategoryOutput() {}
   
    public String name;

    public List<SubCategoryOutput> subCategories;

    public int id;
}
