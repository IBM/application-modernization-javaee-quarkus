package org.pwte.example.service;

import java.util.List;

import org.pwte.example.domain.Category;
import org.pwte.example.domain.Product;
import org.pwte.example.exception.CategoryDoesNotExist;
import org.pwte.example.exception.ProductDoesNotExistException;

public interface ProductSearchService 
{
	public Product loadProduct(int productId) throws ProductDoesNotExistException;
	public List<Product> loadProductsByCategory(int categoryId);
	public Category loadCategory(int categoryId) throws CategoryDoesNotExist;
	public java.util.List<Category> getTopLevelCategories();
}
