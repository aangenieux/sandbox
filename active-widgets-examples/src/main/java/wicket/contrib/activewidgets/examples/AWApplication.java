/*
 * $Id: YuiApplication.java 4640 2006-02-26 10:41:53Z eelco12 $
 * $Revision: 4640 $ $Date: 2006-02-26 18:41:53 +0800 (Sun, 26 Feb 2006) $
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.activewidgets.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * 
 *  Wicket application that uses the Active Widgets library
 * 
 */
public class AWApplication extends WicketExampleApplication
{
	/**
	 * Constructor.
	 */
	public AWApplication()
	{
		//System.setProperty(ActiveWidgetsConfiguration.KEY_AW_LICENSE, ActiveWidgetsConfiguration.AW_DEVELOPER_LICENSE);
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	public Class<? extends WebPage> getHomePage()
	{
		return Index.class;
	}
	
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this,
				context()));
	}
	
	public ApplicationContext context() {
		return WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
	}
	
	
}
