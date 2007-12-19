package wicket.contrib.examples.gmap.refreshpoint;

import java.util.Arrays;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.time.Duration;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GIcon;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.api.GPoint;
import wicket.contrib.gmap.api.GSize;
import wicket.contrib.gmap.behavior.GMapAutoUpdatingBehavior;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class RefreshPointPage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;

	private GMap2 map;
	
	public RefreshPointPage() {
		map = new GMap2("map", LOCALHOST);
		add(map);
		
		GOverlay overlay = createOverlay("Amsterdam", new GLatLng(52.37649, 4.888573), "point.gif");
		
		map.addOverlay(overlay);
		
		map.add(new GMapAutoUpdatingBehavior(Duration.ONE_SECOND) {
			private static final long serialVersionUID = 1L;
			private int i = 1;
			
			@Override
			protected void onTimer(AjaxRequestTarget target, GMap2 map) {
				GOverlay overlay;
				if(i % 3 == 0) {
					overlay = createOverlay("Amsterdam", new GLatLng(52.37649, 4.888573), "point.gif");
					i = 0;
				} else if(i % 3 == 1) {
					overlay = createOverlay("Amsterdam", new GLatLng(52.37649, 4.888573), "point2.gif");
				} else {
					overlay = createOverlay("Anywhere", new GLatLng(43.3944, 1.42965), "point2.gif");
				}
				i++;
				map.updateOverlays(Arrays.asList(new GOverlay[]{overlay}));
			}
		});
	}
	
	private GOverlay createOverlay(String title, GLatLng latLng, String iconName) {
		GMarkerOptions options = new GMarkerOptions();
		options.setTitle(title);
		
		GIcon icon = new GIcon(urlFor(new ResourceReference(RefreshPointPage.class, iconName)).toString());
		icon.setIconSize(new GSize(40, 40));
		icon.setIconAnchor(new GPoint(19, 40));
		icon.setInfoWindowAnchor(new GPoint(9, 2));
		icon.setInfoShadowAnchor(new GPoint(18, 25));
		options.setIcon(icon);
		map.setCenter(latLng);
		return new GMarker(latLng, options);
	}

	/**
	 * pay attention at webapp deploy context, we need a different key for each
	 * deploy context check <a
	 * href="http://www.google.com/apis/maps/signup.html">Google Maps API - Sign
	 * Up</a> for more info. Also the GClientGeocoder is pickier on this than
	 * the GMap2. Running on 'localhost' GMap2 will ignore the key and the maps
	 * will show up, but GClientGeocoder wount. So if the key doesn't match the
	 * url down to the directory GClientGeocoder will not work.
	 * 
	 * This key is good for all URLs in this directory:
	 * http://localhost:8080/wicket-contrib-gmap2-examples/gmap/
	 */
	private static final String LOCALHOST = "ABQIAAAAzaZpf6nHOd9w1PfLaM9u2xSRJOeFm910afBJASoNgKJoF-fSURQRJ7dNBq-d-8hD7iUYeN2jQHZi8Q";
}