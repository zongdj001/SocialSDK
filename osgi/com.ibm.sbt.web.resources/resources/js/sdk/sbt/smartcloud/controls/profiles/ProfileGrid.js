/*
 * © Copyright IBM Corp. 2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

dojo.provide("sbt.smartcloud.controls.profiles.ProfileGrid");

/**
 * 
 * @module sbt.smartcloud.controls.profiles.ProfileGrid
 */
define([ "../../../declare", 
         "../../../lang",
		 "../../../config",
		 "../../../controls/grid/Grid", 
		 "./ProfileGridRenderer", 
		 "./ProfileAction", 
		 "../../../store/parameter",
		 "../../ProfileConstants",
		 "../../CommunityConstants"], 
    function(declare, lang, sbt, Grid, ProfileGridRenderer, ProfileAction, parameter, ProfileConstants,CommunityConstants) {

	var ProfileXPath = {
        id : "o:person/o:id",
	 	entry : "/o:response/o:entry",
		uid : "o:person/o:id",
		name : "o:person/o:displayName",
		email : "o:person/o:emailAddress",
		title : "o:person/o:jobtitle",
		profileUrl : "o:person/o:profileUrl",
		orgName : "o:person/o:org/o:name",
		address : "o:person/o:address",
		phoneNumbers : "o:person/o:phoneNumbers/o:phone",
		photos : "o:person/o:photos/o:value"
	};
	
	var FeedXPath = {
        "entry" : "/o:response/o:entry",
        "entries" : "/o:response/o:entry",
        "totalResults" : "/o:response/o:totalResults",
        "startIndex" : "/o:response/o:startIndex",
        "itemsPerPage" : "/o:response/o:itemsPerPage"
	};
	
	var Namespaces = {
		o : "http://ns.opensocial.org/2008/opensocial"
	};
	
	var ParamSchema = {
        startIndex : parameter.zeroBasedInteger("startIndex"),
        pageSize : parameter.oneBasedInteger("count")
    };
	
    /**
     * @class ProfileGrid
     * @namespace sbt.connections.controls.profiles
     */
    var ProfileGrid = declare(Grid, {
   
    	/**
    	 * @param options, This is a list of all
    	 * the different types of profile grids available.
    	 * Depending on which one is selected specific arguments will be given to
    	 * the atom store and grid renderer.
    	 */
        options : {
            "contacts" : {
                storeArgs : {
                    url : ProfileConstants.GetMyContacts,
                    attributes : ProfileXPath,
                    paramSchema : ParamSchema,
                    feedXPath : FeedXPath,
                    namespaces : Namespaces
                },
                rendererArgs : {
                    type : "contacts"
                }
            },
        	"friends" : {
	            storeArgs : {
	                url : ProfileConstants.GetMyConnections,
	                attributes : ProfileXPath,
	                paramSchema : ParamSchema,
	                feedXPath : FeedXPath,
	                namespaces : Namespaces
	            },
	            rendererArgs : {
	                type : "friends"
	            }
	        },
	        "communityMembers" : {
	            storeArgs : {
	                url : CommunityConstants.AtomCommunityMembers,
	                attributes : CommunityConstants.MemberXPath,
	                paramSchema : ParamSchema,
	                feedXPath : CommunityConstants.CommunityFeedXPath,
	                namespaces: CommunityConstants.CommunityNamespaces
	            },
	            rendererArgs : {
	                type : "communityMembers"
	            }
	        }
        },
        
        /**
         * Endpoint to use, default is 'smartcloud'
         */
    	endpoint: "smartcloud",
    	
        /**
         * A profile action, defines default behaviour for when 
         * items in the grid are clicked on or hovered on,
         * it is possible to override these actions
         */
        profileAction: new ProfileAction(),
        
        /**
         * This is the default grid that will be created if no 
         * arguments are given.
         */
        defaultOption: "contacts",
        
        /**
         * Constructor function
         * @method constructor
         */
        constructor: function(args){
        },
        
        buildUrl: function(url, args, endpoint){
        		var params;
				if (this.communityUuid) {
					params = {communityUuid: this.communityUuid};
				}
        	
        	return this.constructUrl(url, params, {},endpoint);
        },
        
        /**
         * Creates a renderer for the grid.The renderer is responsible for 
         * loading the grid's HTML content.
         * @method createDefaultRenderer
         * @param args sets the template the renderer will use, by checking args.type, but for
         * profile grids this will always be "profile"
         * @returns an instance of a  profile gird renderer.
         */
        createDefaultRenderer : function(args) {
            return new ProfileGridRenderer(args);
        },
        
        postCreate: function(){
        	this.inherited(arguments);  
        	if(this.type === "communityMembers"){
        		if(!arguments.communityUuid){
        			this._updateWithError({message:this.renderer._nls.communityIdError});
	
        		}
        	}
        },
        
        /**
         * In the grid HTML an element can have an event attached 
         * using dojo-attach-event="onClick: handleClick".
         * This method is the handler for the onclick event.
         * @method handleClick
         * @param el the element that fired the event
         * @param data all of the items from the current row of the grid. 
         * @param ev the event 
         */
        handleClick: function(el, data, ev) {
            if (this.profileAction) {
            	console.log(data);
                this.stopEvent(ev);
                
                this.profileAction.execute(data, { grid : this.grid }, ev);
            }
        },        

        // Internals
        
        /*
         * Override to change count if needed
         */
        _doQuery: function(store, options) {
        	// OpenSocial API doesn't like requests for more than is available
        	if (options && options.total) {
        		options.count = Math.min(options.count, options.total - options.start);
        	}
        	
        	this.inherited(arguments);
        }
        
    });

    return ProfileGrid;
});