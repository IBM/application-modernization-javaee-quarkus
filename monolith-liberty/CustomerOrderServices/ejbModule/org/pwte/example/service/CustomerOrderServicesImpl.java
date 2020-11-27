package org.pwte.example.service;

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

@Stateless
//nik
//@RolesAllowed(value="SecureShopper")
public class CustomerOrderServicesImpl implements CustomerOrderServices {

	@PersistenceContext
	protected EntityManager em;
	
	@Resource SessionContext ctx;
	
	public Order addLineItem(LineItem newLineItem)
			throws CustomerDoesNotExistException, OrderNotOpenException,
			ProductDoesNotExistException,GeneralPersistenceException, InvalidQuantityException, OrderModifiedException {
		
		int productId = newLineItem.getProductId();
		long quantity = newLineItem.getQuantity();
		Product product = em.find(Product.class,productId);
		if(quantity <= 0 ) throw new InvalidQuantityException();
		if(product == null) throw new ProductDoesNotExistException();
		AbstractCustomer customer = loadCustomer();
		Order existingOpenOrder = customer.getOpenOrder();
		if(existingOpenOrder == null)
		{
			existingOpenOrder = openOrder();
		}
		else
		{
			if(existingOpenOrder.getVersion() != newLineItem.getVersion())
			{
				System.out.println("Version not equal: " + existingOpenOrder.getVersion() + "---"+ newLineItem.getVersion());
				throw new OrderModifiedException();
			}
			else
			{
				existingOpenOrder.setVersion(newLineItem.getVersion());
			}
		}
		BigDecimal amount = product.getPrice().multiply(new BigDecimal(quantity));
		Set<LineItem> lineItems = existingOpenOrder.getLineitems();
		if (lineItems == null ) lineItems = new HashSet<LineItem>();
		for(LineItem lineItem:lineItems)
		{
			if(lineItem.getProductId() == productId)
			{
				lineItem.setQuantity(lineItem.getQuantity() + quantity);
				lineItem.setAmount(product.getPrice());
				// nik - to be done
				lineItem.setPriceCurrent(lineItem.getAmount());
				return existingOpenOrder;
			}
		}
		
		LineItem lineItem = new LineItem();
		lineItem.setOrderId(existingOpenOrder.getOrderId());
		lineItem.setOrder(existingOpenOrder);
		lineItem.setProductId(product.getProductId());
		lineItem.setAmount(amount);
		// nik - to be done
		lineItem.setPriceCurrent(product.getPrice());
		lineItem.setProduct(product);
		lineItem.setQuantity(quantity);
		lineItems.add(lineItem);
		existingOpenOrder.setLineitems(lineItems);
		em.persist(lineItem);
		System.out.println("EXITING addLineItem SERVICE -> " + existingOpenOrder.getVersion());
		return existingOpenOrder;
	}

	public Order openOrder()
			throws CustomerDoesNotExistException, OrderAlreadyOpenException ,GeneralPersistenceException{
		AbstractCustomer customer = loadCustomer();
		Order existingOpenOrder = customer.getOpenOrder();
		if(existingOpenOrder != null)
		{
			throw new OrderAlreadyOpenException();
		}
		
		Order newOrder = new Order();
		newOrder.setCustomer(customer);
		newOrder.setStatus(Order.Status.OPEN);
		System.out.println(newOrder.getStatus());
		newOrder.setTotal(new BigDecimal(0));
		
		em.persist(newOrder);
		
		customer.setOpenOrder(newOrder);
		return newOrder;
	}

	public void submit(long version) throws CustomerDoesNotExistException,
			OrderNotOpenException, NoLineItemsException,GeneralPersistenceException, OrderModifiedException {
		AbstractCustomer customer = loadCustomer();
		Order existingOpenOrder = customer.getOpenOrder();
		if(existingOpenOrder == null || existingOpenOrder.getStatus() != Order.Status.OPEN)
		{
			throw new OrderNotOpenException();
		}
		else
		{
			if(existingOpenOrder.getVersion() != version)
			{
				throw new OrderModifiedException();
			}
			else
			{
				existingOpenOrder.setVersion(version);
			}
		}
		if(existingOpenOrder.getLineitems() == null || existingOpenOrder.getLineitems().size() <= 0 )
			throw new NoLineItemsException();
		
		existingOpenOrder.setStatus(Order.Status.SUBMITTED);
		existingOpenOrder.setSubmittedTime(new Date());
		customer.setOpenOrder(null);
	}

	

	public Order removeLineItem(int productId,long version) throws CustomerDoesNotExistException, OrderNotOpenException, ProductDoesNotExistException, NoLineItemsException, GeneralPersistenceException, OrderModifiedException {
		Product product = em.find(Product.class,productId);
		if(product == null) throw new ProductDoesNotExistException();
		
		AbstractCustomer customer = loadCustomer();
		Order existingOpenOrder = customer.getOpenOrder();
		if(existingOpenOrder == null || existingOpenOrder.getStatus() != Order.Status.OPEN)
		{
			throw new OrderNotOpenException();
		}
		else
		{
			if(existingOpenOrder.getVersion() != version)
			{
				throw new OrderModifiedException();
			}
			else
			{
				existingOpenOrder.setVersion(version);
			}
		}
		Set<LineItem> lineItems = existingOpenOrder.getLineitems();
		for(LineItem lineItem:lineItems)
		{
			if(lineItem.getProductId() == productId)
			{
				lineItems.remove(lineItem);
				existingOpenOrder.setLineitems(lineItems);
				em.remove(lineItem);
				return existingOpenOrder;
			}
			
		}
		throw new NoLineItemsException();
	}
	
	
	
	/*
	public int getCustomerIdForUser()throws CustomerDoesNotExistException
	{
		String user = ctx.getCallerPrincipal().getName();
		Query query = em.createQuery("select c.customerId from AbstractCustomer c where c.user = :user");
		query.setParameter("user", user);
		int customerId = (Integer)query.getSingleResult();
		return customerId;
	}
	
	*/
	
	public AbstractCustomer loadCustomer() throws CustomerDoesNotExistException,GeneralPersistenceException {
		//nik
		//String user = ctx.getCallerPrincipal().getName();
		String user = "rbarcia";
		Query query = em.createQuery("select c from AbstractCustomer c where c.user = :user");
		query.setParameter("user", user);
		return (AbstractCustomer)query.getSingleResult();
	}
	/*
	private AbstractCustomer loadCustomer(int customerId) throws CustomerDoesNotExistException,GeneralPersistenceException {
		AbstractCustomer customer = em.find(AbstractCustomer.class, customerId);
		if(customer == null) throw new CustomerDoesNotExistException();
		//Instance Security Check
		if(ctx.getCallerPrincipal().getName().equals(customer.getUser())) return customer;
		else throw new CustomerDoesNotExistException();
	}*/

	public Set<Order> loadCustomerHistory()
			throws CustomerDoesNotExistException,GeneralPersistenceException {
		AbstractCustomer customer = loadCustomer();
		
		Set<Order> orders = customer.getOrders();
		if (orders != null) {
			for (Order order : orders) {
				Set<LineItem> lineItems = order.getLineitems();
				Set<LineItem> updatedLineItems = new HashSet<LineItem>();
				if (lineItems != null ) {
					for(LineItem lineItem:lineItems)
					{					
						int productId = lineItem.getProductId();
						BigDecimal currentPrice;

						// to be done: add POST endpoint to change product prices
						// as workaround for now 50% of the prices are set to 1000

						try {
							// to be done: why does the lookup not work here?
							//InitialContext ctx = new InitialContext();
							//ProductSearchService productSearchService = (ProductSearchService)ctx.lookup("java:comp/env/ejb/ProductSearchService");
							//Product product = productSearchService.loadProduct(productId);
							//currentPrice = product.getPrice();						
							Random rand = new Random();
							int n = rand.nextInt(100);
							if (n > 50) {								
								BigDecimal increasedPrice = new BigDecimal("1000.00");
								lineItem.setPriceCurrent(increasedPrice);
							}
						}
						catch (Exception exception) {
						}												
						
						updatedLineItems.add(lineItem);
					}
					order.setLineitems(updatedLineItems);
				}
			}
		}
		return orders;
	}
	
	public Date getOrderHistoryLastUpdatedTime()
	{
		String user = ctx.getCallerPrincipal().getName();
		Query query = em.createQuery("select MAX(o.submittedTime) from Order o join  o.customer c where c.user = :user");
		query.setParameter("user", user);
		return (Date)query.getSingleResult();
	}

	public void updateAddress(Address address)
			throws CustomerDoesNotExistException, GeneralPersistenceException {
		AbstractCustomer customer = loadCustomer();
		customer.setAddress(address);
	}
	
	
	public void updateInfo(HashMap<String, Object> info)throws GeneralPersistenceException, CustomerDoesNotExistException
	{
		AbstractCustomer customer = loadCustomer();
		if(info.get("type").equals("BUSINESS"))
		{
			((BusinessCustomer)customer).setDescription((String)info.get("description"));
		}
		else
		{
			((ResidentialCustomer)customer).setHouseholdSize(((Integer)info.get("householdSize")).shortValue());
		}
	}

}
