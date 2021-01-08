/**
 * This is a Cardinal generated proxy
 */

package org.pwte.example.service;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import com.ibm.cardinal.util.CardinalException;
import com.ibm.cardinal.util.CardinalLogger;
import com.ibm.cardinal.util.CardinalString;
import com.ibm.cardinal.util.ClusterObjectManager;
import com.ibm.cardinal.util.KluInterface;
import com.ibm.cardinal.util.SerializationUtil;


import java.util.List;


import org.pwte.example.domain.Category;
import org.pwte.example.domain.Product;
import org.pwte.example.exception.CategoryDoesNotExist;
import org.pwte.example.exception.ProductDoesNotExistException;


public class ProductSearchServiceImpl implements KluInterface, ProductSearchService {
    private String klu__referenceID = "";
    private static String klu__serviceURI;
    private static Client klu__client;
    private static final Logger klu__logger = CardinalLogger.getLogger(ProductSearchServiceImpl.class);

    static {
        klu__client = ClientBuilder.newClient();

        klu__logger.info("Static initializer of ProductSearchServiceImpl of cluster partition2");

        klu__serviceURI = System.getenv().get("APPLICATION_PARTITION2_REST_URL");
        if (klu__serviceURI == null) {
            throw new java.lang.RuntimeException("Environment variable "+
                "APPLICATION_PARTITION2_REST_URL not set\n"+
                "Please set APPLICATION_PARTITION2_REST_URL to "+
                "partition2 host:port"
            );
        }

        if (!klu__serviceURI.endsWith("/")) {
            klu__serviceURI += "/";
        }
        klu__serviceURI += "ProductSearchServiceImplService";

        try {
            java.net.URI uri = java.net.URI.create(klu__serviceURI);
        }
        catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("Invalid URI for partition partition2, "+
                "service ProductSearchServiceImplService: "+klu__serviceURI, e);
        }

        klu__logger.info("partition2 ProductSearchServiceImplService URI = " + klu__serviceURI);
    }

    // default constructor (generated)
    public ProductSearchServiceImpl() {
        Response svc_response =
            klu__client.target(klu__serviceURI) 
            .path("ProductSearchServiceImpl_default_ctor") 
            .request(MediaType.APPLICATION_JSON) 
            .post(Entity.text(""), Response.class);
        String response_json_str = svc_response.readEntity(String.class);
        klu__logger.info("[ProductSearchServiceImpl()] Response JSON string: "+response_json_str);
        JsonReader json_reader = Json.createReader(new StringReader(response_json_str));
        JsonObject response_json = json_reader.readObject();
        setKlu__referenceID(response_json.getString("return_value"));
        svc_response.close();

    }



    // constructor for creating proxy instances of this class using reference ID
    public ProductSearchServiceImpl(CardinalString referenceId) {
        setKlu__referenceID(referenceId.getString());
        Form form = new Form();
        form.param("klu__referenceID", getKlu__referenceID());
        klu__client.target(klu__serviceURI)
            .path("incObjectCount")
            .request()
            .post(Entity.form(form));
	}



    public String getKlu__referenceID() {
        return this.klu__referenceID;
    }

    public void setKlu__referenceID(String referenceId) {
        this.klu__referenceID = referenceId;
    }

    // decrement object reference count when this object is garbage collected
    public void finalize() {
        Form form = new Form();
        form.param("klu__referenceID", getKlu__referenceID());
        klu__client.target(klu__serviceURI)
            .path("decObjectCount")
            .request()
            .post(Entity.form(form));
    }



    // getter method for field "em" (generated)
    public  EntityManager get__em() {
        // create form for service request to pass reference ID
        Form form = new Form();
        form.param("klu__referenceID", getKlu__referenceID());
        klu__logger.info("[ProductSearchServiceImpl] Calling service "+klu__serviceURI+
            "/get__em with form: "+form.asMap());
        // call getter service and get encoded response from response JSON (for collection return type)
        Response svc_response;
        try {
            svc_response = klu__client.target(klu__serviceURI)
                .path("get__em")
                .request(MediaType.APPLICATION_JSON)
                .method(javax.ws.rs.HttpMethod.GET, Entity.form(form), Response.class);
        }
        catch (WebApplicationException wae) {
            java.lang.Throwable cause = wae.getCause();
            klu__logger.warning("ProductSearchServiceImpl] Exception thrown in service call: "+wae.getMessage());
            throw (java.lang.RuntimeException)cause;
        }
        String response_json_str = svc_response.readEntity(String.class);
        klu__logger.info("[ProductSearchServiceImpl] Response JSON string: "+response_json_str);
        JsonReader json_reader = Json.createReader(new StringReader(response_json_str));
        JsonObject response_json = json_reader.readObject();
        String response = response_json.getString("return_value");
        svc_response.close();
        
        // convert reference ID(s) stored in "response" to physical/proxy object(s)
        EntityManager response_obj = (EntityManager)SerializationUtil.decodeWithDynamicTypeCheck(response);
        return response_obj;

    }



    // setter method for field "em" (generated)
    public  void set__em(EntityManager em) {
        // create form for service request
        Form form = new Form();
        form.param("klu__referenceID", getKlu__referenceID());
        
        // convert physical/proxy object(s) referenced by "em" to reference ID(s)
        String em_fpar = SerializationUtil.encodeWithDynamicTypeCheck(em);
        form.param("em", em_fpar);


        klu__logger.info("[ProductSearchServiceImpl] Calling service "+klu__serviceURI+
            "/set__em with form: "+form.asMap());

        // call setter service (no response for "void" return type)
        try {
            klu__client.target(klu__serviceURI)
                .path("set__em")
                .request()
                .post(Entity.form(form));
        }
        catch (WebApplicationException wae) {
            java.lang.Throwable cause = wae.getCause();
            throw (java.lang.RuntimeException)cause;
        }
    }

public Category loadCategory(int categoryId)throws CategoryDoesNotExist {
        // create form for service request
        Form form = new Form();
        form.param("klu__referenceID", getKlu__referenceID());
        form.param("categoryId", String.valueOf(categoryId)); 

        klu__logger.info("[ProductSearchServiceImpl] Calling service "+klu__serviceURI+
            "/loadCategory with form: "+form.asMap());

        // call service and get encoded response from response JSON
        Response svc_response;
        try {
            svc_response = klu__client.target(klu__serviceURI)
                .path("loadCategory")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form), Response.class);
        }
        catch (WebApplicationException wae) {
            java.lang.Throwable cause = wae.getCause();
            klu__logger.warning("[ProductSearchServiceImpl] Exception thrown in service call: "+wae.getMessage());
            throw (java.lang.RuntimeException)cause;
        }
        String response_json_str = svc_response.readEntity(String.class);
        klu__logger.info("[ProductSearchServiceImpl] Response JSON string: "+response_json_str);
        JsonReader json_reader = Json.createReader(new StringReader(response_json_str));
        JsonObject response_json = json_reader.readObject();
        String response = response_json.getString("return_value");
        svc_response.close();
        
        // convert reference ID(s) stored in "response" to physical/proxy object(s)
        Category response_obj = (Category)SerializationUtil.decodeWithDynamicTypeCheck(response);
        return response_obj;

    }
	
	

	public Product loadProduct(int productId) throws ProductDoesNotExistException {
        // create form for service request
        Form form = new Form();
        form.param("klu__referenceID", getKlu__referenceID());
        form.param("productId", String.valueOf(productId)); 

        klu__logger.info("[ProductSearchServiceImpl] Calling service "+klu__serviceURI+
            "/loadProduct with form: "+form.asMap());

        // call service and get encoded response from response JSON
        Response svc_response;
        try {
            svc_response = klu__client.target(klu__serviceURI)
                .path("loadProduct")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form), Response.class);
        }
        catch (WebApplicationException wae) {
            java.lang.Throwable cause = wae.getCause();
            klu__logger.warning("[ProductSearchServiceImpl] Exception thrown in service call: "+wae.getMessage());
            if (wae.getResponse().getStatus() == CardinalException.APPLICATION_EXCEPTION) {
                klu__logger.warning("[ProductSearchServiceImpl] Re-throwing wrapped application exception: ");
                // typecast to declared exception types
                if (cause instanceof ProductDoesNotExistException) {
                    throw (ProductDoesNotExistException)cause;
                }
            }
            throw (java.lang.RuntimeException)cause;
        }
        String response_json_str = svc_response.readEntity(String.class);
        klu__logger.info("[ProductSearchServiceImpl] Response JSON string: "+response_json_str);
        JsonReader json_reader = Json.createReader(new StringReader(response_json_str));
        JsonObject response_json = json_reader.readObject();
        String response = response_json.getString("return_value");
        svc_response.close();
        
        // convert reference ID(s) stored in "response" to physical/proxy object(s)
        Product response_obj = (Product)SerializationUtil.decodeWithDynamicTypeCheck(response);
        return response_obj;

    }
	
	
	@SuppressWarnings("unchecked")
	public List<Product> loadProductsByCategory(int categoryId) {
        // create form for service request
        Form form = new Form();
        form.param("klu__referenceID", getKlu__referenceID());
        form.param("categoryId", String.valueOf(categoryId)); 

        klu__logger.info("[ProductSearchServiceImpl] Calling service "+klu__serviceURI+
            "/loadProductsByCategory with form: "+form.asMap());

        // call service and get encoded response from response JSON
        Response svc_response;
        try {
            svc_response = klu__client.target(klu__serviceURI)
                .path("loadProductsByCategory")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form), Response.class);
        }
        catch (WebApplicationException wae) {
            java.lang.Throwable cause = wae.getCause();
            klu__logger.warning("[ProductSearchServiceImpl] Exception thrown in service call: "+wae.getMessage());
            throw (java.lang.RuntimeException)cause;
        }
        String response_json_str = svc_response.readEntity(String.class);
        klu__logger.info("[ProductSearchServiceImpl] Response JSON string: "+response_json_str);
        JsonReader json_reader = Json.createReader(new StringReader(response_json_str));
        JsonObject response_json = json_reader.readObject();
        String response = response_json.getString("return_value");
        svc_response.close();
        
        // convert reference ID(s) stored in "response" to physical/proxy object(s)
        List<Product> response_obj = (List<Product>)SerializationUtil.decodeWithDynamicTypeCheck(response);
        return response_obj;

    }



	@SuppressWarnings("unchecked")
	public List<Category> getTopLevelCategories() {
        // create form for service request
        Form form = new Form();
        form.param("klu__referenceID", getKlu__referenceID());
        klu__logger.info("[ProductSearchServiceImpl] Calling service "+klu__serviceURI+
            "/getTopLevelCategories with form: "+form.asMap());

        // call service and get encoded response from response JSON
        Response svc_response;
        try {
            svc_response = klu__client.target(klu__serviceURI)
                .path("getTopLevelCategories")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form), Response.class);
        }
        catch (WebApplicationException wae) {
            java.lang.Throwable cause = wae.getCause();
            klu__logger.warning("[ProductSearchServiceImpl] Exception thrown in service call: "+wae.getMessage());
            throw (java.lang.RuntimeException)cause;
        }
        String response_json_str = svc_response.readEntity(String.class);
        klu__logger.info("[ProductSearchServiceImpl] Response JSON string: "+response_json_str);
        JsonReader json_reader = Json.createReader(new StringReader(response_json_str));
        JsonObject response_json = json_reader.readObject();
        String response = response_json.getString("return_value");
        svc_response.close();
        
        // convert reference ID(s) stored in "response" to physical/proxy object(s)
        List<Category> response_obj = (List<Category>)SerializationUtil.decodeWithDynamicTypeCheck(response);
        return response_obj;

    }

	

}

