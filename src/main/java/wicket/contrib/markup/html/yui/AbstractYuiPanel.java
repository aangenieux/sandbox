/*
 * $Id: AbstractYuiPanel.java 4820 2006-03-08 00:21:01 -0800 (Wed, 08 Mar 2006)
 * eelco12 $ $Revision: 5159 $ $Date: 2006-03-08 00:21:01 -0800 (Wed, 08 Mar
 * 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.markup.html.yui;

import wicket.behavior.HeaderContributor;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

/**
 * Abstract panel for contributing common classes for YUI.
 * 
 * @author Eelco Hillenius
 */
public class AbstractYuiPanel extends Panel {
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public AbstractYuiPanel(String id) {
		super(id);
		addHeaderContributions();
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 */
	public AbstractYuiPanel(String id, IModel model) {
		super(id, model);
		addHeaderContributions();
	}

	/**
	 * Adds the default header contributions for all YUI components.
	 */
	private void addHeaderContributions() {
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "yahoo.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "event.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "dom.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "animation.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "autocomplete.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "calendar.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "connection.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "container.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "dragdrop.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "logger.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "menu.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "slider.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "treeview.js"));
		add(HeaderContributor.forJavaScript(AbstractYuiPanel.class, "DDSwap.js"));
	}
}
