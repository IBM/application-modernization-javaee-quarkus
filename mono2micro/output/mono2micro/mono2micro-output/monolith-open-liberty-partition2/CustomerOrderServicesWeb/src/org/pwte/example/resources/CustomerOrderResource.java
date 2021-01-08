package org.pwte.example.resources;

import com.ibm.cardinal.util.*;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.pwte.example.domain.AbstractCustomer;
import org.pwte.example.domain.Address;
import org.pwte.example.domain.BusinessCustomer;
import org.pwte.example.domain.LineItem;
import org.pwte.example.domain.Order;
import org.pwte.example.exception.CustomerDoesNotExistException;
import org.pwte.example.exception.GeneralPersistenceException;
import org.pwte.example.exception.InvalidQuantityException;
import org.pwte.example.exception.OrderModifiedException;
import org.pwte.example.exception.ProductDoesNotExistException;
import org.pwte.example.service.CustomerOrderServices;
import java.util.Properties;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;



public class CustomerOrderResource {
	
		
	public CustomerOrderResource() 
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CustomerOrderResource.java:CustomerOrderResource:CustomerOrderResource");
    }

	
	
	public Response getCustomer()
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CustomerOrderResource.java:CustomerOrderResource:getCustomer");
    }
	
	
	
	
	public Response updateAddress(Address address)
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CustomerOrderResource.java:CustomerOrderResource:updateAddress");
    }
	
	
	
	
	
	public Response addLineItem(LineItem lineItem, HttpHeaders headers)
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CustomerOrderResource.java:CustomerOrderResource:addLineItem");
    }
	
	
	
	
	public Response removeLineItem( int productId, HttpHeaders headers)
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CustomerOrderResource.java:CustomerOrderResource:removeLineItem");
    }
	
	
	
	public Response submitOrder( HttpHeaders headers)
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CustomerOrderResource.java:CustomerOrderResource:submitOrder");
    }
	
	
	
	
	public Response getOrderHistory( HttpHeaders headers)
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CustomerOrderResource.java:CustomerOrderResource:getOrderHistory");
    }

	
	
	
	public Response getCustomerFormMeta()
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CustomerOrderResource.java:CustomerOrderResource:getCustomerFormMeta");
    }
	
	
	
	
	public Response updateInfo(HashMap<String, Object> info)
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CustomerOrderResource.java:CustomerOrderResource:updateInfo");
    }

}

