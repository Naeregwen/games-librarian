/**
 * 
 */
package commons.comparators;

import java.util.Comparator;

/**
 * @author Alex
 *
 */
public class DoubleNullLastComparator implements Comparator<Double> {

	@Override
	public int compare(Double o1, Double o2) {
		System.err.println("DoubleNullLastComparator");
		int comparisonResult = 0;
		if (o1 == null)
			if (o2 == null)
				comparisonResult = 1;
			else
				comparisonResult = -1;
		else
			if (o2 == null)
				comparisonResult = 1;
			else
				comparisonResult = o1.compareTo(o2);
		return comparisonResult;
	}

}
