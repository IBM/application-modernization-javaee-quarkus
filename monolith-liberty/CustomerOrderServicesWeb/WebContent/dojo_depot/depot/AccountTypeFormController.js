dojo.provide("depot.AccountTypeFormController");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dojox.form.BusyButton");
dojo.require("dojox.dtl");
dojo.require("dojox.dtl.Context");
dojo.require("dijit.form.NumberSpinner");




dojo.declare("depot.AccountTypeFormController",null,
{
	template:null,
	constructor:function()
	{
		this.getMetaData();
	},
	getMetaData:function()
	{
		var getAccountType = {
		        url: "jaxrs/Customer/TypeForm",
		        handleAs: "json",
		        load: dojo.hitch(this,this.setAccount),
		        error:dojo.hitch(this,this.loadAccountTypeError)
			};
			
			dojo.xhrGet(getAccountType);
	},
	setAccount:function(data)
	{
		if(!this.template)
		{
			this.template = new dojox.dtl.Template(dojo.moduleUrl("account","accountType.html"));
		}
		var context = new dojox.dtl.Context({group:data});
		dijit.byId("typeFormTile").set("content",this.template.render(context));
		dijit.byId("typeFormTile").set("title",data.label);
		if(accountController.account)
		{
			dijit.byId("typeForm").set("value",accountController.account);
		}
		dojo.connect(dijit.byId("updateTypeButton"),"onClick",this,this.updateAccountType);
		dojo.query(".formValue","typeForm").forEach(dojo.hitch(this, function (item)
		{
			dojo.connect(item,"onchange",this, this.isValid);
			dojo.connect(item,"onkeyup",this, this.isValid);
		}));
	},
	loadAccountTypeError:function(error)
	{
		console.error(error);
	},
	updateAccountType:function()
	{
		
		dijit.byId("updateTypeButton").makeBusy();
		var data = {"type":accountController.account.type};
		
		data = dojo.mixin(data,dijit.byId("typeForm").get("value"));
		var updateType = {
		        url: "jaxrs/Customer/Info",
		        headers:{"Content-Type":"application/json"},
		        postData:dojo.toJson(data),
		        load: dojo.hitch(this,this.updateTypeSuccess),
		        error:dojo.hitch(this,this.updateTypeError)
			};
		
		dojo.xhrPost(updateType);
	},
	updateTypeSuccess:function(data)
	{
		dijit.byId("updateTypeButton").cancel();
		dojo.place("<div class='successMessage'>Update Succesful</div>","resultType","only");
	},
	updateTypeError:function(error)
	{
		console.error(error);
		dijit.byId("updateTypeButton").cancel();
		dojo.place("<div class='errorMessage'>Update Failed</div>","resultType","only");
	},
	isValid:function()
	{
		if(dijit.byId("typeForm").isValid())
		{
			dijit.byId("updateTypeButton").set("disabled",false);
			return true;
		}
		else
		{
			dijit.byId("updateTypeButton").set("disabled",true);
			return false;
		}
	}
});