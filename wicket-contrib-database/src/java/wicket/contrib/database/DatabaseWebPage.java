/*
 * $Id$ $Revision:
 * 1.43 $ $Date$
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
package wicket.contrib.database;

import wicket.PageParameters;
import wicket.markup.html.WebPage;
import wicket.model.IModel;
import wicket.util.string.StringValueConversionException;

/**
 * Base class for pages in a database driven web application.
 * 
 * @author Jonathan Locke
 */
public class DatabaseWebPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private final PageParameters parameters;

	public DatabaseWebPage()
	{
		super();
		this.parameters = null;
	}

	public DatabaseWebPage(PageParameters parameters)
	{
		this.parameters = parameters;
	}

	public DatabaseWebPage(IModel model)
	{
		super(model);
		this.parameters = null;
	}

	protected Database getDatabase()
	{
		return ((IDatabaseApplication)getApplication()).getDatabase();
	}

	protected DatabaseSession getDatabaseSession()
	{
		DatabaseWebRequestCycle cycle = (DatabaseWebRequestCycle)getRequestCycle();
		return cycle.getDatabaseSession();
	}

	protected IModel newModel(Class c, Long id)
	{
		if (id.longValue() == -1)
		{
			return null;
		}
		return new DatabaseObjectModel(getDatabaseSession(), c, id);
	}

	protected IModel newModel(Class c)
	{
		try
		{
			return newModel(c, new Long(parameters.getLong("id")));
		}
		catch (StringValueConversionException e)
		{
			throw new DatabaseException(e);
		}
	}

	protected Object load(Class c, Long id)
	{
		if (id.longValue() == -1)
		{
			return null;
		}
		return getDatabaseSession().load(c, id);
	}

	protected Object load(Class c)
	{
		try
		{
			return load(c, new Long(parameters.getLong("id")));
		}
		catch (StringValueConversionException e)
		{
			throw new DatabaseException(e);
		}
	}

	protected void deleteTransaction(Class c, Long id)
	{
		if (id.longValue() != -1)
		{
			getDatabaseSession().deleteTransaction(c, id);
		}
	}

	protected void deleteTransaction(Object object)
	{
		getDatabaseSession().deleteTransaction(object);
	}

	protected void evict(Object object)
	{
		getDatabaseSession().evict(object);
	}

	protected void saveOrUpdateTransaction(Object object)
	{
		getDatabaseSession().saveOrUpdateTransaction(object);
	}

	protected void saveTransaction(Object object)
	{
		getDatabaseSession().saveTransaction(object);
	}

	protected void updateTransaction(Object object)
	{
		getDatabaseSession().updateTransaction(object);
	}
}
