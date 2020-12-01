package org.pwte.example.service;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.pwte.example.domain.Category;
import org.pwte.example.domain.Product;
import org.pwte.example.exception.CategoryDoesNotExist;
import org.pwte.example.exception.ProductDoesNotExistException;

@Stateless
public class ProductSearchServiceImpl implements ProductSearchService {

	@PersistenceContext
	protected EntityManager em;
	
	public Category loadCategory(int categoryId)throws CategoryDoesNotExist {
		Category category = em.find(Category.class, categoryId);
		if(category == null) throw new CategoryDoesNotExist();
		return category;
	}
	
	

	public Product loadProduct(int productId) throws ProductDoesNotExistException {
		Product product = em.find(Product.class, productId);
		if(product == null) throw new ProductDoesNotExistException();
		return product;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Product> loadProductsByCategory(int categoryId) {
		Query query = em.createNamedQuery("product.by.cat.or.sub");
		query.setParameter(1, categoryId);
		query.setParameter(2, categoryId);
		return query.getResultList();
	}



	@SuppressWarnings("unchecked")
	public List<Category> getTopLevelCategories() {
		Query query = em.createNamedQuery("top.level.category");
		return query.getResultList();
	}

	

}
