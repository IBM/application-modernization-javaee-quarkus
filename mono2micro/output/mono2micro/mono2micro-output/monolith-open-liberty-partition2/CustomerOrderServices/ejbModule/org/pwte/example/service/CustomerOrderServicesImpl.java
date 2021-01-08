package org.pwte.example.service;

import com.ibm.cardinal.util.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.pwte.example.service.ProductSearchService;
import org.pwte.example.service.ProductSearchServiceImpl;

import org.pwte.example.domain.AbstractCustomer;
import org.pwte.example.domain.Address;
import org.pwte.example.domain.BusinessCustomer;
import org.pwte.example.domain.LineItem;
import org.pwte.example.domain.Order;
import org.pwte.example.domain.Product;
import org.pwte.example.domain.ResidentialCustomer;
import org.pwte.example.exception.CustomerDoesNotExistException;
import org.pwte.example.exception.GeneralPersistenceException;
import org.pwte.example.exception.InvalidQuantityException;
import org.pwte.example.exception.NoLineItemsException;
import org.pwte.example.exception.OrderAlreadyOpenException;
import org.pwte.example.exception.OrderModifiedException;
import org.pwte.example.exception.OrderNotOpenException;
import org.pwte.example.exception.ProductDoesNotExistException;
import javax.naming.InitialContext;
import java.util.Properties;


import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


//nik
//
public class CustomerOrderServicesImpl implements CustomerOrderServices {

	
	
	
	
	public Order addLineItem(LineItem newLineItem)
			throws CustomerDoesNotExistException, OrderNotOpenException,
			ProductDoesNotExistException,GeneralPersistenceException, InvalidQuantityException, OrderModifiedException {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/service/CustomerOrderServicesImpl.java:CustomerOrderServicesImpl:addLineItem");
    }

	public Order openOrder()
			throws CustomerDoesNotExistException, OrderAlreadyOpenException ,GeneralPersistenceException{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/service/CustomerOrderServicesImpl.java:CustomerOrderServicesImpl:openOrder");
    }

	public void submit(long version) throws CustomerDoesNotExistException,
			OrderNotOpenException, NoLineItemsException,GeneralPersistenceException, OrderModifiedException {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/service/CustomerOrderServicesImpl.java:CustomerOrderServicesImpl:submit");
    }

	

	public Order removeLineItem(int productId,long version) throws CustomerDoesNotExistException, OrderNotOpenException, ProductDoesNotExistException, NoLineItemsException, GeneralPersistenceException, OrderModifiedException {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/service/CustomerOrderServicesImpl.java:CustomerOrderServicesImpl:removeLineItem");
    }
	
	public AbstractCustomer loadCustomer() throws CustomerDoesNotExistException,GeneralPersistenceException {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/service/CustomerOrderServicesImpl.java:CustomerOrderServicesImpl:loadCustomer");
    }

	

	public Set<Order> loadCustomerHistory()
			throws CustomerDoesNotExistException,GeneralPersistenceException {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/service/CustomerOrderServicesImpl.java:CustomerOrderServicesImpl:loadCustomerHistory");
    }
	
	public Date getOrderHistoryLastUpdatedTime()
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/service/CustomerOrderServicesImpl.java:CustomerOrderServicesImpl:getOrderHistoryLastUpdatedTime");
    }

	public void updateAddress(Address address)
			throws CustomerDoesNotExistException, GeneralPersistenceException {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/service/CustomerOrderServicesImpl.java:CustomerOrderServicesImpl:updateAddress");
    }
	
	
	public void updateInfo(HashMap<String, Object> info)throws GeneralPersistenceException, CustomerDoesNotExistException
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/service/CustomerOrderServicesImpl.java:CustomerOrderServicesImpl:updateInfo");
    }

}

