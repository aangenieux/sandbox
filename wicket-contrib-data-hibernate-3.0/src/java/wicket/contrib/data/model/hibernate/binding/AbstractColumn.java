package wicket.contrib.data.model.hibernate.binding;

/**
 * A convenient place to extends from to create new {@link IColumn}s.
 * 
 * @author Phil Kulak
 */
public abstract class AbstractColumn implements IColumn
{
	private String displayName;

	private String modelPath;

	private String orderByPath;

	private boolean allowOrderBy = true;

	/**
	 * Constructor that sets the orderByPath to the modelPath.
	 * 
	 * @param displayName
	 *            the name that will be displayed as the header for this column
	 * @param modelPath
	 *            the OGNL path to the model
	 */
	public AbstractColumn(String displayName, String modelPath)
	{
		this.displayName = displayName;
		this.modelPath = modelPath;
		this.orderByPath = modelPath;
	}

	public boolean allowOrderBy()
	{
		return allowOrderBy;
	}

	/**
	 * Sets weather or not users are allowed to order by this column.
	 * 
	 * @param allowOrderBy
	 * @return an IColumn to support chaining
	 */
	public IColumn setAllowOrderBy(boolean allowOrderBy)
	{
		this.allowOrderBy = allowOrderBy;
		return this;
	}

	/**
	 * @see IColumn#getOrderByPath()
	 * @param orderByPath
	 * @return an IColumn to support chaining
	 */
	public IColumn setOrderByPath(String orderByPath)
	{
		this.orderByPath = orderByPath;
		return this;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getModelPath()
	{
		return modelPath;
	}

	public String getOrderByPath()
	{
		return orderByPath;
	}
}
