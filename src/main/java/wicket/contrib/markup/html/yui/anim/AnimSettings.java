package wicket.contrib.markup.html.yui.anim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wicket.RequestCycle;
import wicket.ResourceReference;
import wicket.contrib.ImageResourceInfo;
import wicket.contrib.InlineStyle;
import wicket.contrib.YuiImage;

/**
 * An AnimSettings allows the user to define the anim select settings
 * 
 * @author cptan
 * 
 */
public class AnimSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Get the default settings
	 * 
	 * @param easing -
	 *            the easing effect of the animation
	 * @param duration -
	 *            the duration of the animation
	 * @param maxSelection -
	 *            the maximum selection allowed
	 * @param animSelectOptionList -
	 *            an arraylist of the animOptions
	 * @return the default settings
	 */
	public static AnimSettings getDefault(String easing, double duration,
			int maxSelection, List<AnimOption> animOptionList) {
		AnimSettings settings = new AnimSettings();
		settings.setResources(easing, duration, maxSelection, animOptionList);
		return settings;
	}

	/**
	 * Get the default settings
	 * 
	 * @param easing -
	 *            the easing effect of the animation
	 * @param duration -
	 *            the duration of the animation
	 * @param maxSelection -
	 *            the maximum selection allowed
	 * @param message -
	 *            the message to display if reach the maximum selection
	 * @param animSelectOptionList -
	 *            an arraylist of the animOptions
	 * @return the default settings
	 */
	public static AnimSettings getDefault(String easing, double duration,
			int maxSelection, String message, List<AnimOption> animOptionList) {
		AnimSettings settings = new AnimSettings();
		settings.setResources(easing, duration, maxSelection, message,
				animOptionList);
		return settings;
	}

	private List<AnimOption> animOptionList;

	private List<InlineStyle> defaultImgOverStyleList = new ArrayList<InlineStyle>();

	private List<InlineStyle> defaultImgStyleList = new ArrayList<InlineStyle>();

	private double duration;

	private String easing;

	private int height;

	private int maxSelection;

	private String message;

	private List<InlineStyle> selectedImgOverStyleList = new ArrayList<InlineStyle>();

	private List<InlineStyle> selectedImgStyleList = new ArrayList<InlineStyle>();

	private int width;

	/**
	 * Creates an AnimSettings
	 * 
	 */
	public AnimSettings() {
	}

	/**
	 * Set the AnimSelectOptionList
	 * 
	 * @return an arraylist of the animOptions
	 */
	public List<AnimOption> getAnimOptionList() {
		return animOptionList;
	}

	/**
	 * Get the default mouseover image style list
	 * 
	 * @return the default mouseover image style list
	 */
	public List<InlineStyle> getDefaultImgOverStyleList() {
		return defaultImgOverStyleList;
	}

	/**
	 * Get the default image style list
	 * 
	 * @return the default image style list
	 */
	public List<InlineStyle> getDefaultImgStyleList() {
		return defaultImgStyleList;
	}

	/**
	 * Get the duration
	 * 
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Get the easing effect
	 * 
	 * @return the easing effect
	 */
	public String getEasing() {
		return easing;
	}

	/**
	 * Get the height
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the maximum selection allowed
	 * 
	 * @return the maximum selection allowed
	 */
	public int getMaxSelection() {
		return maxSelection;
	}

	/**
	 * Get the message
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Get the selected mouseover image style list
	 * 
	 * @return the selected mouseover image style list
	 */
	public List<InlineStyle> getSelectedImgOverStyleList() {
		return selectedImgOverStyleList;
	}

	/**
	 * Get the selected image style list
	 * 
	 * @return the selected image style list
	 */
	public List<InlineStyle> getSelectedImgStyleList() {
		return selectedImgStyleList;
	}

	/**
	 * Get the width
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set the AnimOptionList
	 * 
	 * @param animOptionList -
	 *            an new arraylist of the animOptions
	 */
	public void setAnimOptionList(List<AnimOption> animOptionList) {
		this.animOptionList = animOptionList;
	}

	/**
	 * Set the default mouseover image style list
	 * 
	 * @param defaultImgOverStyleList -
	 *            the new default mouseover image style list
	 */
	public void setDefaultImgOverStyleList(
			List<InlineStyle> defaultImgOverStyleList) {
		this.defaultImgOverStyleList = defaultImgOverStyleList;
	}

	/**
	 * Set the default image style list
	 * 
	 * @param defaultImgStyleList -
	 *            set the new default image style list
	 */
	public void setDefaultImgStyleList(List<InlineStyle> defaultImgStyleList) {
		this.defaultImgStyleList = defaultImgStyleList;
	}

	/**
	 * Set the duration
	 * 
	 * @param duration -
	 *            the new duration
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * Set the easing effects
	 * 
	 * @param easing -
	 *            the new easing effect
	 */
	public void setEasing(String easing) {
		this.easing = easing;
	}

	/**
	 * Set the height
	 * 
	 * @param height -
	 *            the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Set the image resources
	 * 
	 * @param animOptionList -
	 *            an arraylist of the animOptions
	 */
	public void setImageResources(List<AnimOption> animOptionList) {

		for (int i = animOptionList.size() - 1; i >= 0; i--) {
			AnimOption animOption = animOptionList.get(i);
			YuiImage defaultImg = animOption.getDefaultImg();
			YuiImage defaultImgOver = animOption.getDefaultImgOver();
			YuiImage selectedImg = animOption.getSelectedImg();
			YuiImage selectedImgOver = animOption.getSelectedImgOver();

			ResourceReference defaultImgRR = new ResourceReference(
					AnimSettings.class, defaultImg.getFileName());
			ResourceReference defaultImgOverRR = new ResourceReference(
					AnimSettings.class, defaultImgOver.getFileName());
			ResourceReference selectedImgRR = new ResourceReference(
					AnimSettings.class, selectedImg.getFileName());
			ResourceReference selectedImgOverRR = new ResourceReference(
					AnimSettings.class, selectedImgOver.getFileName());

			ImageResourceInfo defaultImgInfo = new ImageResourceInfo(
					defaultImgRR);
			int defaultImgWidth = defaultImgInfo.getWidth();
			int defaultImgHeight = defaultImgInfo.getHeight();
			ImageResourceInfo defaultImgOverInfo = new ImageResourceInfo(
					defaultImgOverRR);
			int defaultImgOverWidth = defaultImgOverInfo.getWidth();
			int defaultImgOverHeight = defaultImgOverInfo.getHeight();
			ImageResourceInfo selectedImgInfo = new ImageResourceInfo(
					selectedImgRR);
			int selectedImgWidth = selectedImgInfo.getWidth();
			int selectedImgHeight = selectedImgInfo.getHeight();
			ImageResourceInfo selectedImgOverInfo = new ImageResourceInfo(
					selectedImgOverRR);
			int selectedImgOverWidth = selectedImgOverInfo.getWidth();
			int selectedImgOverHeight = selectedImgOverInfo.getHeight();

			InlineStyle defaultImgStyle = new InlineStyle();
			defaultImgStyle.add("background", "url("
					+ RequestCycle.get().urlFor(defaultImgRR) + ")");
			defaultImgStyle.add("width", defaultImgWidth + "px");
			defaultImgStyle.add("height", defaultImgHeight + "px");

			InlineStyle defaultImgOverStyle = new InlineStyle();
			defaultImgOverStyle.add("background", "url("
					+ RequestCycle.get().urlFor(defaultImgOverRR) + ")");
			defaultImgOverStyle.add("width", defaultImgOverWidth + "px");
			defaultImgOverStyle.add("height", defaultImgOverHeight + "px");

			InlineStyle selectedImgStyle = new InlineStyle();
			selectedImgStyle.add("background", "url("
					+ RequestCycle.get().urlFor(selectedImgRR) + ")");
			selectedImgStyle.add("width", selectedImgWidth + "px");
			selectedImgStyle.add("height", selectedImgHeight + "px");

			InlineStyle selectedImgOverStyle = new InlineStyle();
			selectedImgOverStyle.add("background", "url("
					+ RequestCycle.get().urlFor(selectedImgOverRR) + ")");
			selectedImgOverStyle.add("width", selectedImgOverWidth + "px");
			selectedImgOverStyle.add("height", selectedImgOverHeight + "px");

			defaultImgStyleList.add(defaultImgStyle);
			defaultImgOverStyleList.add(defaultImgOverStyle);
			selectedImgStyleList.add(selectedImgStyle);
			selectedImgOverStyleList.add(selectedImgOverStyle);

			this.width = defaultImgWidth;
			this.height = defaultImgHeight;
		}
	}

	/**
	 * Set the maximum selection allowed
	 * 
	 * @param maxSelection -
	 *            the new maximum selection allowed
	 */
	public void setMaxSelection(int maxSelection) {
		this.maxSelection = maxSelection;
	}

	/**
	 * Set the message
	 * 
	 * @param message -
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Set the resources
	 * 
	 * @param easing -
	 *            the easing effect of the animation
	 * @param duration -
	 *            the duration of the animation
	 * @param maxSelection -
	 *            the maximum selection allowed
	 * @param animSelectGroupOption -
	 *            an arraylist of the animOptions
	 */
	public void setResources(String easing, double duration, int maxSelection,
			List<AnimOption> animOptionList) {
		setEasing(easing);
		setDuration(duration);
		setMaxSelection(maxSelection);
		setAnimOptionList(animOptionList);
		setImageResources(animOptionList);
	}

	/**
	 * Set the resources
	 * 
	 * @param easing -
	 *            the easing effect of the animation
	 * @param duration -
	 *            the duration of the animation
	 * @param maxSelection -
	 *            the maximum selection allowed
	 * @param message -
	 *            the message to display if reach the maximum selection
	 * @param animSelectOptionList -
	 *            an arraylist of the animOptions
	 */
	public void setResources(String easing, double duration, int maxSelection,
			String message, List<AnimOption> animOptionList) {
		setEasing(easing);
		setDuration(duration);
		setMaxSelection(maxSelection);
		setMessage(message);
		setAnimOptionList(animOptionList);
		setImageResources(animOptionList);
	}

	/**
	 * Set the selected mouseover image style list
	 * 
	 * @param selectedImgOverStyleList -
	 *            the new selected mouseover image style list
	 */
	public void setSelectedImgOverStyleList(
			List<InlineStyle> selectedImgOverStyleList) {
		this.selectedImgOverStyleList = selectedImgOverStyleList;
	}

	/**
	 * Set the selected image style list
	 * 
	 * @param selectedImgStyleList -
	 *            the new selected image style list
	 */
	public void setSelectedImgStyleList(List<InlineStyle> selectedImgStyleList) {
		this.selectedImgStyleList = selectedImgStyleList;
	}

	/**
	 * Set the width
	 * 
	 * @param width -
	 *            the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}
