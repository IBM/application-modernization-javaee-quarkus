package org.pwte.example.resources;

import java.net.URI;
import java.text.SimpleDateFormat;
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

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

@Path("/Customer")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CustomerOrderResource {
	CustomerOrderServices customerOrderServices = null;
	
	public CustomerOrderResource() 
	{
		try {
			InitialContext ctx = new InitialContext();
			customerOrderServices = (CustomerOrderServices) ctx.lookup("java:app/CustomerOrderServices/CustomerOrderServicesImpl!org.pwte.example.service.CustomerOrderServices");
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer()
	{
		try {
			AbstractCustomer customer = customerOrderServices.loadCustomer();
			Order order = customer.getOpenOrder();
			if(order != null)
			{
				return Response.ok(customer).header("ETag", order.getVersion()).build();
			}
			return Response.ok(customer).build();
		} catch (CustomerDoesNotExistException e) {
			e.printStackTrace(System.out);
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} catch (GeneralPersistenceException e) {
			e.printStackTrace(System.out);
			throw new WebApplicationException(e);
		}
		
	}
	
	@PUT
	@Path("/Address")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAddress(Address address)
	{
		try {
			customerOrderServices.updateAddress(address);
			return Response.noContent().build();
		} catch (CustomerDoesNotExistException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (Exception e) {
			throw new WebApplicationException();
		}
	}
	
	@POST
	@Path("/OpenOrder/LineItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLineItem(LineItem lineItem,@Context HttpHeaders headers)
	{
		
		try {
			List<String> matchHeaders = headers.getRequestHeader("If-Match");
			if((matchHeaders != null) && (matchHeaders.size()>0))
			{
				
				lineItem.setVersion(new Long(matchHeaders.get(0)));
			}
			Order openOrder = customerOrderServices.addLineItem(lineItem);
			System.out.println("Open Order -> " + openOrder.getVersion());
			return Response.ok(openOrder).header("ETag", openOrder.getVersion()).location(new URI("Customer")).build();
		} catch (CustomerDoesNotExistException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (ProductDoesNotExistException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (InvalidQuantityException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		} catch (OrderModifiedException e) {
			throw new WebApplicationException(Status.PRECONDITION_FAILED);
		} 
		catch (Exception e) {
			throw new WebApplicationException(e);
		}
		
	}
	
	@DELETE
	@Path("/OpenOrder/LineItem/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeLineItem(@PathParam(value="productId") int productId,@Context HttpHeaders headers)
	{
		try {
			List<String> matchHeaders = headers.getRequestHeader("If-Match");
			if((matchHeaders != null) && (matchHeaders.size()>0))
			{
				Order openOrder = customerOrderServices.removeLineItem(productId,new Long(matchHeaders.get(0)));
				return Response.ok(openOrder).header("ETag", openOrder.getVersion()).build();	
			}
			else
			{
				return Response.status(Status.PRECONDITION_FAILED).build();
			}
		} 
		catch (CustomerDoesNotExistException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (ProductDoesNotExistException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (OrderModifiedException e) {
			throw new WebApplicationException(Status.PRECONDITION_FAILED);
		} 
		catch (Exception e) {
			throw new WebApplicationException(e);
		}
		
	}
	
	@POST
	@Path("/OpenOrder")
	public Response submitOrder(@Context HttpHeaders headers)
	{
		try
		{
			List<String> matchHeaders = headers.getRequestHeader("If-Match");
			if((matchHeaders != null) && (matchHeaders.size()>0))
			{
				customerOrderServices.submit(new Long(matchHeaders.get(0)));
				return Response.noContent().build();
			}
			else
			{
				return Response.status(Status.PRECONDITION_FAILED).build();
			}
		}
		catch (CustomerDoesNotExistException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} 
		catch (OrderModifiedException e) {
			throw new WebApplicationException(Status.PRECONDITION_FAILED);
		} 
		catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	
	@GET
	@Path("/Orders")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderHistory(@Context HttpHeaders headers)
	{
		try {
			Date lastModified = customerOrderServices.getOrderHistoryLastUpdatedTime();
			List<String> matchHeaders = headers.getRequestHeader("If-Modified-Since");
			
			if((matchHeaders != null) && (matchHeaders.size()>0))
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date headerDate = dateFormat.parse(matchHeaders.get(0));
				if(headerDate.getTime() < lastModified.getTime())
				{
					Set<Order> orders = customerOrderServices.loadCustomerHistory();
					return Response.ok(orders).lastModified(lastModified).build();
				}
				else
				{
					return Response.notModified().build();
				}
			}
			else
			{
				Set<Order> orders = customerOrderServices.loadCustomerHistory();
				return Response.ok(orders).lastModified(lastModified).build();
			}
			
		} catch (CustomerDoesNotExistException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (Exception e) {
			throw new WebApplicationException();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TypeForm")
	public Response getCustomerFormMeta()
	{
		try
		{
			AbstractCustomer customer = customerOrderServices.loadCustomer();
			JSONObject data = new JSONObject();
			JSONArray groups = new JSONArray();
			
			JSONObject name = new JSONObject();
			name.put("name", "name");
			name.put("label", "Name");
			name.put("type", "string");
			name.put("readonly", "true");
			groups.add(name);
			
			if(customer instanceof BusinessCustomer)
			{
				data.put("type","business");
				data.put("label","Business Customer");
				
				JSONObject desc = new JSONObject();
				desc.put("name", "description");
				desc.put("label", "Description");
				desc.put("type", "text");
				groups.add(desc);
				
				JSONObject bp = new JSONObject();
				bp.put("name", "businessPartner");
				bp.put("label", "Business Partner");
				bp.put("type", "string");
				bp.put("readonly", "true");
				groups.add(bp);
				
				JSONObject vd = new JSONObject();
				vd.put("name", "volumeDiscount");
				vd.put("label", "Volume Discount");
				vd.put("type", "string");
				vd.put("readonly", "true");
				groups.add(vd);
				
			}
			else
			{
				data.put("type","residential");
				data.put("label","Residential Customer");
				
				JSONObject freq = new JSONObject();
				freq.put("name", "frequentCustomer");
				freq.put("label", "Frequent Customer");
				freq.put("type", "string");
				freq.put("readonly", "true");
				groups.add(freq);
				
				JSONObject hs = new JSONObject();
				hs.put("name", "householdSize");
				hs.put("label", "Household Size");
				hs.put("type", "number");
				hs.put("constraints", "{min:1,max:10,places:0}");
				hs.put("required", "true");
				groups.add(hs);
				
			}
			data.put("formData",groups);
			return Response.ok(data).build();
		}
		catch (CustomerDoesNotExistException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		catch (GeneralPersistenceException e) {
			throw new WebApplicationException(e);
		}
	}
	
	@POST
	@Path("/Info")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateInfo(HashMap<String, Object> info)
	{
		try {
			customerOrderServices.updateInfo(info);
			return Response.noContent().build();
		} catch (CustomerDoesNotExistException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (Exception e) {
			throw new WebApplicationException();
		}
		
	}

}
