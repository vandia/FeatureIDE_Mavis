/* FeatureIDE - An IDE to support feature-oriented software development
 * Copyright (C) 2005-2011  FeatureIDE Team, University of Magdeburg
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package de.ovgu.featureide.fm.core;

import java.util.ArrayList;

/**
 * Holds the colors for one feature
 * 
 * @author Sebastian Krieter
 */
public class ColorList {
	public static final int INVALID_COLOR = -1;
	
	private final ColorschemeTable colorschemeTable;
	private ArrayList<Integer> colors = new ArrayList<Integer>();
	
	public ColorList(Feature feature) {
		colorschemeTable = feature.getFeatureModel().getColorschemeTable();
		for (int i = 0; i < colorschemeTable.size() + 1; i++) {
			colors.add(INVALID_COLOR);
		}
	}
	
	public static final boolean isValidColor(int color) {
		return color > INVALID_COLOR;
	}

	public boolean hasColor() {
		return colors.get(colorschemeTable.getSelectedColorscheme()) > INVALID_COLOR;
	}
	
	public int getColor() {
		return colors.get(colorschemeTable.getSelectedColorscheme());
	}
	
	public void setColor(int color) {
		colors.set(colorschemeTable.getSelectedColorscheme(), color);
	}
	
	public void removeColor() {
		colors.set(colorschemeTable.getSelectedColorscheme(), INVALID_COLOR);
	}
	
	
	public boolean hasColor(int scheme) {
		return colors.get(scheme) > INVALID_COLOR;
	}
	
	public int getColor(int scheme) {
		return colors.get(scheme);
	}
	
	public void setColor(int scheme, int color) {
		colors.set(scheme, color);
	}
	
	public void addColorscheme() {
		colors.add(INVALID_COLOR);
	}
	
	public void removeColorscheme() {
		colors.remove(colorschemeTable.getSelectedColorscheme());
	}

	public ColorList clone(Feature feature) {
		ColorList newColorScheme = new ColorList(feature);
		newColorScheme.colors = new ArrayList<Integer>(colors);
		return newColorScheme;
	}
}