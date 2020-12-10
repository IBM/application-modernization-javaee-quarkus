package org.pwte.example.jaxrs.test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import junit.framework.TestCase;

public class ProductRESTSearchTest extends TestCase {

	private String urlPrefix;

	public void setUp() throws Exception {
		try {
			Context envEntryContext = (Context) new InitialContext().lookup("java:comp/env");
			urlPrefix = (String) envEntryContext.lookup("CUSTOMER_ORDER_SERVICES_WEB_ENDPOINT");
		} catch (NamingException e) {
			e.printStackTrace();
			urlPrefix = "https://localhost:9443/CustomerOrderServicesWeb/";
		}
	}

	public void testProductResources() {
		RestClient client = new RestClient();
		Resource resource = client.resource(urlPrefix + "jaxrs/Product/1");

		JSONObject product = resource.accept("application/json").get(JSONObject.class);
		assertEquals("Return of the Jedi", product.get("name"));
		assertEquals(29.99, product.get("price"));
		assertEquals(new Long(1), (Long) product.get("id"));
		assertEquals("images/Return.jpg", product.get("image"));
		assertEquals("Episode 6, Luke has the final confrontation with his father!", product.get("description"));
	}

	public void testProductListByCategory() {
		RestClient client = new RestClient();
		Resource resource = client.resource(urlPrefix + "jaxrs/Product?categoryId=1");

		JSONArray productList = resource.accept("application/json").get(JSONArray.class);
		for (int i = 0; i < productList.size(); i++) {
			JSONObject product = (JSONObject) productList.get(i);

			switch (((Long) product.get("id")).intValue()) {
			case 1: {
				assertEquals("Return of the Jedi", product.get("name"));
				assertEquals(29.99, product.get("price"));
				assertEquals(new Long(1), product.get("id"));
				assertEquals("images/Return.jpg", product.get("image"));
				assertEquals("Episode 6, Luke has the final confrontation with his father!",
						product.get("description"));
				break;
			}
			case 2: {
				assertEquals("Empire Strikes Back", product.get("name"));
				assertEquals(29.99, product.get("price"));
				assertEquals(new Long(2), product.get("id"));
				assertEquals("images/Empire.jpg", product.get("image"));
				assertEquals("Episode 5, Luke finds out a secret that will change his destiny",
						product.get("description"));
				break;
			}
			case 3: {
				assertEquals("New Hope", product.get("name"));
				assertEquals(29.99, product.get("price"));
				assertEquals(new Long(3), product.get("id"));
				assertEquals("images/NewHope.jpg", product.get("image"));
				assertEquals("Episode 4, after years of oppression, a band of rebels fight for freedom",
						product.get("description"));
				break;
			}
			default: {
				// fail("Invalid object");
			}
			}
		}
	}

	public void testCategoryResource() {
		RestClient client = new RestClient();
		Resource resource = client.resource(urlPrefix + "jaxrs/Category/1");

		JSONObject category = resource.accept("application/json").get(JSONObject.class);
		assertEquals("Entertainment", category.get("name"));
		assertEquals(new Long(1), category.get("id"));
		JSONArray subCategories = (JSONArray) category.get("subCategories");
		for (int i = 0; i < subCategories.size(); i++) {
			JSONObject subCategory = (JSONObject) subCategories.get(i);
			switch (((Long) subCategory.get("id")).intValue()) {
			case 2: {
				assertEquals(new Long(2), subCategory.get("id"));
				assertEquals("Movies", subCategory.get("name"));
				break;
			}
			case 3: {
				assertEquals(new Long(3), subCategory.get("id"));
				assertEquals("Music", subCategory.get("name"));
				break;
			}
			case 4: {
				assertEquals(new Long(4), subCategory.get("id"));
				assertEquals("Games", subCategory.get("name"));
				break;
			}
			default: {
				fail("Invalid Subcategory");
			}
			}
		}
	}

	public void testCategoryListResources() {
		RestClient client = new RestClient();
		Resource resource = client.resource(urlPrefix + "jaxrs/Category");

		JSONArray categories = resource.accept("application/json").get(JSONArray.class);
		for (int k = 0; k < categories.size(); k++) {
			JSONObject category = (JSONObject) categories.get(k);
			switch (((Long) category.get("id")).intValue()) {
			case 1: {
				assertEquals("Entertainment", category.get("name"));
				assertEquals(new Long(1), category.get("id"));
				JSONArray subCategories = (JSONArray) category.get("subCategories");
				for (int i = 0; i < subCategories.size(); i++) {
					JSONObject subCategory = (JSONObject) subCategories.get(i);
					switch (((Long) subCategory.get("id")).intValue()) {
					case 2: {
						assertEquals(new Long(2), subCategory.get("id"));
						assertEquals("Movies", subCategory.get("name"));
						break;
					}
					case 3: {
						assertEquals(new Long(3), subCategory.get("id"));
						assertEquals("Music", subCategory.get("name"));
						break;
					}
					case 4: {
						assertEquals(new Long(4), subCategory.get("id"));
						assertEquals("Games", subCategory.get("name"));
						break;
					}
					default: {
						fail("Invalid Subcategory");
					}
					}
				}
				break;
			}
			case 10: {
				assertEquals("Electronics", category.get("name"));
				assertEquals(new Long(10), category.get("id"));
				JSONArray subCategories = (JSONArray) category.get("subCategories");
				for (int i = 0; i < subCategories.size(); i++) {
					JSONObject subCategory = (JSONObject) subCategories.get(i);
					switch (((Long) subCategory.get("id")).intValue()) {
					case 12: {
						assertEquals(new Long(12), subCategory.get("id"));
						assertEquals("TV", subCategory.get("name"));
						break;
					}
					case 13: {
						assertEquals(new Long(13), subCategory.get("id"));
						assertEquals("Cellphones", subCategory.get("name"));
						break;
					}
					case 14: {
						assertEquals(new Long(14), subCategory.get("id"));
						assertEquals("DVD Players", subCategory.get("name"));
						break;
					}
					default: {
						fail("Invalid Subcategory");
					}
					}
				}
				break;
			}
			default:
				fail("Invalid Category");

			}
		}
	}

}
