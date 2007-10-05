package wicket.contrib.input.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;

import wicket.contrib.input.events.key.KeyType;

/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
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

public class Page extends WebPage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Page() {
		Label label = new Label("id");
		add(label);
		Button button = new Button("button") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				warn("you clicked me!");
			}
		};
		List<KeyType> keys = new ArrayList<KeyType>();
		keys.add(KeyType.a);
		button.add(new AjaxEventBehavior("onClick"){@Override
		protected void onEvent(AjaxRequestTarget target) {
			// TODO Auto-generated method stub
			
		}});
		button.add(new InputBehavior(keys, EventType.click));
		add(button);
		
		Button button2 = new Button("button2") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				warn("you clicked me!");
			}
		};
		keys = new ArrayList<KeyType>();
		keys.add(KeyType.a);
		button2.add(new AjaxEventBehavior("onClick"){@Override
		protected void onEvent(AjaxRequestTarget target) {
			// TODO Auto-generated method stub
			
		}});
		button2.add(new InputBehavior(keys, true));
		add(button2);

	}

}
