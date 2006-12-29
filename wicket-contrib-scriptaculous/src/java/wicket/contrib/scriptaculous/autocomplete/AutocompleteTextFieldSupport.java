package wicket.contrib.scriptaculous.autocomplete;

import wicket.MarkupContainer;
import wicket.ResourceReference;
import wicket.contrib.scriptaculous.JavascriptBuilder;
import wicket.contrib.scriptaculous.ScriptaculousAjaxBehavior;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.form.TextField;
import wicket.markup.html.internal.HeaderContainer;

/**
 * support class for all autocomplete text fields. handles binding of needed css
 * and javascript.
 *
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public abstract class AutocompleteTextFieldSupport<T> extends TextField<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 *
	 * @param id
	 */
	public AutocompleteTextFieldSupport(MarkupContainer parent, String id)
	{
		super(parent, id);
		add(ScriptaculousAjaxBehavior.newJavascriptBindingBehavior());

		setOutputMarkupId(true);
	}

	@Override
	public final void renderHead(HeaderContainer container)
	{
		super.renderHead(container);
		addCssReference(container, getCss());

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("new " + getAutocompleteType() + "(");
		builder.addLine("  '" + getMarkupId() + "', ");
		builder.addLine("  '" + getAutocompleteId() + "', ");
		builder.addLine("  '" + getThirdAutocompleteArgument() + "', ");
		builder.addLine(");");
		container.getResponse().write(builder.buildScriptTagString());
	}

	protected abstract String getThirdAutocompleteArgument();

	protected abstract String getAutocompleteType();

	protected final String getAutocompleteId()
	{
		return getMarkupId() + "_autocomplete";
	}

	protected ResourceReference getCss()
	{
		return new ResourceReference(AutocompleteTextFieldSupport.class, "style.css");
	}

	/**
	 * @see wicket.Component#onComponentTag(wicket.markup.ComponentTag)
	 */
	protected final void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("autocomplete", "off");
	}

	/**
	 * adds a placeholder div where auto completion results will be populated.
	 */
	protected final void onRender(MarkupStream markupStream)
	{
		super.onRender(markupStream);

		getResponse().write(
				"<div class=\"auto_complete\" id=\"" + getAutocompleteId() + "\"></div>");
	}

	private void addCssReference(HeaderContainer container, ResourceReference ref)
	{
		CharSequence url = container.getPage().urlFor(ref);
		write(container, "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
		write(container, url);
		write(container, "\"/>\n");
	}

	/**
	 * Writes the given string to the header container.
	 *
	 * @param container
	 *            the header container
	 * @param s
	 *            the string to write
	 */
	private void write(HeaderContainer container, CharSequence s)
	{
		container.getResponse().write(s);
	}
}
