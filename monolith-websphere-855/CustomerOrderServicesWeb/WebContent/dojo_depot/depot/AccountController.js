dojo.provide("depot.AccountController");

dojo.declare("depot.AccountController",null,
{
	account:{},
	etag:"",
	ACCOUNT_LOAD:"AccountLoaded",
	loadAccount:function()
	{
		console.debug("Loading Account Controller");
		var getAccount = {
	        url: "/CustomerOrderServicesWeb/jaxrs/Customer",
	        handleAs: "json",
	        load: dojo.hitch(this,this.loadAccountSuccess),
	        error:dojo.hitch(this,this.loadAccountError)
		};
		
		return dojo.xhrGet(getAccount);
	},
	loadAccountSuccess:function(data,ioArgs)
	{
		console.debug("Account Controller Loaded",data);
		this.account = data;
		if(ioArgs.xhr.getResponseHeader("ETag"))
		{
			this.etag = ioArgs.xhr.getResponseHeader("ETag");
		};
		dojo.publish(this.ACCOUNT_LOAD,[this.account]);
		console.debug("Published");
	},
	loadAccountError:function(e)
	{
		console.error("Error Loading Account",e);
	}
});
