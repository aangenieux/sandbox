package wicket.contrib.mootools;

import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.util.string.JavascriptUtils;

import wicket.contrib.mootools.plugins.MFXLogger;

/**
 * Convenience class for writing Moocentric Javascript tags.
 * 
 * @author victori
 * 
 */
public class MFXJavascriptUtils {
	/**
	 * Write domReady function open tag
	 * 
	 * @return
	 */
	public final static String DOM_READY_OPEN() {
		String str;
		str = JavascriptUtils.SCRIPT_OPEN_TAG;
		str += "window.addEvent(\"load\", function() {";
		return str;
	}

	public final static CompressedResourceReference getMooAddons() {
		return new CompressedResourceReference(MFXLogger.class, "mfxaddons.js");
	}

	/**
	 * Write domReady function close tag
	 * 
	 * @return
	 */
	public final static String DOM_READY_CLOSE() {
		return "});" + JavascriptUtils.SCRIPT_CLOSE_TAG;
	}
}
