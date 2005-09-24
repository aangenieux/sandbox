package wicket.contrib.scriptaculous.autocomplete;

import wicket.PageParameters;
import wicket.markup.html.WebPage;
import wicket.markup.html.list.ListView;

public class CustomLayoutAutocompleteResultsPageContribution extends WebPage {

	private final String value;

	public CustomLayoutAutocompleteResultsPageContribution(PageParameters parameters) {
		String field = parameters.getString("fieldName");
		value = parameters.getString(field);
	}

	protected String getInputValue() {
		return value;
	}
}
