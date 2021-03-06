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
 
dojo.provide("sbt.connections.SearchConstants");
/**
 * Social Business Toolkit SDK.
 * Definition of constants for SearchService.
 */
define('sbt/connections/SearchConstants',[ "sbt/lang", "sbt/connections/ConnectionsConstants" ], function(lang,conn) {

    return lang.mixin(conn, {
    	
        /**
         * Namespaces to be used when reading the Search ATOM feed
         */
        SearchNamespaces : {
            a : "http://www.w3.org/2005/Atom",
            ibmsc : "http://www.ibm.com/search/content/2010",
            opensearch : "http://a9.com/-/spec/opensearch/1.1/",
            relevance : "http://a9.com/-/opensearch/extensions/relevance/1.0/",
            snx : "http://www.ibm.com/xmlns/prod/sn",
            spelling : "http://a9.com/-/opensearch/extensions/spelling/1.0/"
        },
        
        /**
         * XPath expressions used when parsing a Connections Search ATOM feed
         */
        SearchFeedXPath : conn.ConnectionsFeedXPath,

        /**
         * XPath expressions used when parsing a Connections Search facets feed
         * that only contains a single facet
         */
        SingleFacetXPath : {
            // used by getEntitiesDataArray
            entries : "/a:feed/ibmsc:facets/ibmsc:facet[@id='{facet.id}']/ibmsc:facetValue"
            // used by getSummary
            //totalResults : "",
            //startIndex : "",
            //itemsPerPage : ""
        },

        /**
         * XPath expressions used when parsing a Connections Search facet
         */
        FacetValueXPath : {
            // used by getEntityData
            entry : "/ibmsc:facetValue",
            // used by getEntityId
            uid : "@id",
            // used by getters
            id : "@id",
            label : "@label",
            weight : "@weight"
        },

        /**
         * XPath expressions to be used when reading a scope
         */
        ScopeXPath : lang.mixin({}, conn.AtomEntryXPath, {
            link : "link"
        }),
        
        /**
         * XPath expressions to be used when reading a search result
         */
        SearchXPath : lang.mixin({}, conn.AtomEntryXPath, {
            rank : "snx:rank",
            relevance : "relevance:score",
            type : "a:category[@scheme='http://www.ibm.com/xmlns/prod/sn/type']/@term",
            application : "a:category[@scheme='http://www.ibm.com/xmlns/prod/sn/component']/@term",
            applicationCount : "count(a:category[@scheme='http://www.ibm.com/xmlns/prod/sn/component']/@term)",
            primaryComponent : "a:category[ibmsc:field[@id='primaryComponent']]/@term",
            tags : "a:category[not(@scheme)]/@term",
            commentCount : "snx:rank[@scheme='http://www.ibm.com/xmlns/prod/sn/comment']",
            resultLink : "a:link[not(@rel)]/@href",
            bookmarkLink : "ibmsc:field[@id='dogearURL']",
            eventStartDate : "ibmsc:field[@id='eventStartDate']",
            authorJobTitle : "a:content/xhtml:div/xhtml:span/xhtml:div[@class='title']",
            authorJobLocation : "a:content/xhtml:div/xhtml:span/xhtml:div[@class='location']",
            authorCount : "count(a:contributor)",
            contributorCount : "count(a:author)",
            tagCount : "count(a:category[not(@scheme)])",
            highlightField : "ibmsc:field[@id='highlight']",
            fileExtension : "ibmsc:field[@id='fileExtension']",
            memberCount : "snx:membercount",
            communityUuid : "snx:communityUuid",
            containerType : "ibmsc:field[@id='container_type']",
            communityParentLink : "a:link[@rel='http://www.ibm.com/xmlns/prod/sn/container' and @type='text/html']/@href",
            parentageMetaID : "ibmsc:field[contains(@id, 'ID')]/@id",
            parentageMetaURL : "ibmsc:field[contains(@id, 'URL')]",
            parentageMetaURLID : "ibmsc:field[contains(@id, 'URL')]/@id",
            objectRefDisplayName : "ibmsc:field[@id='FIELD_OBJECT_REF_DISPLAY_NAME']",
            objectRefUrl : "ibmsc:field[@id='FIELD_OBJECT_REF_URL']",
            accessControl : "a:category[@scheme='http://www.ibm.com/xmlns/prod/sn/accesscontrolled']/@term",
            commentsSummary : "ibmsc:field[@id='commentsSummary']"
        }),
        
		/**
         * Returns the set of supported values that can be passed to the "scope" parameter of the Search API. 
         * Scopes relating to Connections applications that have not been installed will not be returned.
         */
        AtomScopes : "/${search}/atom/scopes",
        
        /**
         * API call for searching IBM Connections to find content that, for example, contains a specific text 
         * string in its title or content, or is tagged with a specific tag.
         */
        AtomSearch : "/${search}/atom/search",
        
        /**
         * API call for searching IBM Connections to find content that, for example, contains a specific text 
         * string in its title or content, or is tagged with a specific tag.
         */
        AtomMySearch : "/${search}/atom/mysearch",
        
		/**
         * These API's are all deprecated
         */
        publicSearch : "/${search}/atom/search/results",
        mySearch : "/${search}/atom/mysearch/results",
        peopleSearch : "/${search}/atom/search/facets/people",
        myPeopleSearch : "/${search}/atom/mysearch/facets/people",
        tagsSearch : "/${search}/atom/search/facets/tags",
        myTagsSearch : "/${search}/atom/mysearch/facets/tags",
        dateSearch : "/${search}/atom/search/facets/date",
        myDateSearch : "/${search}/atom/mysearch/facets/date",
        sourceSearch : "/${search}/atom/search/facets/source",
        mySourceSearch : "/${search}/atom/mysearch/facets/source"
        

    });
});
