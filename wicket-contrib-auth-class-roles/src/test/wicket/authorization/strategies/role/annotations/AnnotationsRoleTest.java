/*
 * $Id$
 * $Revision$ $Date$
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
package wicket.authorization.strategies.role.annotations;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;
import wicket.Page;
import wicket.authorization.UnauthorizedInstantiationException;
import wicket.contrib.authorization.strategies.role.IRoleCheckingStrategy;
import wicket.contrib.authorization.strategies.role.RoleAuthorizationStrategy;
import wicket.contrib.authorization.strategies.role.Roles;
import wicket.util.tester.ITestPageSource;
import wicket.util.tester.WicketTester;

/**
 * Test the annotations package of the auth-roles project.
 *
 * @author Eelco Hillenius
 */
public class AnnotationsRoleTest extends TestCase
{
  /**
   * Construct.
   */
  public AnnotationsRoleTest()
  {
    super();
  }
  
  /**
   * Construct.
   *
   * @param arg0
   */
  public AnnotationsRoleTest(String arg0)
  {
    super(arg0);
  }
  
  /**
   * @throws Exception
   */
  public void testClear() throws Exception
  {
    WicketTester tester = new WicketTester();
    tester.getSecuritySettings().setAuthorizationStrategy(
      new RoleAuthorizationStrategy(new UserRolesAuthorizer(FooRole.class)));
    tester.startPage(new ITestPageSource()
    {
      public Page getTestPage()
      {
        return new NormalPage();
      }
    });
    tester.assertRenderedPage(NormalPage.class);
  }
  
  /**
   * @throws Exception
   */
  public void testAuthorized() throws Exception
  {
    WicketTester tester = new WicketTester();
    tester.getSecuritySettings().setAuthorizationStrategy(
      new RoleAuthorizationStrategy(new UserRolesAuthorizer(AdminRole.class)));
    tester.startPage(new ITestPageSource()
    {
      public Page getTestPage()
      {
        return new AdminPage();
      }
    });
    tester.assertRenderedPage(AdminPage.class);
  }
  
  /**
   * @throws Exception
   */
  public void testNotAuthorized() throws Exception
  {
    WicketTester tester = new WicketTester();
    tester.getSecuritySettings().setAuthorizationStrategy(
      new RoleAuthorizationStrategy(new UserRolesAuthorizer(UserRolesAuthorizer.class)));
    try
    {
      tester.startPage(new ITestPageSource()
      {
        public Page getTestPage()
        {
          return new AdminPage();
        }
      });
      fail("an authorization exception should have been thrown");
    }
    catch (Exception e)
    {
      if (!(e.getCause() instanceof InvocationTargetException && ((InvocationTargetException)e
        .getCause()).getTargetException() instanceof UnauthorizedInstantiationException))
      {
        throw e;
      }
    }
  }
  
  /**
   * Authorizer class that uses the TS user and it's defined string[] roles.
   */
  private static final class UserRolesAuthorizer implements IRoleCheckingStrategy, Serializable
  {
    private static final long serialVersionUID = 1L;
    
    private final Class role;
    
    /**
     * Construct.
     *
     * @param roles
     */
    public UserRolesAuthorizer(Class role)
    {
      this.role = role;
    }
    
    /**
     * @see wicket.authorization.strategies.role.IRoleCheckingStrategy#hasAnyRole(Roles)
     */
    public boolean hasAnyRole(Roles roles)
    {
      return new Roles(this.role).containsAny(roles);
    }
  }
}
