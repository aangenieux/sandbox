/*
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
package org.wicketstuff.openlayers.event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.openlayers.OpenLayersMap;

public abstract class MoveEndListener extends EventListenerBehavior {

	@Override
	protected String getEvent() {
		return "moveend";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target) {
		onMoveEnd(target);
	}

	/**
	 * Override this method to provide handling of a moveend.<br>
	 * You can get the new center coordinates of the map by calling
	 * {@link OpenLayersMap#getCenter()}.
	 * 
	 * @param target
	 *            the target that initiated the move
	 */
	protected abstract void onMoveEnd(AjaxRequestTarget target);
}