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
package wicket.contrib.gmap.event;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;

import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GOverlay;

public abstract class GMarkerDragendListener extends GOverlayListenerBehavior
{

	@Override
	protected String getEvent()
	{
		return "dragend";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target)
	{
		Request request = RequestCycle.get().getRequest();

		GMarker marker = null;
		GLatLng  latLng  = null;
		
		String markerParameter = request.getParameter("marker");
		if (markerParameter != null)
		{
			for (GOverlay ovl : getGMap2().getOverlays())
			{
				if (ovl.getId().equals(markerParameter)  && ovl instanceof GMarker)
				{
					marker = (GMarker)ovl;
					break;
				}
			}
		}

		String latLngParameter = request.getParameter("latLng");
		if (latLngParameter != null)
		{
			latLng = GLatLng.parse(latLngParameter);
		}
		
		marker.setLagLng(latLng);
		
		onDragend(target, marker);
	}
	/**
	 * Override this method to provide handling of a dragend of a marker. See the
	 * event section of <a
	 * href="http://www.google.com/apis/maps/documentation/reference.html#GMap2">GMap2</a>.
	 * 
	 * @param marker
	 *            The clicked marker. Might be null.
	 * @param target
	 *            The target that initiated the click.
	 */
	protected abstract void onDragend(AjaxRequestTarget target, GMarker marker);
}
