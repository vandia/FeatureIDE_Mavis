/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2015  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 * 
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package de.ovgu.featureide.fm.ui.editors.featuremodel.operations;

import static de.ovgu.featureide.fm.core.localization.StringTable.EDIT_CONSTRAINT;

import org.prop4j.Node;

import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.event.FeatureModelEvent;

/**
 * Operation with functionality to edit a constraint. Enables undo/redo
 * functionality.
 * 
 * @author Fabian Benduhn
 * @author Marcus Pinnecke
 */
public class EditConstraintOperation extends AbstractFeatureModelOperation {

	private IConstraint constraint;
	private Node newNode;
	private Node oldNode;

	public EditConstraintOperation(IConstraint constraint, Node propNode) {
		super(constraint.getFeatureModel(), EDIT_CONSTRAINT);
		this.newNode = propNode;
		this.oldNode = constraint.getNode();
		this.constraint = constraint;
	}

	@Override
	protected FeatureModelEvent operation() {
		constraint.setNode(newNode);
		return new FeatureModelEvent(constraint, FeatureModelEvent.CONSTRAINT_MODIFY, oldNode, newNode);
	}

	@Override
	protected FeatureModelEvent inverseOperation() {
		constraint.setNode(oldNode);
		return new FeatureModelEvent(constraint, FeatureModelEvent.CONSTRAINT_MODIFY, newNode, oldNode);
	}

}