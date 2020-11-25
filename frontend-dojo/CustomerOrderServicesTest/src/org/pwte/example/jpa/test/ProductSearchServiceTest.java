package org.pwte.example.jpa.test;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.pwte.example.domain.Category;
import org.pwte.example.domain.Product;
import org.pwte.example.service.ProductSearchService;

public class ProductSearchServiceTest extends DBTestCase {

	
	private ProductSearchService productSearchService;
	
	public ProductSearchServiceTest(String name) {
		super(name);

		String DBUNIT_DRIVER_CLASS = "";
		String DBUNIT_CONNECTION_URL = "";
		String DBUNIT_SCHEMA = "";
		String DBUNIT_USERNAME = "";
		String DBUNIT_PASSWORD = "";
		
		try {
			Context envEntryContext = (Context) new InitialContext().lookup("java:comp/env");
			
			DBUNIT_DRIVER_CLASS = (String) envEntryContext.lookup("DBUNIT_DRIVER_CLASS");
			DBUNIT_CONNECTION_URL = (String) envEntryContext.lookup("DBUNIT_CONNECTION_URL");
			DBUNIT_SCHEMA = (String) envEntryContext.lookup("DBUNIT_SCHEMA");
			DBUNIT_USERNAME = (String) envEntryContext.lookup("DBUNIT_USERNAME");
			DBUNIT_PASSWORD = (String) envEntryContext.lookup("DBUNIT_PASSWORD");
			
		} catch (NamingException e) {
			e.printStackTrace();
			
			DBUNIT_DRIVER_CLASS = "com.ibm.db2.jcc.DB2Driver";
			DBUNIT_CONNECTION_URL = "jdbc:db2://localhost:50000/ORDERDB";
			DBUNIT_SCHEMA = "DB2INST1";
			DBUNIT_USERNAME = "DB2INST1";
			DBUNIT_PASSWORD = "password";
		}
		
		System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DBUNIT_DRIVER_CLASS );
	    System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, DBUNIT_CONNECTION_URL );
	    System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, DBUNIT_SCHEMA );
	    System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, DBUNIT_USERNAME );
	    System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, DBUNIT_PASSWORD );
		
	}
	
	public void setUp() throws Exception {
		
		super.setUp();
		InitialContext ctx = new InitialContext();
		productSearchService = (ProductSearchService)ctx.lookup("java:comp/env/ejb/ProductSearchService");
		
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		ReplacementDataSet dataSet = new ReplacementDataSet(
		        new FlatXmlDataSet(this.getClass().getResourceAsStream ("CustomerOrderInitialDataSet.xml"))); 
		dataSet.addReplacementObject("[null]", null);
		return dataSet;
	}
	
	protected DatabaseOperation getSetUpOperation() 
	  throws Exception {
	    return DatabaseOperation.CLEAN_INSERT;
	 }
	
	protected DatabaseOperation getTearDownOperation() 
	  throws Exception {
		return DatabaseOperation.NONE;
	}

	public void testLoadCategory() {
		try
		{
			InputStream file = this.getClass().getResourceAsStream ("LoadCategoryParentExpectedResults.xml");
			IDataSet expectedParentDataSet = new FlatXmlDataSet(file);
			ITable expectedParentTable = expectedParentDataSet.getTable("CATEGORY");
			int categoryId = Integer.parseInt((String)expectedParentTable.getValue(0,"CAT_ID")); 
			Category cat = productSearchService.loadCategory(categoryId);
			assertEquals(Integer.parseInt((String)expectedParentTable.getValue(0,"CAT_ID")),cat.getCategoryID());
			assertEquals(expectedParentTable.getValue(0,"CAT_NAME"),cat.getName() );
			
			Collection<Category> childrenCats = cat.getSubCategories();
			InputStream childFile = this.getClass().getResourceAsStream ("LoadCategoryChildrenExpectedResults.xml");
			IDataSet expectedChildrenDataSet = new FlatXmlDataSet(childFile);
			ITable expectedChildrenTable = expectedChildrenDataSet.getTable("CATEGORY");
			assertEquals(expectedChildrenTable.getRowCount(),childrenCats.size());
			Iterator<Category> iterCat = childrenCats.iterator();
			for(int i = 0; i < childrenCats.size();i++)
			{
				cat = iterCat.next();
				if(Integer.parseInt((String)expectedChildrenTable.getValue(i,"CAT_ID"))==cat.getCategoryID())
				{
					assertEquals(Integer.parseInt((String)expectedChildrenTable.getValue(i,"CAT_ID")),cat.getCategoryID());
					assertEquals(expectedChildrenTable.getValue(i,"CAT_NAME"),cat.getName() );
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
			fail(e.getLocalizedMessage());
		}
		
	}

	public void testLoadProduct() {
		try
		{
			InputStream file = this.getClass().getResourceAsStream ("LoadProductExpectedData.xml");
			IDataSet expectedProductDataSet = new FlatXmlDataSet(file);
			ITable expectedProductTable = expectedProductDataSet.getTable("PRODUCT");
			int productId = Integer.parseInt((String)expectedProductTable.getValue(0,"PRODUCT_ID"));
			Product product = productSearchService.loadProduct(productId);
			assertEquals(Integer.parseInt((String)expectedProductTable.getValue(0,"PRODUCT_ID")),product.getProductId());
			assertEquals(expectedProductTable.getValue(0,"NAME"),product.getName() );
			assertEquals(expectedProductTable.getValue(0,"DESCRIPTION"),product.getDescription() );
			assertEquals(expectedProductTable.getValue(0,"PRICE"),product.getPrice().toString());
			
			Collection <Category> categories = product.getCategories();
			InputStream childFile = this.getClass().getResourceAsStream ("LoadProductsExpectCats.xml");
			IDataSet expectedCatDataSet = new FlatXmlDataSet(childFile);
			ITable expectedCatTable = expectedCatDataSet.getTable("CATEGORY");
			assertEquals(expectedCatTable.getRowCount(),categories.size());
			Iterator<Category> iterCat = categories.iterator();
			for(int i = 0; i < categories.size();i++)
			{
				Category cat = iterCat.next();
				if(Integer.parseInt((String)expectedCatTable.getValue(i,"CAT_ID"))==cat.getCategoryID())
				{
					assertEquals(Integer.parseInt((String)expectedCatTable.getValue(i,"CAT_ID")),cat.getCategoryID());
					assertEquals(expectedCatTable.getValue(i,"CAT_NAME"),cat.getName() );
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
			fail(e.getLocalizedMessage());
		}
	}

	public void testLoadProductsByCategory() {
		try
		{
			//TEST LOAD Product by Parent Category
			InputStream parentCatFile = this.getClass().getResourceAsStream ("LoadProductByCatParent.xml");
			IDataSet expectedParentDataSet = new FlatXmlDataSet(parentCatFile);
			ITable expectedParentTable = expectedParentDataSet.getTable("CATEGORY");
			int categoryId = Integer.parseInt((String)expectedParentTable.getValue(0,"CAT_ID")); 
			List<Product> products = productSearchService.loadProductsByCategory(categoryId);
			
			InputStream expectedParentCatFile = this.getClass().getResourceAsStream ("ExpectedCatsByParentCat.xml");
			IDataSet expectedProductDataSet = new FlatXmlDataSet(expectedParentCatFile);
			ITable expectedProductTable = expectedProductDataSet.getTable("PRODUCT");
			
			assertEquals(expectedProductTable.getRowCount(),products.size());
			for(int i= 0; i < products.size(); i++)
			{
				if(Integer.parseInt((String)expectedProductTable.getValue(0,"PRODUCT_ID"))==products.get(i).getProductId())
				{
					assertEquals(Integer.parseInt((String)expectedProductTable.getValue(0,"PRODUCT_ID")),products.get(i).getProductId());
					assertEquals(expectedProductTable.getValue(i,"NAME"),products.get(i).getName() );
					assertEquals(expectedProductTable.getValue(i,"DESCRIPTION"),products.get(i).getDescription() );
					assertEquals(expectedProductTable.getValue(i,"PRICE"),products.get(i).getPrice().toString());
				}
			}
			
			//TEST LOAD Product by Child Category
			InputStream childCatFile = this.getClass().getResourceAsStream ("LoadProductByCatChild.xml");
			IDataSet expectedChildDataSet = new FlatXmlDataSet(childCatFile);
			ITable expectedChildTable = expectedChildDataSet.getTable("CATEGORY");
			categoryId = Integer.parseInt((String)expectedChildTable.getValue(0,"CAT_ID")); 
			products = productSearchService.loadProductsByCategory(categoryId);
			
			InputStream expectedChildCatFile = this.getClass().getResourceAsStream ("ExpectedCatsByChildCat.xml");
			expectedProductDataSet = new FlatXmlDataSet(expectedChildCatFile);
			expectedProductTable = expectedProductDataSet.getTable("PRODUCT");
			
			assertEquals(expectedProductTable.getRowCount(),products.size());
			for(int i= 0; i < products.size(); i++)
			{
				if(Integer.parseInt((String)expectedProductTable.getValue(0,"PRODUCT_ID"))==products.get(i).getProductId())
				{
					assertEquals(Integer.parseInt((String)expectedProductTable.getValue(0,"PRODUCT_ID")),products.get(i).getProductId());
					assertEquals(expectedProductTable.getValue(i,"NAME"),products.get(i).getName() );
					assertEquals(expectedProductTable.getValue(i,"DESCRIPTION"),products.get(i).getDescription() );
					assertEquals(expectedProductTable.getValue(i,"PRICE"),products.get(i).getPrice().toString());
				}
			}
		}
		catch (Exception e) 
		{
			fail(e.getLocalizedMessage());
		}
	}

	public void testGetTopLevelCategories() {
		try
		{
			List<Category> categories = productSearchService.getTopLevelCategories();
			InputStream file = this.getClass().getResourceAsStream ("TopLevelCategoriesExpectedResults.xml");
			IDataSet expectedDataSet = new FlatXmlDataSet(file);
			ITable expectedTable = expectedDataSet.getTable("CATEGORY");
			assertEquals(expectedTable.getRowCount(),categories.size());
			for(int i = 0; i< categories.size();i++)
			{
				
				assertEquals(Integer.parseInt((String)expectedTable.getValue(i,"CAT_ID")),categories.get(i).getCategoryID());
				assertEquals(expectedTable.getValue(i,"CAT_NAME"),categories.get(i).getName() );
			}
		}
		catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}

	

}
