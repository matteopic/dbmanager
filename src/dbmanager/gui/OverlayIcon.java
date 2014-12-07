package dbmanager.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class OverlayIcon extends ImageIcon {

	private static final long serialVersionUID = -6590163736202380210L;
	private ImageIcon base;
	private List<Icon> overlays;

	public OverlayIcon(ImageIcon base) {
		super(base.getImage());
		this.base = base;
		this.overlays = new ArrayList<Icon>();
	}

	public void addOverlay(Icon overlay) {
		overlays.add(overlay);
	}

	public void clearOverlays() {
		overlays.clear();
	}

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		base.paintIcon(c, g, x, y);
		for (Icon icon : overlays) {
			icon.paintIcon(c, g, x, y);
		}
	}
}