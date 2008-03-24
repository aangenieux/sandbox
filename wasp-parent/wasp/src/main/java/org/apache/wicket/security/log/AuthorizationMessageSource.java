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
package org.apache.wicket.security.log;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;
import org.apache.wicket.validation.IErrorMessageSource;

/**
 * Default {@link IErrorMessageSource} to store variables which van be used in
 * the message to provide more detail.
 * 
 * @author marrink
 */
public class AuthorizationMessageSource implements IAuthorizationMessageSource
{
	private static final long serialVersionUID = 1L;
	/**
	 * Map containing the variable we can use to compose an error message.
	 */
	private Map variables = new HashMap();
	/**
	 * Source of the authorization denied.
	 */
	private Component component;

	/**
	 * Constructs a new AuthorizationErrorMessageSource without any variables.
	 */
	public AuthorizationMessageSource()
	{

	}

	/**
	 * Constructs a new AuthorizationErrorMessageSource with the specified
	 * initial variables
	 * 
	 * @param vars
	 *            initial variables
	 */
	public AuthorizationMessageSource(Map vars)
	{
		variables.putAll(vars);
	}

	/**
	 * @see org.apache.wicket.validation.IErrorMessageSource#getMessage(java.lang.String)
	 */
	public final String getMessage(String key)
	{
		Localizer localizer = Application.get().getResourceSettings().getLocalizer();
		// Note: It is important that the default value of "" is provided
		// to getString() not to throw a MissingResourceException or to
		// return a default string like "[Warning: String ..."
		String message = localizer.getString(key, getComponent(), "");
		if (Strings.isEmpty(message))
			return null;
		return message;
	}

	/**
	 * @see org.apache.wicket.security.log.IAuthorizationMessageSource#getComponent()
	 */
	public final Component getComponent()
	{
		return component;
	}

	/**
	 * @see org.apache.wicket.validation.IErrorMessageSource#substitute(java.lang.String,
	 *      java.util.Map)
	 */
	public final String substitute(String string, Map vars) throws IllegalStateException
	{
		return new MapVariableInterpolator(string, mergeVariables(vars), Application.get()
				.getResourceSettings().getThrowExceptionOnMissingResource()).toString();
	}

	/**
	 * @see org.apache.wicket.security.log.IAuthorizationMessageSource#setComponent(org.apache.wicket.Component)
	 */
	public final void setComponent(Component component)
	{
		this.component = component;
		variables.put("component", component);
	}

	/**
	 * @see org.apache.wicket.security.log.IAuthorizationMessageSource#addVariable(java.lang.String,
	 *      java.lang.Object)
	 */
	public final void addVariable(String name, Object value)
	{
		variables.put(name, value);
	}

	/**
	 * Merges this variables with a new set of variables in a new {@link Map}.
	 * The extra variables overwrite the pre-existing variables.
	 * 
	 * @param map
	 *            extra variables
	 * @return new map containing both the internal variables and the extra
	 *         variables from the map
	 */
	protected final Map mergeVariables(Map map)
	{
		Map result = new HashMap(variables);
		if (map != null && !map.isEmpty())
			result.putAll(map);
		return result;
	}
}