dojo.provide("depot.ProductController");
dojo.require("dijit.Menu");
dojo.require("dijit.MenuItem");
dojo.require("dijit.PopupMenuItem");
dojo.require("dijit.MenuSeparator");
dojo.require("dojox.data.JsonRestStore");
dojo.require("dojox.grid.DataGrid");
dojo.require("dijit.Tree");
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dojo.dnd.common");
dojo.require("dojo.dnd.Source");
dojo.require("dijit.Dialog");
dojo.require("dojox.dtl");
dojo.require("dojox.dtl.Context");
dojo.registerModulePath("product","../../product");


dojo.declare("depot.ProductController",null,
{
	defaultOrder:null,
	constructor:function()
	{
		this.defaultOrder = 
		{
			identifier:'productId',
			label:'product.name',
			items:[]
		};
	},
	loadCatalog:function()
	{
		var getAccount = {
			url: "jaxrs/Category",
			handleAs: "json",
			load: dojo.hitch(this,this.loadCatalogSuccess),
			error:dojo.hitch(this,this.loadCatalogError)
		};
		
		dojo.xhrGet(getAccount);
		this.cartPreview();
		dojo.connect(dijit.byId("productGrid"), "onRowClick", this,this.dragAndDrop);
		dojo.subscribe("/dnd/drop", this, this.addToCart);
	},
	loadCatalogSuccess:function(data,ioArgs)
	{
		var menu = dijit.byId("categoryMenu");
		dojo.forEach(data,dojo.hitch(this, function(item)
		{
			if(item.subCategories)
			{
				var popMenu =  new dijit.Menu({parentMenu:menu});
				dojo.addClass(popMenu,"outer");
				dojo.forEach(item.subCategories, dojo.hitch(this,function(subItem)
				{
					var mItem = new dijit.MenuItem({label:subItem.name,title:subItem.id});
					dojo.connect(mItem,"onClick",this,this.selectCategory);
					popMenu.addChild(mItem);
				}));
				var pItem = new dijit.PopupMenuItem({label:item.name,popup:popMenu});
				menu.addChild(pItem);
			}
		}));
	},
	loadCatalogError:function(e)
	{
		console.error("Error Loading Categories",e);
	},
	formatImage:function(item)
	{
		return dojo.replace("<img  src='{image}' height='100px' width='100px'></img>",{image:item});
	},
	combineData:function(index,item)
	{
		return item;	
	},	
	formatData:function(item)
	{
		return dojo.replace("<div class='productTitle'>{name}</div><div>{price}</div>",item);
	},
	selectCategory:function(event)
	{
		var grid = dijit.byId("productGrid");
		grid.setQuery({categoryId:event.target.parentNode.title});
		dojo.place("<div>"+event.target.innerHTML+"</div>","catHeader","only");
	},
	cartPreview:function()
	{
		if(accountController.account.openOrder)
		{
			this.defaultOrder.items = accountController.account.openOrder.lineitems;
			dojo.forEach(this.defaultOrder.items, function(item)
			{
				try
				{
					previewStore.newItem(item);
				}
				catch(e)
				{
					previewStore.fetchItemByIdentity({identity:item.productId,onItem:function(sItem)
					{
						previewStore.setValue(sItem,"quantity",item.quantity);
					}
					});
				}
			});
				previewStore.save();
				dojo.place("<div>Order Total: "+accountController.account.openOrder.total+"</div>","orderTotal","only");
		}
	},
	dragAndDrop:function()
	{
		var detailDialog = dijit.byId("detailDialog");
		var grid = dijit.byId("productGrid");
		console.debug(arguments[0]);
		var item = grid.getItem(arguments[0].rowIndex);
		console.debug(item);
		detailDialog.set("title",item.name);
		var template = new dojox.dtl.Template(dojo.moduleUrl("product","productDetail.html"));
		var context = new dojox.dtl.Context(item);
		dijit.byId("detailDialogPane").set("content",template.render(context));
		dojo.query(".progressSection").style({display:"none"});
		dojo.query(".cartSection").style({display:"block"});
		dojo.query(".productSection").style({display:"block"});
		detailDialog.show();
	},
	addToCart:function(data)
	{
		var grid = dijit.byId("productGrid");
		console.debug(grid);
		var selected = grid.selection.getSelected();
		console.debug("Adding to Cart",selected);
		console.debug(selected[0].id);
		dojo.query(".progressSection").style({display:"block"});
		dojo.query(".cartSection").style({display:"none"});
		dojo.query(".productSection").style({display:"none"});
		var addLineItem = {
		        url: "jaxrs/Customer/OpenOrder/LineItem",
		        headers:{"Content-Type":"application/json","If-Match":accountController.etag},
		        postData:dojo.toJson({quantity:1,productId:selected[0].id}),
		        handleAs:"json",
		        load: dojo.hitch(this,this.addToCartSuccess),
		        error:dojo.hitch(this,this.addToCartError)
			};
			dojo.xhrPost(addLineItem);
	},
	addToCartSuccess:function(data,ioArgs)
	{
		accountController.account.openOrder=data;
		this.defaultOrder.items = [];
		accountController.etag = ioArgs.xhr.getResponseHeader("ETag");
		console.debug("Restting preview store");
		this.cartPreview();
		var detailDialog = dijit.byId("detailDialog");
		detailDialog.hide();
	},
	addToCartError:function(error)
	{
		console.debug(error);
	},
	removeItem:function(e)
	{
		console.info("remove item not implemented",e);
	},
	updateItem:function(e)
	{
		console.info("update quantity not implemented",e);
	}
});