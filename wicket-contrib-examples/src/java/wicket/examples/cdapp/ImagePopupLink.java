/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.examples.cdapp;

import java.sql.Blob;

import org.apache.wicket.Page;
import org.apache.wicket.PageMap;
import org.apache.wicket.examples.cdapp.model.CD;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * Link that displays the image of a cd in a popup window that resizes to fit.
 *
 * @author Eelco Hillenius
 */
public final class ImagePopupLink extends Link
{
	/**
	 * Construct.
	 * @param name
	 * @param cdModel
	 */
	public ImagePopupLink(String name, IModel cdModel)
	{
		super(name, cdModel);
		
		// custom popup settings that uses our automatic resize script
		PopupSettings popupSettings = new PopupSettings(PageMap.forName("imagepopup"));
		popupSettings.setHeight(20);
		popupSettings.setWidth(20);
		setPopupSettings(popupSettings);
	}

	/**
	 * @see wicket.markup.html.link.Link#onClick()
	 */
	public void onClick()
	{
		WebResource imgResource = new WebResource()
		{
			public IResourceStream getResourceStream()
			{
				BlobImageResource img = new BlobImageResource()
				{
					protected Blob getBlob()
					{
						CD cd = (CD)getModelObject();
						return cd.getImage();
					}
				};
				return img.getResourceStream();
			}
		};
		getRequestCycle().setResponsePage(new ImagePopup(imgResource));
	}

	/**
	 * @see wicket.markup.html.link.Link#linksTo(wicket.Page)
	 */
	protected boolean linksTo(Page page)
	{
		// this is kind of ugly, but as isEnabled is marked final, this
		// is our best option; otherwise we would have to override render
		final CD cd = (CD)getModelObject();
		return cd.getImage() == null;
	}
}