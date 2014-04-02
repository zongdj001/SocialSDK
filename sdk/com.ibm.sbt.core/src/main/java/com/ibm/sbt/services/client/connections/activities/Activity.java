/*
 * � Copyright IBM Corp. 2014
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
package com.ibm.sbt.services.client.connections.activities;

import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;

import com.ibm.commons.util.StringUtil;
import com.ibm.commons.xml.NamespaceContext;
import com.ibm.commons.xml.xpath.XPathExpression;
import com.ibm.sbt.services.client.ClientServicesException;
import com.ibm.sbt.services.client.base.AtomEntity;
import com.ibm.sbt.services.client.base.BaseService;
import com.ibm.sbt.services.client.base.ConnectionsConstants;
import com.ibm.sbt.services.client.connections.activities.serializers.ActivitySerializer;
import com.ibm.sbt.services.client.connections.common.Member;
import com.ibm.sbt.services.client.connections.common.Person;

/**
 * @author mwallace
 *
 */
public class Activity extends AtomEntity {

	static final String UUID_PREFIX = "urn:lsid:ibm.com:oa:"; ////$NON-NLS-1$
	
	/*
	 * Specifies the starting page of a template. The term attribute identifies the default view to use. 
	 */
	static final public String RECENT = "recent"; //$NON-NLS-1$
	static final public String OUTLINE = "outline"; //$NON-NLS-1$
	static final public String TODO = "todo"; //$NON-NLS-1$
	
	/**
	 * Default constructor
	 */
	public Activity() {
	}

	/**
	 * Construct Activity based on the specified node
	 * 
	 * @param service
	 * @param node
	 * @param nameSpaceCtx
	 * @param xpath
	 */
	public Activity(BaseService service, Node node, NamespaceContext namespaceCtx, XPathExpression xpathExpression) {
		super(service, node, namespaceCtx, xpathExpression);
	}
	
	/**
	 * Return the associated ActivityService.
	 * 
	 * @return
	 */
	public ActivityService getActivityService() {
		return (ActivityService)getService();
	}
	
	/**
	 * 
	 */
	@Override
	public String createEntryData() {
		ActivitySerializer serializer = new ActivitySerializer(this);
		return serializer.generateCreate();
	}

	/**
	 * Returns the activity ID.
	 * 
	 * @return activityUuid
	 */
	public String getActivityUuid() {
		return getAsString(ActivityXPath.activity);
	}
	
	/**
	 * Returns the activity collection title.
	 * 
	 * @return collection title
	 */
	public String getCollectionTitle() {
		return getAsString(ActivityXPath.collectionTitle);
	}
	
	/**
	 * Returns the activity node urls.
	 * 
	 * @return activity nodel urls
	 */
	public String[] getActivityNodeUrls() {
		return getAsArray(ActivityXPath.collectionCategoryHrefs);
	}
	
	/**
	 * Returns a URI from which you can retrieve the access control list of an activity. 
	 * 
	 * Use this URI to view or update the list of activity members and change their levels of access to the activity.
	 * 
	 * @return
	 */
	public String getMemberListUrl() {
		return getAsString(ActivityXPath.memberListHref);
	}
	
	/**
	 * Returns the community ID to which a community activity belongs.
	 * 
	 * @return communityUuid
	 */
	public String getCommunityUuid() {
		return getAsString(ActivityXPath.communityUuid);
	}
	
	/**
	 * Returns permissions.
	 * 
	 * @return permissions
	 */
	public String getPermissions() {
		return getAsString(ActivityXPath.permissions);
	}
	
	/**
	 * Returns link to an icon that depicts the status of an activity.
	 * 
	 * @return icon
	 */
	public String getIcon() {
		return getAsString(ActivityXPath.icon);
	}
	
	/**
	 * Returns the them id for the activity.
	 * 
	 * @return theme id
	 */
	public String getThemeId() {
		return getAsString(ActivityXPath.themeId);
	}
	
	/**
	 * Returns the date on which the activity is due to be completed.
	 * 
	 * @return due date
	 */
	public Date getDuedate() {
		return getAsDate(ActivityXPath.duedate);
	}
	
	/**
	 * Set the date on which the activity is due to be completed.
	 * 
	 * @param due date
	 */
	public void setDuedate(Date duedate) {
		setAsDate(ActivityXPath.duedate, duedate);
	}
	
	/**
	 * Returns the depth of the activity.
	 * 
	 * @return depth
	 */
	public int getDepth() {
		return getAsInt(ActivityXPath.depth);
	}
	
	/**
	 * Returns the position of the activity.
	 * 
	 * @return position
	 */
	public long getPosition() {
		return getAsLong(ActivityXPath.position);
	}
	
	/**
	 * 
	 * @return boolean indicating whether or not this activity is a community activity.
	 */
	public boolean isCommunityActivity() {
		return StringUtil.isNotEmpty(getCommunityUuid());
	}

	/**
	 * Returns true if the activity is deleted.
	 * 
	 * Flag that is only present on an activity that is deleted, meaning it is in the Trash view and has not been removed from the system.
	 * 
	 * @return
	 */
	public boolean isDeleted() {
		return exists(ActivityXPath.deleted);
	}
	
	/**
	 * Set deleted flag for the activity.
	 * 
	 * @param deleted
	 */
	public void setDeleted(boolean deleted) {
		setAsBoolean(ActivityXPath.deleted, deleted);
	}
	
	/**
	 * Returns the default view of an activity template.
	 * 
	 * Specifies the starting page of a template. The term attribute identifies the default view to use. 
	 * Options are: 
	 * recent
	 * outline. This is the default value.
	 * todo
	 *  
	 * @return defaultView
	 */
	public String getDefaultView() {
		return getAsString(ActivityXPath.defaultView);
	}
	
	/**
	 * Sets the default view of an activity template.
	 * 
	 * Specifies the starting page of a template. The term attribute identifies the default view to use. 
	 * Options are: 
	 * recent
	 * outline. This is the default value.
	 * todo
	 *  
	 * @param defaultView
	 */
	public void setDefaultView(String defaultView) {
		setAsString(ActivityXPath.defaultView, defaultView);
	}
		
	/**
	 * Returns the priority of the activity.
	 * 
	 * Identifies the priority of the activity. Options are High, Medium, or Normal. 
	 * Prioritization settings are not global, but are unique to each user; no other members can see these collections.
	 *  
	 * @return priority
	 */
	public Priority getPriority() {
		if (getDataHandler() == null) {
			return null;
		}
		return new Priority(getService(), (Node)getDataHandler().getData(), 
				ConnectionsConstants.nameSpaceCtx, ActivityXPath.priority.getPath());
	}
	
	/**
	 * Set the priority of the activity.
	 * 
	 * Identifies the priority of the activity. Options are High, Medium, or Normal. 
	 * Prioritization settings are not global, but are unique to each user; no other members can see these collections.
	 *  
	 * @param priority
	 */
	public void setPriority(String priority) {
		setAsString(ActivityXPath.priority, priority);
	}
	
	/**
	 * Returns true if the activity is external.
	 * 
	 * @return
	 */
	public boolean isExternal() {
		return exists(ActivityXPath.external);
	}
	
	/**
	 * Set flag to indicate this activity is external.
	 * 
	 * @param external
	 */
	public void setExternal(boolean external) {
		setAsBoolean(ActivityXPath.external, external);
	}
	
	/**
	 * Returns true if the activity is completed.
	 * 
	 * Flag that identifies a completed activity. To complete an activity, add this flag. 
	 * If it is not present, the activity is not completed.
	 * 
	 * @return
	 */
	public boolean isCompleted() {
		return exists(ActivityXPath.completed);
	}
	
	/**
	 * Set flag to indicate this activity is completed.
	 * 
	 * @param completed
	 */
	public void setCompleted(boolean external) {
		setAsBoolean(ActivityXPath.completed, external);
	}
	
	/**
	 * Returns true if the activity is a template.
	 * 
	 * Flag that is only present on an activity that is a template for creating other activities. 
	 * Add this flag to make an activity appear as a template. 
	 * The API does not provide any applications that use activity templates.
	 * 
	 * @return
	 */
	public boolean isTemplate() {
		return exists(ActivityXPath.template);
	}
	
	/**
	 * Set flag to indicate this activity is a template.
	 * 
	 * @param template
	 */
	public void setTemplate(boolean external) {
		setAsBoolean(ActivityXPath.template, external);
	}

	/**
	 * 
	 * @return
	 */
	public String getInReplyTo() {
		return getAsString(ActivityXPath.in_reply_to);
	}
	
	/**
	 * 
	 * @return
	 */
	public void setInReplyTo(String inReplyTo) {
		setAsString(ActivityXPath.in_reply_to, inReplyTo);
	}
	
	/**
	 * 
	 * @return
	 */
	public Person getAssignedTo() {
		return null;
    	//return new Person(getService(), new XmlDataHandler((Node)this.getDataHandler().getData(), 
    	//		ConnectionsConstants.nameSpaceCtx, (XPathExpression)ActivityXPath.assignedto.getPath()));		
	}
	
	/**
	 * 
	 * @param person
	 */
	public void setAssignedTo(Person person) {
		//setAsObject(ActivityXPath.assignedto, person);
	}
	
	/**
	 * Returns the tags on the wiki page.
	 * 
	 * @return
	 */
	public List<String> getTags() {
		return super.getBaseTags();
	}

	/**
	 * Set the tags on the wiki page.
	 * 
	 * @param tags
	 */
	public void setTags(List<String> tags) {
		super.setBaseTags(tags);
	}
	
	//------------------------------------------------------------------------------------------------------------------
	// Working with activities programmatically.
	//------------------------------------------------------------------------------------------------------------------
	
	/**
	 * To delete an existing activity, use the HTTP DELETE method.
	 * Deleted activities are moved to the trash collection and can be restored.  
	 * 
	 * @return
	 * @throws ClientServicesException 
	 * @throws {@link NullPointerException} If there is no service associated with this Activity
	 */
	public void delete() throws ClientServicesException {
		ActivityService service = (ActivityService)getService();
		service.deleteActivity(this);
	}
	
	//------------------------------------------------------------------------------------------------------------------
	// Working with activity fields programmatically.
	//------------------------------------------------------------------------------------------------------------------
	
	public Field[] getFields() {
		return null;
	}
	
	public Field getField(String fid) {
		return null;
	}
	
	public Field addField(Field field) {
		return field;
	}
	
	public Field updateField(Field field) {
		return field;
	}
	
	public String deleteField(Field field) {
		return deleteField(field.getFid());
	}
	
	public String deleteField(String fid) {
		return fid;
	}
	
	//------------------------------------------------------------------------------------------------------------------
	// Working with activity members programmatically.
	//------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Add an activity member.
	 * 
	 * @param member
	 * @return
	 */
	public Member addMember(Member member) throws ClientServicesException {
		ActivityService activityService = getActivityService();
		if (activityService == null) {
			throw new ClientServicesException(null, "No activity service associated with this activity.");
		}
		return activityService.addMember(this, member);
	}
	
	/**
	 * Retrieve an activity member.
	 * 
	 * @return
	 */
	public Member getMember(String memberId) throws ClientServicesException {
		ActivityService activityService = getActivityService();
		if (activityService == null) {
			throw new ClientServicesException(null, "No activity service associated with this activity.");
		}
		return activityService.getMember(this, memberId);
	}
	
	/**
	 * Update an activity member.
	 * 
	 * @param member
	 * @return
	 */
	public Member updateMember(Member member) throws ClientServicesException {
		ActivityService activityService = getActivityService();
		if (activityService == null) {
			throw new ClientServicesException(null, "No activity service associated with this activity.");
		}
		return activityService.updateMember(this, member);
	}
	
	/**
	 * Delete an activity member.
	 * 
	 * @param member
	 * @return
	 */
	public String deleteMember(Member member) throws ClientServicesException {
		ActivityService activityService = getActivityService();
		if (activityService == null) {
			throw new ClientServicesException(null, "No activity service associated with this activity.");
		}
		return activityService.deleteMember(this, member);
	}
	
}