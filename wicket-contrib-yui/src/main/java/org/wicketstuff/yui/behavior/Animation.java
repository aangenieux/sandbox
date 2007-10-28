package org.wicketstuff.yui.behavior;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.value.ValueMap;
import org.wicketstuff.yui.YuiHeaderContributor;

/**
 * YUI Animation. 
 * Base class for YAHOO.util.Anim / Motion / Scroll. Also include
 * Effects from <a href="http://blog.davglass.com/files/yui/effects/">http://blog.davglass.com/files/yui/effects/</a>
 * 
 * @author josh
 */
public class Animation extends AbstractBehavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * a sequence of effects.
	 */
	private List<AnimEffect> effects = new ArrayList<AnimEffect>();

	/**
	 * the component's id 
	 */
	private String componentId; 
	
	/**
	 * the Event to trigger the animation
	 */
	private OnEvent onEvent;
	/**
	 * 
	 * @param effects
	 */
	public Animation(OnEvent onEvent)
	{
		this.onEvent = onEvent;
	}

	/**
	 * actually only need relative for 
	 * ShakeLR/ShakeTB/TV 
	 */
	@Override
	public void onComponentTag(Component component, ComponentTag tag)
	{
		super.onComponentTag(component, tag);
		final ValueMap map = (ValueMap) tag.getAttributes();
		
		String existingStyle = map.getString("style");
		String newStyle = "position:relative";
		if (existingStyle != null)
		{
			newStyle = ";" + existingStyle;
		}
		map.put( "style", newStyle);
	}
	
	/**
	 * not sure if this is the best place for this...? but need to add all the header contribution
	 * 
	 */
	@Override
	public void bind(Component component)
	{
		super.beforeRender(component);
		component.add(YuiHeaderContributor.forModule("animation"));
		component.add(HeaderContributor.forJavaScript(AnimEffect.class, "effects/effects.js"));
		component.add(HeaderContributor.forJavaScript(AnimEffect.class, "effects/tools.js"));
		component.add(HeaderContributor.forJavaScript(AnimEffect.class, "effects/animator.js"));
		component.setOutputMarkupId(true);
		this.componentId = component.getMarkupId();
	}
	
	/**
	 * Renders the javascript for this animation. basically 2 lines of javascript.
	 * 1/ var a_anim_object = new  new YAHOO.util.Anim('yim-6-pic', hide_attributes, 1, YAHOO.util.Easing.easeIn);
     * 2/ Wicketstuff.yui.Animator.add(group, event, trigger_id, a_anim_object); 
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		StringBuffer buffer = new StringBuffer()
		.append("var ").append(getAnimVar()).append(" = ").append(buildEffectsJS())
		.append("WicketYuiAnimator.add(")
		.append("'").append(getOnEvent()).append("'").append(",") 			// trigger_event : the event 'click'
		.append("'").append(getTriggerGorupId()).append("'").append(",") 	// trigger_group : the id of the group
		.append("'").append(getTriggerId()).append("'").append(",") 		// trigger_id    : the id of the triggering obj
		.append(getAnimVar()) 												// a_anim_object : the animation object 
		.append(")");
		response.renderOnDomReadyJavascript(buffer.toString());
	}

	/**
	 * the id that triggers the animation. the triggerId must be a child of the parentId
	 * 
	 * @return
	 */
	public String getTriggerId()
	{
		return getComponentId();
	}

	/**
	 * 
	 * @return
	 */
	private OnEvent getOnEvent()
	{
		return this.onEvent;
	}

	/**
	 * each group share the same event handler. default to the component Id, meaning 
	 * each Animation has its own event handler. that's not good. so override this if
	 * you have a lot of animations within the same container with the parent id.
	 * 
	 * a good idea is probably component.getParent().
	 * @return
	 */
	private String getTriggerGorupId()
	{
		return getTriggerId();
	}

	/**
	 * 
	 * @param effect
	 * @return
	 */
	public Animation add(AnimEffect effect)
	{
		getEffects().add(effect);
		return this;
	}
	
	/**
	 * need this to 
	 * 1/ strip out the initial "eff ="
	 * 2/ end "animate()"
	 * 
	 * var anim_singapore0 = anim_singapore01=new YAHOO.widget.Effects.BlindDown(singapore0,{delay:true},{delay:true});.animate();;
	 * @return
	 */
	private String buildEffectsJS()
	{
		String js = buildEffectsJS(getAnimVar(), effects);
		return js.substring(js.indexOf("=")+1, js.lastIndexOf(getAnimVar()+".animate();"));
	}
	
	/**
	 * builds a Effects from the list
	 * 
	 *  eff = new YAHOO.widget.Effects.BlindUp('demo13', { delay: true }); 
	 *	eff.onEffectComplete.subscribe(function() { 
	 *		eff2 = new YAHOO.widget.Effects.BlindRight('demo13', { delay: true }); 
	 *		eff2.onEffectComplete.subscribe(function() { 
	 *			eff3 = new YAHOO.widget.Effects.BlindDown('demo13', { delay: true }); 
	 *          eff3.animate(); 
	 *      }); 
	 *		eff2.animate(); 
	 *	});
	 *
	 * @return
	 */
	private String buildEffectsJS(String jsVar, List<AnimEffect> effectslist ) 
	{
		int listsize = effectslist.size();
		if (listsize == 0)
		{
			return "";
		}
		else
		{
			AnimEffect effect = effectslist.get(0); 
			StringBuffer buffer = new StringBuffer();
			buffer.append(jsVar).append("=").append("new ")
				  .append(effect.newEffectJS()).append("('").append(getComponentId()).append("',")
				  .append(effect.getAttributes())
				  .append(effect.getOpts())
				  .append(");");
			
			if (listsize > 1) // means at least one more child to go
			{
				buffer.append(jsVar).append(".").append(effect.onCompleteJS()).append(".subscribe(function() {");
				buffer.append(buildEffectsJS(jsVar + listsize, effectslist.subList(1, listsize)));
				buffer.append("});");
			}
			buffer.append(jsVar).append(".").append("animate();");
			return buffer.toString();
		}
	}
	
	/**
	 * the Javascript variable
	 * @return
	 */
	private String getAnimVar()
	{
		return "anim_" + getComponentId();
	}
	
	public String getComponentId()
	{
		return componentId;
	}

	public void setComponentId(String componentId)
	{
		this.componentId = componentId;
	}

	public List<AnimEffect> getEffects()
	{
		return effects;
	}

	public void setEffects(List<AnimEffect> effects)
	{
		this.effects = effects;
	}
}