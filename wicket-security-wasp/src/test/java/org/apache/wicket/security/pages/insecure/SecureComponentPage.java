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
package org.apache.wicket.security.pages.insecure;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.security.checks.ComponentSecurityCheck;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.pages.BasePage;


/**
 * @author marrink
 * 
 */
public class SecureComponentPage extends BasePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SecureComponentPage()
	{
		add(new Label("welcome", "Welcome Anyone can see this page as long as they are logged in"));
		Label secureLabel=new Label("secure","this label is what forces you to login");
		add(SecureComponentHelper.setSecurityCheck(secureLabel, new ComponentSecurityCheck(secureLabel)));
	}

}
