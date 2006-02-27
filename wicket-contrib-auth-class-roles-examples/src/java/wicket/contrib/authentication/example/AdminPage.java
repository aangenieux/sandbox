/*
 * $Id$ $Revision$
 * $Date$
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
package wicket.contrib.authentication.example;

import wicket.contrib.authorization.strategies.role.annotations.AuthorizeInstantiation;
import wicket.contrib.authorization.strategies.role.example.AdminRole;
import wicket.markup.html.WebPage;

/**
 * A page only accessible by a user in the ADMIN role.
 *
 * @author Jonathan Locke
 * @author Gili Tzabari
 */
@AuthorizeInstantiation(AdminRole.class)
public class AdminPage extends WebPage
{
}