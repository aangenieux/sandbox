/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.dojo.markup.html.list;

import java.util.List;

import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.dojodnd.DojoDragContainerHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.IHeaderResponse;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * DojoOrderableListView should be added on a {@link DojoOrderableListViewContainer}
 * This container and it child allow top make a DragAndDropableList. Model associated with the {@link DojoOrderableListView}
 * will be automaticaly updated during dnd
 * <p>
 * <b>Sample</b>
 * <pre>
 * public class OrderableList extends WebPage {
 * 
 * 	static final List objList  = new  ArrayList();
 * 
 * 	
 * 	public OrderableList() {
 * 		super();
 * 		if (objList.size() == 0){
 * 			objList.add("foo1");
 * 			objList.add("bar1");
 * 			objList.add("foo2");
 * 			objList.add("bar2");
 * 			objList.add("foo3");
 * 			objList.add("bar3");
 * 			objList.add("foo4");
 * 			objList.add("bar4");
 * 			objList.add("foo5");
 * 			objList.add("bar5");
 * 			objList.add("foo6");
 * 			objList.add("bar6");
 * 		}
 * 		DojoOrderableListViewContainer container = new DojoOrderableListViewContainer(this, "container");
 * 		DojoOrderableListView list = new DojoOrderableListView(container, "list", objList){
 * 
 * 			protected void populateItem(ListItem item) {
 * 				item.add(new Label("label",(String)item.getModelObject()));
 * 				item.add(new DojoOrderableListRemover("remover", item));
 * 				
 * 			}
 * 			
 * 		};
 * 	}
 * 
 * }
 * </pre>
 * </p>
 * @author Vincent Demay
 */
public abstract class DojoOrderableListView extends ListView
{
	DojoOrderableListViewContainer container;
	int pos = 0; 
	private String dragId;

	/**
	 * Construct
	 * @param parent the {@link DojoOrderableListViewContainer} where it will be added
	 * @param id id
	 * @param model model
	 */
	public DojoOrderableListView(DojoOrderableListViewContainer parent, String id, IModel model)
	{
		super(parent, id, model);
		container = parent;
		dragId = container.getMarkupId();
	}

	/**
	 * Construct
	 * @param parent the {@link DojoOrderableListViewContainer} where it will be added
	 * @param id id 
	 * @param list list
	 */
	public DojoOrderableListView(DojoOrderableListViewContainer parent, String id, List list)
	{
		super(parent, id, list);
		container = parent;
		dragId = container.getMarkupId();
	}

	/**
	 * Construct
	 * @param parent the {@link DojoOrderableListViewContainer} where it will be added
	 * @param id id
	 */
	public DojoOrderableListView(DojoOrderableListViewContainer parent, String id)
	{
		super(parent, id);
		container = parent;
		dragId = container.getMarkupId();
	}
	
	public String generateId(){
		return container.getMarkupId()+ "_list_" + (pos++);
		
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
	}

	@Override
	protected void renderItem(ListItem item)
	{
		String id = generateId();
		item.add(new AttributeAppender("id", true, new Model<String>(id),""));
		super.renderItem(item);
	}

	@Override
	protected void populateItem(ListItem item)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderString(generateDragDefinition(container.getMarkupId()));

	}
	
	private String generateDragDefinition(String id){
		String toReturn = "";
		toReturn += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		toReturn += "function initDrag" + id + "(){\n";
		toReturn += "	var children = document.getElementById('" + container.getMarkupId() + "').getElementsByTagName('div');\n";
		toReturn += "	for(var i=0;  children.length > i ; i++){\n";
		toReturn += "		var drag = new dojo.dnd.HtmlDragSource(children[i], ['" + dragId + "']);\n";
		toReturn += "	}\n";
		toReturn += "}\n";
		toReturn += "dojo.event.connect(dojo, \"loaded\", \"initDrag" + id + "\");\n";
		toReturn += "</script>\n";
		return toReturn;
	}

}
