// dojo.provide allows pages to use all of the types declared in this resource.
dojo.provide("depot.AddressController");

dojo.require("dijit.form.Form");
dojo.require("dijit.TitlePane");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojox.form.BusyButton"); 

dojo.declare("depot.AddressController", [], {
	constructor:function()
	{
		console.debug("Address Controller");
		dojo.subscribe(accountController.ACCOUNT_LOAD, this, this.setEvents);
		dojo.subscribe(accountController.ACCOUNT_LOAD, this, this.setAddress);
		dojo.connect(dijit.byId("account"),"onLoad",this,this.setAddress);
		dojo.connect(dijit.byId("account"),"onLoad", this, this.setEvents);
		dojo.query(".formValue","addressForm").forEach(dojo.hitch(this, function (item)
		{
			dojo.connect(item,"onchange",this, this.isValid);
			dojo.connect(item,"onkeyup",this, this.isValid);
		}
		));
	},
	setEvents:function()
	{
		dojo.connect(dijit.byId("updateAddressButton"),"onClick",this,this.updateAddress);
	},
	setAddress:function()
	{
		console.debug("setting address");
		if(accountController.account && accountController.account.address)
		{
			console.dir(accountController.account.address);
			dijit.byId("addressForm").set("value",accountController.account.address);
			console.debug("address Set");
		}
	},
	isValid:function()
	{
		if(dijit.byId("addressForm").isValid())
		{
			dijit.byId("updateAddressButton").set("disabled",false);
			return true;
		}
		else
		{
			dijit.byId("updateAddressButton").set("disabled",true);
			return false;
		}
	},
	updateAddress:function()
	{
		console.debug("Updating Address");
		dojo.query("input").forEach(dojo.hitch(this, function (item)
		{
			item.disabled=true;
		}));
		dijit.byId("updateAddressButton").makeBusy();
		
		var updateAddress = {
	        url: "jaxrs/Customer/Address",
	        headers:{"Content-Type":"application/json"},
	        putData:dojo.toJson(dijit.byId("addressForm").get("value")),
	        load: dojo.hitch(this,this.updateAddressSuccess),
	        error:dojo.hitch(this,this.updateAddressError)
		};
		dojo.xhrPut(updateAddress);
	},
	updateAddressSuccess:function(data)
	{
		console.info("Update Success");
		accountController.account.address = dijit.byId("addressForm").get("value");
		dojo.query("input").forEach(dojo.hitch(this, function (item)
		{
			item.disabled=true;
		}));
		dijit.byId("updateAddressButton").cancel();
		dojo.place("<div class='successMessage'>Address Update Succesful</div>","result","only");
	},
	updateAddressError:function(e)
	{
		console.error("Error Loading Account",e);
		dojo.query("input").forEach(dojo.hitch(this, function (item)
		{
			item.disabled=false;
		}));
		dijit.byId("updateAddressButton").cancel();
		dojo.place("<div class='errorMessage'>Address Update Failed</div>","result","only");
	}
});