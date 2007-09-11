package wicket.contrib.scriptaculous.examples.dragdrop;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.scriptaculous.Indicator;
import org.wicketstuff.scriptaculous.dragdrop.DraggableBehavior;
import org.wicketstuff.scriptaculous.dragdrop.DraggableTarget;

public class DragDropExamplePage extends WebPage
{
	public DragDropExamplePage()
	{
		Indicator indicator = new Indicator();
		final DraggableTarget cart = new DraggableTarget("cart") {
			protected void onDrop(String input, AjaxRequestTarget target)
			{
				System.out.println("Input: " + input + " was dropped on the DraggableTarget");
			}
		};

		DraggableBehavior draggableBehavior = new DraggableBehavior() {
			public String getDraggableClassName()
			{
				return "draggable";
			}
		};
		WebMarkupContainer product1 = new WebMarkupContainer("product1");
		product1.add(draggableBehavior);

		WebMarkupContainer product2 = new WebMarkupContainer("product2");
		product1.add(draggableBehavior);

		cart.accepts(product1);
		cart.accepts(product2);

		add(cart);
		add(indicator);
	}
}