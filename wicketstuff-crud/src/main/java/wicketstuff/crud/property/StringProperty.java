package wicketstuff.crud.property;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.TextEditor;


public class StringProperty extends Property
{
	private int maxLength;

	public StringProperty(String path, IModel label)
	{
		super(path);
		setLabel(label);
	}

	@Override
	public Component getEditor(String id, IModel object)
	{
		TextEditor editor = new TextEditor(id, new PropertyModel(object, getPath()))
				.setMaxLength(maxLength);
		configure(editor);
		return editor;
	}

	public int getMaxLength()
	{
		return maxLength;
	}

	public StringProperty setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
		return this;
	}


}