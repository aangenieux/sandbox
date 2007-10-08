package org.wicketstuff.pickwick.frontend.panel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.AbstractTree;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.pickwick.frontend.FolderTree;
import org.wicketstuff.pickwick.frontend.pages.SequencePage;

import wicket.contrib.gmap.GLatLng;
import wicket.contrib.gmap.GMap;
import wicket.contrib.gmap.GMapClickListener;
import wicket.contrib.gmap.GMapPanel;
import wicket.contrib.gmap.GMarker;

import com.google.inject.Inject;

/**
 * Panel displaying the image folder structure
 * @author Vincent Demay
 *
 */
public class GmapLocalizedPanel extends Panel{
	@Inject
	private ImageUtils imageUtils;
	@Inject
	private Settings settings;
	
	private List<Folder> folders;
	
	private static final String LOCALHOST_8080_WICKET_CONTRIB_GMAP_EXAMPLES_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxTEpDPjw6LRH7yL06TcOjcEpKZmCRRGeXL1BMh_MNX22hDtswyQqVAOyQ";


	/**
	 * There isn't any model because model is auto binded using {@link FolderProvider}
	 * @param id
	 */
	public GmapLocalizedPanel(String id) {
		super(id);
		
		//add gmap
		GMap gmap = new GMap(new GLatLng(30, 10), 2);
		gmap.setTypeControl(true);
		gmap.setSmallMapControl(true);
		
		folders = new ArrayList<Folder>();
		Folder folder = imageUtils.getFolderFor(PickwickSession.get().getUser().getRoles());
		fillFolders(folder);
		String basePath = settings.getImageDirectoryRoot().getAbsolutePath();
		for (Folder current : folders){
			
			Sequence sequence = ImageUtils.readSequence(current.getFile());
			if (sequence != null && sequence.getLatitude() != 0 && sequence.getLongitude() != 0){
				GMarker wicketLibrary = new GMarker(new GLatLng(sequence.getLatitude(), sequence.getLongitude()),
						new DescriptionPanel("gmarkerInfo", sequence, current));
				gmap.addOverlay(wicketLibrary);
			}
			
		}
		
		GMapPanel mapPanel = new GMapPanel("gmap", gmap, 800, 600,
				LOCALHOST_8080_WICKET_CONTRIB_GMAP_EXAMPLES_KEY);
		/*mapPanel.addClickListener(new GMapClickListener() {

			public void onClick(AjaxRequestTarget target, GLatLng point) {
			}
		});*/
		add(mapPanel);
	
	}

	private void fillFolders(Folder folder) {
		if (folder != null){
			folders.add(folder);
			List<Folder> subFolders = folder.getSubFolders();
			for (Folder current : subFolders){
				fillFolders(current);
			}
		}
	}


}