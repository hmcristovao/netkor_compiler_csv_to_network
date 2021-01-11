package intermediate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class CollectionLink implements Comparator<Map.Entry<ArrayList<String>,Integer>>
{
  public int compare(Map.Entry<ArrayList<String>,Integer> left,
		  					Map.Entry<ArrayList<String>,Integer> right) {
	if(left.getKey().get(0) == right.getKey().get(0)) {
		return Integer.compare(Integer.valueOf(left.getKey().get(1)),Integer.valueOf(right.getKey().get(1)));
	}
    return Integer.compare(Integer.valueOf(left.getKey().get(0)),Integer.valueOf(right.getKey().get(0)));
  }
}

