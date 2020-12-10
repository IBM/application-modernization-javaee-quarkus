package org.pwte.example.jpa.test;



import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.pwte.example.domain.AbstractCustomer;
import org.pwte.example.domain.Address;
import org.pwte.example.domain.LineItem;
import org.pwte.example.domain.Order;
import org.pwte.example.domain.ResidentialCustomer;
import org.pwte.example.exception.CustomerDoesNotExistException;
import org.pwte.example.exception.GeneralPersistenceException;
import org.pwte.example.exception.InvalidQuantityException;
import org.pwte.example.exception.NoLineItemsException;
import org.pwte.example.exception.OrderAlreadyOpenException;
import org.pwte.example.exception.OrderModifiedException;
import org.pwte.example.exception.OrderNotOpenException;
import org.pwte.example.exception.ProductDoesNotExistException;
import org.pwte.example.service.CustomerOrderServices;


public class CustomerOrderServicesTest extends DBTestCase{

	private CustomerOrderServices customerOrderServices;
	private int customerId = 2;
	private int businessCustomerId = 3;
	
	public CustomerOrderServicesTest(String name)
	{
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
		Properties props = new Properties();
		props.setProperty(Context.SECURITY_PRINCIPAL, "rbarcia");
		props.setProperty(Context.SECURITY_CREDENTIALS, "bl0wfish");
		InitialContext ctx = new InitialContext(props);
		customerOrderServices = (CustomerOrderServices)ctx.lookup("java:comp/env/ejb/CustomerOrderService");
		
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
	
	
	
	public void testLoadCustomer() throws DataSetException, IOException {
		AbstractCustomer customer;
		try {
			customer = customerOrderServices.loadCustomer();
			assertNotNull(customer);
			assertEquals(customerId,customer.getCustomerId() );
			//assertNull(customer.getOpenOrder());
			//customerOrderServices.openOrder(customerId);
			//customer = customerOrderServices.loadCustomer();
			//assertNotNull(customer.getOpenOrder());
			LineItem newLine = new LineItem();
			newLine.setProductId(1);
			newLine.setQuantity(1);
			Order order = customerOrderServices.addLineItem(newLine);
			customer = customerOrderServices.loadCustomer();
			assertNotNull(customer.getOpenOrder());
			assertNotNull(customer.getOpenOrder().getLineitems());
			assertTrue(customer.getOpenOrder().getLineitems().size() > 0);
			customerOrderServices.submit(order.getVersion());
			customer = customerOrderServices.loadCustomer();
			assertNull(customer.getOpenOrder());
			
			//Inherit Test
			assertTrue(customer instanceof ResidentialCustomer );
			
			//Fields and Address Test
			InputStream file = this.getClass().getResourceAsStream ("ExpectedCustomer.xml");
			IDataSet expectedCustomerDataSet = new FlatXmlDataSet(file);
			ITable expectedCustomerTable = expectedCustomerDataSet.getTable("CUSTOMER");
			assertEquals(Integer.parseInt((String)expectedCustomerTable.getValue(0,"CUSTOMER_ID")),customer.getCustomerId());
			assertEquals(expectedCustomerTable.getValue(0,"NAME"),customer.getName() );
			assertEquals(expectedCustomerTable.getValue(0,"TYPE"),customer.getType());
			assertEquals(expectedCustomerTable.getValue(0,"USERNAME"),customer.getUser() );
			assertEquals(expectedCustomerTable.getValue(0,"addressLine1"),customer.getAddress().getAddressLine1() );
			assertEquals(expectedCustomerTable.getValue(0,"addressLine2"),customer.getAddress().getAddressLine2() );
			assertEquals(expectedCustomerTable.getValue(0,"city"),customer.getAddress().getCity());
			assertEquals(expectedCustomerTable.getValue(0,"country"),customer.getAddress().getCountry() );
			assertEquals(expectedCustomerTable.getValue(0,"state"),customer.getAddress().getState() );
			assertEquals(expectedCustomerTable.getValue(0,"zip"),customer.getAddress().getZip() );
			assertEquals(Short.parseShort((String)expectedCustomerTable.getValue(0,"RESIDENTIAL_HOUSEHOLD_SIZE")),((ResidentialCustomer)customer).getHouseholdSize() );
			boolean pref = false;
			System.out.println("Expected Results -> " + expectedCustomerTable.getValue(0,"RESIDENTIAL_FREQUENT_CUSTOMER"));
			if(((String)expectedCustomerTable.getValue(0,"RESIDENTIAL_FREQUENT_CUSTOMER")).equals("Y")) pref = true;
			assertEquals(pref,((ResidentialCustomer)customer).isFrequentCustomer()  );
			/*
			//Test Business Customer
			expectedCustomerDataSet = new ReplacementDataSet(
			        new FlatXmlDataSet(this.getClass().getResourceAsStream ("ExpectedBusinessCustomer.xml"))); 
			((ReplacementDataSet)expectedCustomerDataSet).addReplacementObject("[null]", null);
			
			
			assertTrue(customer instanceof ResidentialCustomer);
			expectedCustomerTable = expectedCustomerDataSet.getTable("CUSTOMER");
			assertEquals(Integer.parseInt((String)expectedCustomerTable.getValue(0,"CUSTOMER_ID")),customer.getCustomerId());
			assertEquals(expectedCustomerTable.getValue(0,"NAME"),customer.getName() );
			assertEquals(expectedCustomerTable.getValue(0,"TYPE"),customer.getType());
			assertEquals(expectedCustomerTable.getValue(0,"USERNAME"),customer.getUser() );
			assertEquals(expectedCustomerTable.getValue(0,"addressLine1"),customer.getAddress().getAddressLine1() );
			assertEquals(expectedCustomerTable.getValue(0,"addressLine2"),customer.getAddress().getAddressLine2() );
			assertEquals(expectedCustomerTable.getValue(0,"city"),customer.getAddress().getCity());
			assertEquals(expectedCustomerTable.getValue(0,"country"),customer.getAddress().getCountry() );
			assertEquals(expectedCustomerTable.getValue(0,"state"),customer.getAddress().getState() );
			assertEquals(expectedCustomerTable.getValue(0,"zip"),customer.getAddress().getZip() );
			
			*/
			
		} catch (CustomerDoesNotExistException e) {
			e.printStackTrace();
			fail("Customer Does Not Exist");
		} catch (GeneralPersistenceException e) {
			e.printStackTrace();
			fail("Persistence Error");
		}  catch (OrderNotOpenException e) {
			e.printStackTrace();
			fail("Order Not Open");
		} catch (ProductDoesNotExistException e) {
			e.printStackTrace();
			fail("No Such Product");
		} catch (InvalidQuantityException e) {
			e.printStackTrace();
			fail("InvalidQuantity");
		} catch (NoLineItemsException e) {
			e.printStackTrace();
			fail("No Line Item");
		}
		catch (OrderModifiedException e) {
			fail("Order Modified");
		}
		
	}
	/*
	public void testOpenOrder() {
		try {
			Order openOrder = customerOrderServices.openOrder();
			assertNotNull(openOrder);
			AbstractCustomer customer = customerOrderServices.loadCustomer();
			assertNotNull(customer.getOpenOrder());
			assertEquals(customer.getOpenOrder().getOrderId(), openOrder.getOrderId());
			try
			{
			customerOrderServices.openOrder();
			}
			catch (OrderAlreadyOpenException e) {
				assertTrue("This is the Correct Exception",true);
			}
			customerOrderServices.addLineItem(, 1, 1);
			customerOrderServices.submit();
		} catch (OrderAlreadyOpenException e) {
			fail("Order Already Open");
		} catch (CustomerDoesNotExistException e) {
			fail("Customer does not exist.");
		} 
		catch (GeneralPersistenceException e) {
			fail("General Persistence Error");
		} 
		catch (OrderNotOpenException e) {
			e.printStackTrace();
			fail("Order Not Open");
		} catch (ProductDoesNotExistException e) {
			e.printStackTrace();
			fail("No Such Product");
		} catch (InvalidQuantityException e) {
			e.printStackTrace();
			fail("InvalidQuantity");
		} catch (NoLineItemsException e) {
			e.printStackTrace();
			fail("No Line Item");
		}
	}
	*/
	public void testAddLineItem() {
		try {
			
			System.out.println("TESTING ADD LINEITEM");
			//Order openOrder = customerOrderServices.openOrder();
			LineItem newLine = new LineItem();
			newLine.setProductId(1);
			newLine.setQuantity(1);
			Order order =  customerOrderServices.addLineItem(newLine);
			assertNotNull(order);
			AbstractCustomer customer = customerOrderServices.loadCustomer();
			Set<LineItem> lineItems = customer.getOpenOrder().getLineitems();
			assertEquals(order.getLineitems().size(),lineItems.size());
			//LineItem li = ((LineItem)(lineItems.iterator().next()));
			/*
			assertEquals(li.getProductId(),lineItem.getProductId());
			assertEquals(li.getOrderId(),lineItem.getOrderId());
			*/
			//Add Line Item for Same Product, should just bump Quantity
			newLine.setVersion(customer.getOpenOrder().getVersion());
			order = customerOrderServices.addLineItem(newLine);
			customer = customerOrderServices.loadCustomer();
			lineItems = customer.getOpenOrder().getLineitems();
			assertEquals(lineItems.size(), 1);
			
			//Add a Second Line Item ...
			newLine.setProductId(2);
			newLine.setVersion(order.getVersion());
			order = customerOrderServices.addLineItem( newLine);
			customer = customerOrderServices.loadCustomer();
			lineItems = customer.getOpenOrder().getLineitems();
			assertEquals(lineItems.size(), 2);
			
			try
			{
				newLine.setProductId(1);
				newLine.setQuantity(0);
				newLine.setVersion(order.getVersion());
				customerOrderServices.addLineItem(newLine);
			}
			catch (InvalidQuantityException e) {
				assertTrue("Correct Error, cannot add 0 items",true);
			} 
			try
			{
				newLine.setQuantity(-1);
				newLine.setVersion(order.getVersion());
				customerOrderServices.addLineItem(newLine);
			}
			catch (InvalidQuantityException e) {
				assertTrue("Correct Error, cannot add negative items",true);
			} 
			
			try
			{
				newLine.setProductId(444);
				newLine.setQuantity(1);
				newLine.setVersion(order.getVersion());
				customerOrderServices.addLineItem(newLine);
				fail("Cannot add product that does not exist");
			}
			catch (ProductDoesNotExistException e) {
				assertTrue("Correct Exception",true);
			} 
			
			//Test Order Total
			
			
			order = customerOrderServices.loadCustomer().getOpenOrder();
			
			BigDecimal total = new BigDecimal(0);
			for(LineItem item:order.getLineitems())
			{
				System.out.println(item.getAmount() + "  -  " + item.getProduct().getName());
				total = total.add(item.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
			}
			
			assertEquals(total, order.getTotal());
			customerOrderServices.submit(order.getVersion());
			System.out.println("DONE TEST LINEITEM");
			
		} catch (CustomerDoesNotExistException e) {
			fail("Customer does not exist.");
		} 
		catch (GeneralPersistenceException e) {
			fail("General Persistence Error");
		} catch (OrderNotOpenException e) {
			fail("Order Not Open");
		} catch (ProductDoesNotExistException e) {
			fail("No Such Product");
		} catch (InvalidQuantityException e) {
			e.printStackTrace(System.out);
			fail("Invlid Quantity");
		} catch (NoLineItemsException e) {
			fail("No Line Items");
		}
		catch (OrderModifiedException e) {
			fail("Order Modified");
		}
	}

	public void testSubmit() {
		//Order openOrder;
		try {
			try {
				customerOrderServices.submit(0);
				fail("Non Open Order Got Submitted");
			} catch (OrderNotOpenException e) {
				assertTrue("Correct Exception, No Open Order",true);
			} catch (NoLineItemsException e) {
				fail("Not correct Exception");
			}
			//openOrder = customerOrderServices.openOrder();
			//assertNotNull(openOrder);
			/*
			try {
				customerOrderServices.submit();
				fail("Order with No LineItems was submitted");
			} catch (OrderNotOpenException e) {
				
				fail("Not correct Exception");
			} catch (NoLineItemsException e) {
				assertTrue("Correct Exception, No LineItems",true);
			}
			*/
			LineItem newLine = new LineItem();
			newLine.setProductId(1);
			newLine.setQuantity(1);
			Order order = customerOrderServices.addLineItem(newLine);
			customerOrderServices.submit(order.getVersion());
			AbstractCustomer customer = customerOrderServices.loadCustomer();
			//Open Order Should be Null
			assertNull(customer.getOpenOrder());
			 
		} catch (CustomerDoesNotExistException e) {
			fail("Customer Does Not Exist");
		} catch (GeneralPersistenceException e) {
			fail("Persistence Failure");
		} catch (OrderNotOpenException e) {
			fail("Order Already Open");
		} catch (ProductDoesNotExistException e) {
			fail("No Such Product");
		} catch (InvalidQuantityException e) {
			fail("Not a Valid Quantity");
		} catch (NoLineItemsException e) {
			fail("No Line Items");
		}
		catch (OrderModifiedException e) {
			e.printStackTrace();
		}	
		
	}

	
	public void testRemoveLineItem() {
			try {
				
				try
				{
					customerOrderServices.removeLineItem(1,0);
					fail("Cannot remove Line from non open Order");
				}
				catch(OrderNotOpenException e)
				{
					assertTrue("Correct, Order Not Open",true);
				}
				/*
				Order openOrder = customerOrderServices.openOrder();
				
				try
				{
					customerOrderServices.removeLineItem(2, 1);
					assertTrue("Will let pass to avoid extra query",true);
				}
				catch(NoLineItemsException e)
				{
					assertTrue("Correct, Order Not Open",true);
				}
				*/
				LineItem newLine = new LineItem();
				newLine.setProductId(1);
				newLine.setQuantity(1);
				Order order = customerOrderServices.addLineItem(newLine);
				order = customerOrderServices.removeLineItem(1,order.getVersion());
				
				AbstractCustomer customer = customerOrderServices.loadCustomer();
				Set<LineItem> lineItems = customer.getOpenOrder().getLineitems();
				assertEquals(lineItems.size(), 0);
				
				try
				{
					customerOrderServices.removeLineItem( 333,order.getVersion());
					fail("Cannot remove product that does not exist");
				}
				catch (ProductDoesNotExistException e) {
					assertTrue("Correct Exception",true);
				}
				newLine.setVersion(order.getVersion());
				customerOrderServices.addLineItem(newLine);
				
				//Test Order Total
				order = customerOrderServices.loadCustomer().getOpenOrder();
				
				BigDecimal total = new BigDecimal(0);
				for(LineItem item:order.getLineitems())
				{
					total = total.add(item.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
				}

				
				assertEquals(total, order.getTotal());
				
				customerOrderServices.submit(order.getVersion());
				
			} catch (CustomerDoesNotExistException e) {
				fail("Customer Does Not Exist");
			}  catch (GeneralPersistenceException e) {
				fail("General Persistence");
			} catch (OrderNotOpenException e) {
				fail("Order Not OPen");
			} catch (ProductDoesNotExistException e) {
				fail("No Such Product");
			} catch (InvalidQuantityException e) {
				fail("Invalid Quantity");
			} catch (NoLineItemsException e) {
				fail("No Line Item");
			}
			catch (OrderModifiedException e) {
				e.printStackTrace(System.out);
				fail("Order Modified");
			}
			
	}

	public void testLoadCustomerHistory() throws CustomerDoesNotExistException, OrderAlreadyOpenException, GeneralPersistenceException, OrderNotOpenException, ProductDoesNotExistException, InvalidQuantityException, NoLineItemsException, OrderModifiedException
	{
		LineItem newLine = new LineItem();
		newLine.setProductId(1);
		newLine.setQuantity(1);
		Order openOrder = customerOrderServices.addLineItem( newLine);
		customerOrderServices.submit(openOrder.getVersion());
		
		Order openOrder2 =  customerOrderServices.addLineItem(newLine);
		customerOrderServices.submit(openOrder.getVersion());
		
		Set<Order> history = customerOrderServices.loadCustomerHistory();
		assertEquals(2,history.size());
		
		for (Order order:history)
		{
			if(order.getOrderId()== openOrder.getOrderId())
			{
				assertEquals(openOrder.getOrderId(), order.getOrderId());
				if(Order.Status.SUBMITTED == order.getStatus() || Order.Status.SHIPPED == order.getStatus())
						assertTrue(true);
				assertEquals(1, order.getLineitems().size());
			}
			else if(order.getOrderId()== openOrder2.getOrderId())
			{
				assertEquals(openOrder2.getOrderId(), order.getOrderId());
				if(Order.Status.SUBMITTED == order.getStatus() || Order.Status.SHIPPED == order.getStatus())
					assertTrue(true);
				assertEquals(1, order.getLineitems().size());
			}
			else
			{
				fail("order should not be here");
			}
		}
	}
	
	public void testUpdateAddress() throws CustomerDoesNotExistException, GeneralPersistenceException
	{
		Address address = new Address();
		address.setAddressLine1("333 3rd Street");
		address.setAddressLine2("Apt 3");
		address.setCity("West New York");
		address.setState("NJ");
		address.setCountry("USA");
		address.setZip("22344");
		
		customerOrderServices.updateAddress(address);
		
		AbstractCustomer customer = customerOrderServices.loadCustomer();
		Address updatedAddress = customer.getAddress();
		
		assertEquals(address.getAddressLine1(), updatedAddress.getAddressLine1());
		assertEquals(address.getAddressLine2(), updatedAddress.getAddressLine2());
		assertEquals(address.getCity(), updatedAddress.getCity());
		assertEquals(address.getState(), updatedAddress.getState());
		assertEquals(address.getCountry(), updatedAddress.getCountry());
		assertEquals(address.getZip(), updatedAddress.getZip());
	};
	

}
