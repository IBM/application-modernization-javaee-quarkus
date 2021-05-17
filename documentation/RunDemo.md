## Event Driven Architecture

This page describes how to run the demo which uses events to send changed price information from the catalog service to the remaining monolith.

Follow the instructions how to set up the application either in Docker Desktop or on OpenShift.

Once everything has been started, you can open the web applications:

* Legacy Dojo frontend: http://localhost/CustomerOrderServicesWeb
* Modern micro-frontend based application: http://localhost:8080

Add the item "Return of the Jedi" to the shopping cart via drag and drop.

<kbd><img src="storefront-add-item.png" /></kbd>

Update the price of this item:

```
$ curl -X PUT "http://localhost/CustomerOrderServicesWeb/jaxrs/Product/1" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\":1, \"price\":50}"
```

Open the "Order History" tab to see the updated price. The new price has been updated in the catalog service and the remaining monolith.

<kbd><img src="storefront-new-price.png" /></kbd>