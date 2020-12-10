// dojo.provide allows pages to use all of the types declared in this resource.
dojo.provide("depot.NavigationController");
dojo.require("dojo.back");

dojo.declare("StateObject",null,
		{
			thisKey:"",
			changeUrl:true,
			tab:null,
			constructor:function(thisKey)
			{
				this.thisKey = thisKey;
				var bookmark = this.thisKey || false;
				(bookmark)?this.changeUrl = bookmark + "Page":bookmark = false;
			},
			back:function ()
			{
				this.selectTab();
			},
			forward:function()
			{
				this.selectTab();
			},
			selectTab:function()
			{
				 dijit.byId("center").selectChild(dijit.byId(this.thisKey));
			}
		});

dojo.declare("depot.NavigationController", [], {
	defaultPage:"shop",
	tabContainerId:"center",
	loadedPage:function()
	{
		var anchor;
		(dojo.doc.location.hash && dojo.doc.location.hash.substring(1))?
			anchor=dojo.doc.location.hash.substring(1,dojo.doc.location.hash.length-4):
			anchor = null;
		if(anchor)
		{
			var content = dijit.byId(anchor);
			if(content)
			{
				this.defaultPage = anchor;
				dijit.byId(this.tabContainerId).selectChild(content);
			}
		}
		dojo.doc.location.hash = "#"+this.defaultPage+"Page";
		var state = new StateObject(this.defaultPage);
		dojo.back.setInitialState(state);
		dojo.subscribe(this.tabContainerId+"-selectChild",this,"addToHistory");
	},
	addToHistory:function(child)
	{
		var state = new StateObject(child.id);
		dojo.back.addToHistory(state);
		dojo.publish(child.id+"-select", [child.id]);
		return true;
	}		
});


