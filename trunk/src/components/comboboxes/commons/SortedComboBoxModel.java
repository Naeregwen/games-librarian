package components.comboboxes.commons;

import java.util.*;
import javax.swing.*;

/*
 *  http://tips4java.wordpress.com/2008/11/11/sorted-combo-box-model/
 *  
 *  Custom model to make sure the items are stored in a sorted order.
 *  The default is to sort in the natural order of the item, but a
 *  Comparator can be used to customize the sort order.
 */
public class SortedComboBoxModel<E> extends DefaultComboBoxModel<E> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5527587395310192195L;
	
	private Comparator<E> comparator;

	/*
	 * Create an empty model that will use the natural sort order of the item
	 */
	public SortedComboBoxModel() {
		super();
	}

	/*
	 * Create an empty model that will use the specified Comparator
	 */
	public SortedComboBoxModel(Comparator<E> comparator) {
		super();
		this.comparator = comparator;
	}

	/*
	 * Create a model with data and use the nature sort order of the items
	 */
	public SortedComboBoxModel(E items[]) {
		this(items, null);
	}

	/*
	 * Create a model with data and use the specified Comparator
	 */
	public SortedComboBoxModel(E items[], Comparator<E> comparator) {
		this.comparator = comparator;
		for (E item : items) {
			addElement(item);
		}
	}

	/*
	 * Create a model with data and use the nature sort order of the items
	 */
	public SortedComboBoxModel(Vector<E> items) {
		this(items, null);
	}

	/*
	 * Create a model with data and use the specified Comparator
	 */

	public SortedComboBoxModel(Vector<E> items, Comparator<E> comparator) {
		this.comparator = comparator;
		for (E item : items) {
			addElement(item);
		}
	}

	@Override
	public void addElement(E element) {
		insertElementAt(element, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insertElementAt(E element, int index) {
		
		int size = getSize();

		// Determine where to insert element to keep model in sorted order

		for (index = 0; index < size; index++) {
			if (comparator != null) {
				E indexedElement = getElementAt(index);
				if (comparator.compare(indexedElement, element) > 0)
					break;
			} else {
				Comparable<E> c = (Comparable<E>) getElementAt(index);
				if (c.compareTo(element) > 0)
					break;
			}
		}

		super.insertElementAt(element, index);

		// Select an element when it is added to the beginning of the model
		if (index == 0 && element != null && getSelectedItem() == null) {
			setSelectedItem(element);
		}
	}
}
