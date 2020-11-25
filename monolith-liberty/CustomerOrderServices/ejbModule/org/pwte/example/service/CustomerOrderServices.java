package org.pwte.example.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import org.pwte.example.domain.AbstractCustomer;
import org.pwte.example.domain.Address;
import org.pwte.example.domain.LineItem;
import org.pwte.example.domain.Order;
import org.pwte.example.exception.CustomerDoesNotExistException;
import org.pwte.example.exception.GeneralPersistenceException;
import org.pwte.example.exception.InvalidQuantityException;
import org.pwte.example.exception.NoLineItemsException;
import org.pwte.example.exception.OrderModifiedException;
import org.pwte.example.exception.OrderNotOpenException;
import org.pwte.example.exception.ProductDoesNotExistException;

public interface CustomerOrderServices {
	
	
	//public int getCustomerIdForUser()throws CustomerDoesNotExistException;
	
	
	/**
	 * @param customerId
	 * @return
	 * @throws CustomerDoesNotExistException
	 * @throws GeneralPersistenceException
	 */
	//public AbstractCustomer loadCustomer(int customerId)throws CustomerDoesNotExistException, GeneralPersistenceException;
	public AbstractCustomer loadCustomer()throws CustomerDoesNotExistException, GeneralPersistenceException;

	/**
	 * @param customerId
	 * @return
	 * @throws CustomerDoesNotExistException
	 * @throws OrderAlreadyOpenException
	 * @throws GeneralPersistenceException
	 */
	//public Order openOrder(int customerId) throws CustomerDoesNotExistException, OrderAlreadyOpenException, GeneralPersistenceException; 

	/**
	 * @param customerId
	 * @param productId
	 * @param quantity
	 * @return
	 * @throws CustomerDoesNotExistException
	 * @throws OrderNotOpenException
	 * @throws ProductDoesNotExistException
	 * @throws GeneralPersistenceException
	 */
	public Order addLineItem(LineItem lineItem) throws CustomerDoesNotExistException, OrderNotOpenException, ProductDoesNotExistException, GeneralPersistenceException, InvalidQuantityException, OrderModifiedException; 

	public Order removeLineItem(int productId,long version ) throws CustomerDoesNotExistException, OrderNotOpenException, ProductDoesNotExistException, NoLineItemsException, GeneralPersistenceException, OrderModifiedException;
	/**
	 * @param customerId
	 * @throws CustomerDoesNotExistException
	 * @throws OrderNotOpenException
	 * @throws NoLineItemsException
	 * @throws GeneralPersistenceException
	 */
	public void submit(long version) throws CustomerDoesNotExistException, OrderNotOpenException, NoLineItemsException, GeneralPersistenceException,OrderModifiedException ;

	public Set<Order> loadCustomerHistory()throws CustomerDoesNotExistException,GeneralPersistenceException;
	
	public void updateAddress(Address address) throws CustomerDoesNotExistException, GeneralPersistenceException;
	
	public Date getOrderHistoryLastUpdatedTime();
	
	public void updateInfo(HashMap<String, Object> info)throws GeneralPersistenceException, CustomerDoesNotExistException;

}