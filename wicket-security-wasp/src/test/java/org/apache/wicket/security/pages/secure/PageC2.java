/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.pages.secure;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ClassSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;

/**
 * Shows inherited instantiation checks.
 * @author marrink
 *
 */
public class PageC2 extends PageC
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Instantiation check that requires foo rights instead of the default access rights.
	 */
	static final ISecurityCheck alternate = new ClassSecurityCheck(PageC2.class)
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @see org.apache.wicket.security.checks.ClassSecurityCheck#isActionAuthorized(org.apache.wicket.security.actions.WaspAction)
		 */
		public boolean isActionAuthorized(WaspAction action)
		{
			if (isAuthenticated())
				return getStrategy().isClassAuthorized(getClazz(), action.add(getActionFactory().getAction("foo")));
			throw new RestartResponseAtInterceptPageException(getLoginPage());
		}

	};

	/**
	 * 
	 */
	public PageC2()
	{
		super();
	}

}
