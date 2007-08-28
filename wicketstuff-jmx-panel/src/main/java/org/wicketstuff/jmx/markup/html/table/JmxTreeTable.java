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
package org.wicketstuff.jmx.markup.html.table;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation;
import org.apache.wicket.extensions.markup.html.tree.table.IColumn;
import org.apache.wicket.extensions.markup.html.tree.table.TreeTable;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Alignment;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Unit;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * @author Gerolf Seitz
 * 
 */
public class JmxTreeTable extends TreeTable implements IHeaderContributor {
	private static final ResourceReference ATTRIBUTE_ICON = new ResourceReference(
			JmxTreeTable.class, "res/attribute.gif");
	private static final ResourceReference OPERATION_ICON = new ResourceReference(
			JmxTreeTable.class, "res/operation.gif");
	public static final ResourceReference CSS = new ResourceReference(
			JmxTreeTable.class, "res/JmxTreeTable.css");

	private static final long serialVersionUID = 1L;

	public JmxTreeTable(String id, TreeModel model) {
		super(id, model, new IColumn[] {
				new JmxTreeColumn(new ColumnLocation(Alignment.LEFT, 50,
						Unit.PERCENT), "Node"),
				new JmxEditorTreeColumn(new ColumnLocation(Alignment.RIGHT, 50,
						Unit.PERCENT), "Value") });
		setRootLess(true);
		getTreeState().collapseAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.extensions.markup.html.tree.DefaultAbstractTree#getNodeIcon(javax.swing.tree.TreeNode)
	 */
	@Override
	protected ResourceReference getNodeIcon(TreeNode node) {
		if (!node.isLeaf()) {
			return super.getNodeIcon(node);
		}
		Object obj = ((DefaultMutableTreeNode) node).getUserObject();

		if (obj instanceof MBeanAttributeInfo) {
			return ATTRIBUTE_ICON;
		} else if (obj instanceof MBeanOperationInfo) {
			return OPERATION_ICON;
		} else {
			return super.getItem();
		}
	}

	/**
	 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response) {
		HeaderContributor.forCss(CSS).renderHead(response);
	}

	@Override
	protected Component newTreePanel(MarkupContainer parent, String id,
			TreeNode node, int level, IRenderNodeCallback renderNodeCallback) {
		return new TreeNodePanel(id, node, level, renderNodeCallback);
	}

	/**
	 * Default panel for tree nodes. Identical to treetable but without a
	 * clickable link.
	 * 
	 * @author ivaynberg
	 * 
	 */
	private class TreeNodePanel extends Panel {
		private static final long serialVersionUID = 1L;

		private TreeNodePanel(String id, final TreeNode node, int level,
				final IRenderNodeCallback renderNodeCallback) {
			super(id);

			add(newIndentation(this, "indent", node, level));

			add(newJunctionLink(this, "link", "image", node));

			add(newNodeIcon(this, "icon", node));

			add(new Label("label", new AbstractReadOnlyModel() {
				private static final long serialVersionUID = 1L;

				/**
				 * @see org.apache.wicket.model.AbstractReadOnlyModel#getObject()
				 */
				public Object getObject() {
					return renderNodeCallback.renderNode(node);
				}
			}));
		}
	}
}
