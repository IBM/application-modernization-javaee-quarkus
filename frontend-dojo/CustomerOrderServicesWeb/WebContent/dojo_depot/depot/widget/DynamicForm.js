dojo.provide("depot.widget.DynamicForm");

dojo.require("dojox.dtl._Templated");
dojo.require("dijit.form.Form");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dojox.form.BusyButton");
dojo.require("dojox.dtl");
dojo.require("dojox.dtl.Context");
dojo.require("dijit.form.NumberSpinner");

dojo.declare("depot.widget.DynamicForm",[dijit._Widget, dojox.dtl._Templated] ,
{
	
	widgetsInTemplate:true,
	refreshTopic:"",
	updateTopic:"formUpdate",
	metaUrl:"",
	updateUrl:"",
	typeForm:"",
	group:"",
	dynaRoot:"",
	templatePath:dojo.moduleUrl("depot","widget/template/dynamicForm.html"),
	typeFormTile:"",
	updateTypeButton:"",
	formData:"",
	extraData:"",
	resultType:"",
	postMixInProperties:function()
	{
		if(this.formData)
		{
			this.formData = eval(this.formData);
		}
		dojo.subscribe(this.refreshTopic, this, this.populateForm);
		this.inherited(arguments);
	},
	startup:function()
	{
		var getAccountType = {
		        url: this.metaUrl,
		        handleAs: "json",
		        load: dojo.hitch(this,this.setMeta),
		        error:dojo.hitch(this,this.loadMetaError)
			};
			
			dojo.xhrGet(getAccountType);
			this.inherited(arguments);
	},
	setMeta:function(data)
	{
		this.group = data;
		this.render();
		this.typeFormTile.set("title",data.label);
		if(this.formData)
		{
			this.typeForm.set("value",this.formData);
		}
	},
	loadMetaError:function(error)
	{
		console.error(error);
	},
	populateForm:function(formData)
	{
		console.debug("Populating form data called",formData);
		if(formData)
		{
			this.formData = formData;
			this.typeForm.set("value",formData);
			console.debug("Populated Form",formData);
		}
	},
	updateForm:function()
	{
		this.updateTypeButton.makeBusy();
		console.debug("Updating",this.extraData);
		data = dojo.mixin(eval(this.extraData),this.typeForm.get("value"));
		var updateType = {
		        url: this.updateUrl,
		        headers:{"Content-Type":"application/json"},
		        postData:dojo.toJson(data),
		        load: dojo.hitch(this,this.updateFormSuccess),
		        error:dojo.hitch(this,this.updateFormError)
			};
		
		dojo.xhrPost(updateType);
	},
	updateFormSuccess:function(data)
	{
		this.updateTypeButton.cancel();
		dojo.place("<div class='successMessage'>Update Succesful</div>",this.resultType,"only");
		dojo.publish(this.updateTopic,[this.typeForm.get("value")]);
	},
	updateFormError:function(error)
	{
		console.error(error);
		this.updateTypeButton.cancel();
		dojo.place("<div class='errorMessage'>Update Failed</div>",this.resultType,"only");
	},
	isValid:function()
	{
		console.debug("isValid");
		if(this.typeForm.isValid())
		{
			this.updateTypeButton.set("disabled",false);
			return true;
		}
		else
		{
			this.updateTypeButton.set("disabled",true);
			return false;
		}
	}
});
