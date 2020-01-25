package semantic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class CollectionLink implements Comparator<Map.Entry<ArrayList<Integer>,Integer>>
{
  public int compare(Map.Entry<ArrayList<Integer>,Integer> left,
		  					Map.Entry<ArrayList<Integer>,Integer> right) {
	if(left.getKey().get(0) == right.getKey().get(0)) {
		return Integer.compare(left.getKey().get(1),right.getKey().get(1));
	}
    return Integer.compare(left.getKey().get(0),right.getKey().get(0));
  }
}

