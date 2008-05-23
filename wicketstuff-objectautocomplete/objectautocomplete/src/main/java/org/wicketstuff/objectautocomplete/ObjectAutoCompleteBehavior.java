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
package org.wicketstuff.objectautocomplete;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.Component;
import org.apache.wicket.Application;
import org.apache.wicket.RequestContext;
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.settings.IDebugSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

/**
 * Behaviour for object auto completion using a slightly modified variant of
 * {@see org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteBehavior}
 *
 * An (hidden) element is required to store the object id which has been selected.
 *
 * @author roland
 * @since May 18, 2008
 */
abstract public class ObjectAutoCompleteBehavior<T> extends AutoCompleteBehavior<T> {

    private static final ResourceReference OBJECTAUTOCOMPLETE_JS = new JavascriptResourceReference(
            ObjectAutoCompleteBehavior.class, "wicketstuff-objectautocomplete.js");
    // Our version of 'wicket-autocomplete.js', with the patch from WICKET-1651
    private static final ResourceReference AUTOCOMPLETE_OBJECTIFIED_JS = new JavascriptResourceReference(
            ObjectAutoCompleteBehavior.class, "wicket-autocomplete-objectified.js");
    // Reference to upstream JS, use this if the required patch has been applied. For now, unused.
    private static final ResourceReference AUTOCOMPLETE_JS = new JavascriptResourceReference(
		AutoCompleteBehavior.class, "wicket-autocomplete.js");

    // Element holding the object id as value
    private Component objectElement;

    public ObjectAutoCompleteBehavior(Component pObjectElement) {
        this(pObjectElement,new ObjectAutoCompleteRenderer<T>());
    }
    public ObjectAutoCompleteBehavior(Component pObjectElement,ObjectAutoCompleteRenderer<T> pAutoCompleteRenderer) {
        this(pObjectElement,pAutoCompleteRenderer,false);
    }

    public ObjectAutoCompleteBehavior(Component pObjectElement,ObjectAutoCompleteRenderer<T> pAutoCompleteRenderer,
                                      boolean pPreselect) {
        this(pObjectElement,pAutoCompleteRenderer, new AutoCompleteSettings().setPreselect(pPreselect));
    }

    public ObjectAutoCompleteBehavior(Component pObjectElement,ObjectAutoCompleteRenderer<T> pAutoCompleteRenderer,
                                      AutoCompleteSettings pSettings) {
        super(pAutoCompleteRenderer, pSettings);
        objectElement = pObjectElement;
    }


    /**
     * Temporarily solution until patch from WICKET-1651 is applied. Note, that we avoid a call to super
     * to avoid the initialization in the direct parent class, but we have to copy over all other code from the parent,
     *
     * @param response response to write to
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        abstractDefaultAjaxBehaviour_renderHead(response);
        initHead(response);
    }

    // Copied over from AbstractDefaultAjaxBehaviour.renderHead() until patch
    // in WICKET-1651 gets applied
    private void abstractDefaultAjaxBehaviour_renderHead(IHeaderResponse response) {
		final IDebugSettings debugSettings = Application.get().getDebugSettings();

		response.renderJavascriptReference(WicketEventReference.INSTANCE);
		response.renderJavascriptReference(WicketAjaxReference.INSTANCE);

		if (debugSettings.isAjaxDebugModeEnabled())
		{
            response.renderJavascriptReference(new JavascriptResourceReference(
                    AbstractDefaultAjaxBehavior.class, "wicket-ajax-debug.js"));
			response.renderJavascript("wicketAjaxDebugEnable=true;", "wicket-ajax-debug-enable");
		}

		RequestContext context = RequestContext.get();
		if (context.isPortletRequest())
		{
			response.renderJavascript("Wicket.portlet=true", "wicket-ajax-portlet-flag");
		}
    }

    /**
     * Initialize response with our own java script
     *
     * @param response response to write to
     */
    protected void initHead(IHeaderResponse response)
	{
		response.renderJavascriptReference(AUTOCOMPLETE_OBJECTIFIED_JS);
		response.renderJavascriptReference(OBJECTAUTOCOMPLETE_JS);
		final String id = getComponent().getMarkupId();
        String initJS = String.format("new Wicketstuff.ObjectAutoComplete('%s','%s','%s',%s);", id,objectElement.getMarkupId(),
            getCallbackUrl(), constructSettingsJS());
		response.renderOnDomReadyJavascript(initJS);
	}

}