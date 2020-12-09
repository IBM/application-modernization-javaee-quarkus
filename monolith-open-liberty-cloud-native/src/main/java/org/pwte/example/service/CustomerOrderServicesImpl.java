package org.pwte.example.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import javax.enterprise.context.ApplicationScoped;
import javax.naming.InitialContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ejb.EJB;

@ApplicationScoped
public class CustomerOrderServicesImpl implements CustomerOrderServices {

	@PersistenceContext
	protected EntityManager em;
	
	@Resource
    UserTransaction utx;
	
	@Transactional
	public void updateLineItem(String productId, String newPrice) {
		try {
			AbstractCustomer customer = loadCustomer();
			Set<Order> orders = customer.getOrders();
			if (orders != null) {
				for (Order order : orders) {
					Set<LineItem> lineItems = order.getLineitems();
					Set<LineItem> updatedLineItems = new HashSet<LineItem>();
					if (lineItems != null ) {
						for (LineItem lineItem:lineItems) {
							int lineItemProductId = lineItem.getProductId();
							if (lineItemProductId == Integer.parseInt(productId)) {
								lineItem.setPriceCurrent(new BigDecimal(newPrice));
							}	
							updatedLineItems.add(lineItem);		
						}						
					}
					order.setLineitems(updatedLineItems);
				}				
			}		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
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
				lineItem.setPriceCurrent(lineItem.getAmount());
				return existingOpenOrder;
			}
		}
		
		existingOpenOrder = customer.getOpenOrder();
		LineItem lineItem = new LineItem();
		lineItem.setOrderId(existingOpenOrder.getOrderId());
		lineItem.setOrder(existingOpenOrder);
		lineItem.setProductId(product.getProductId());
		lineItem.setAmount(amount);
		lineItem.setPriceCurrent(product.getPrice());
		lineItem.setProduct(product);
		lineItem.setQuantity(quantity);
		lineItems.add(lineItem);
		existingOpenOrder.setLineitems(lineItems);
		
		// super dirty hack, because of JPA issues
		// to be done tbd
		if (lineItem.getOrderId() == 0) lineItem.setOrderId(1);
		
		try {
			utx.begin();
			em.persist(lineItem);
			utx.commit();		
		} catch (Exception e) {
            System.out.println(e);
		}
			
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
		newOrder.setStatus(Order.STATUS_OPEN);
		newOrder.setTotal(new BigDecimal(0));
		em.persist(newOrder);
		customer.setOpenOrder(newOrder);
		return newOrder;
	}

	public void submit(long version) throws CustomerDoesNotExistException,
			OrderNotOpenException, NoLineItemsException,GeneralPersistenceException, OrderModifiedException {
		AbstractCustomer customer = loadCustomer();
		Order existingOpenOrder = customer.getOpenOrder();
		if (existingOpenOrder == null || !existingOpenOrder.getStatus().equalsIgnoreCase(Order.STATUS_OPEN))
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
		
		existingOpenOrder.setStatus(Order.STATUS_SUBMITTED);
		existingOpenOrder.setSubmittedTime(new Date());
		customer.setOpenOrder(null);
	}

	public Order removeLineItem(int productId,long version) throws CustomerDoesNotExistException, OrderNotOpenException, ProductDoesNotExistException, NoLineItemsException, GeneralPersistenceException, OrderModifiedException {
		Product product = em.find(Product.class,productId);
		if(product == null) throw new ProductDoesNotExistException();
		
		AbstractCustomer customer = loadCustomer();
		Order existingOpenOrder = customer.getOpenOrder();
		if(existingOpenOrder == null || !existingOpenOrder.getStatus().equalsIgnoreCase(Order.STATUS_OPEN))
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
	
	public AbstractCustomer loadCustomer() throws CustomerDoesNotExistException,GeneralPersistenceException {
		//nik
		//String user = ctx.getCallerPrincipal().getName();
		String user = "rbarcia";
		Query query = em.createQuery("select c from AbstractCustomer c where c.user = :user");
		query.setParameter("user", user);
		return (AbstractCustomer)query.getSingleResult();
	}

	@EJB ProductSearchService productSearchService;

	public Set<Order> loadCustomerHistory()
			throws CustomerDoesNotExistException,GeneralPersistenceException {
		AbstractCustomer customer = loadCustomer();
		
		Set<Order> orders = customer.getOrders();
		/*
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
						// as workaround for now 50% of the prices are increased by 10

						try {
							productSearchService = (ProductSearchService) new InitialContext().lookup("java:app/CustomerOrderServices/ProductSearchServiceImpl!org.pwte.example.service.ProductSearchService");
							Product product = productSearchService.loadProduct(productId);							
							currentPrice = product.getPrice();					
							Random rand = new Random();
							int n = rand.nextInt(100);
							if (n > 50) {								
								BigDecimal increasedPrice = new BigDecimal("10.00");
								lineItem.setPriceCurrent(currentPrice.add(increasedPrice));
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
		*/
		return orders;
	}
	
	public Date getOrderHistoryLastUpdatedTime()
	{
		//String user = ctx.getCallerPrincipal().getName();
		String user = "rbarcia";
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
