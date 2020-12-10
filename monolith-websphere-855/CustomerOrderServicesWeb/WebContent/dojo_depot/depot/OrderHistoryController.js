dojo.provide("depot.OrderHistoryController");

dojo.require("dojox.grid.DataGrid");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojox.dtl");
dojo.require("dojox.dtl.Context");


dojo.declare("depot.OrderHistoryController",null,
{
	format:null,
	orderStore:null,
	template:null,
	constructor:function()
	{
		this.format = {
				id:"orderId",
				items:[]
		};
		
		this.orderStore = new dojo.data.ItemFileReadStore({data:this.format,clearOnClose:true});
		dojo.connect(dijit.byId("orderHistory"),"onLoad",this,this.getOrders);
		dojo.subscribe("orderHistory-select",this,this.getOrders);
	},
	getOrders:function()
	{
		console.debug("Getting Orders");
		var getOrdersXhr = {
				url: "/CustomerOrderServicesWeb/jaxrs/Customer/Orders",
				handleAs: "json",
				load: dojo.hitch(this,this.loadOrdersSuccess),
				error:dojo.hitch(this,this.loadOrdersError)
			};
		return dojo.xhrGet(getOrdersXhr);
	},
	loadOrdersSuccess:function(data)
	{
		console.debug("Order History",data);
		this.format.items = data;
		this.orderStore.data = this.format;
		this.orderStore.close();
		console.debug(dijit.byId("orderHistoryGrid"));
		dijit.byId("orderHistoryGrid").setStore(this.orderStore);
		
	},
	loadOrdersError:function(error)
	{
		console.error(error);
	},
	formatStatus:function(status)
	{
		console.debug("format status",status);
		return (status && status=="OPEN")?"Current Order":status;
	},
	getLineItems:function(i,item)
	{
		console.debug("getLineItem,",item);
		
		var li = [];
		if(item)
		{
			dojo.forEach(item.lineitems,function(litem,i)
			{
				console.debug(litem,i);
				li[i] = litem;
			});
		}
		console.debug("li",li);
		return li;
	},
	formatLineItems:function(lineItem)
	{
		console.debug("format",lineItem);
		if(lineItem)
		{
			if(!this.template)
			{
				this.template = new dojox.dtl.Template(dojo.moduleUrl("orderHistory","orderHistoryTemplate.html"));
			}
			var context = new dojox.dtl.Context({lis:lineItem});
			var result = this.template.render(context);
			return result;
		}
	}
	
});
