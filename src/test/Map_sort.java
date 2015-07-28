package test;

import java.util.*;

public class Map_sort {
	public LinkedHashMap<String,Integer > map_sort(HashMap<String,Integer > map){
		LinkedHashMap<String,Integer>	map1=new LinkedHashMap<String,Integer>();
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>();
		list.addAll(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
		   public int compare(Map.Entry obj1, Map.Entry obj2) {//从高往低排序
		       if(Integer.parseInt(obj1.getValue().toString())<Integer.parseInt(obj2.getValue().toString()))
		           return 1;
		       if(Integer.parseInt(obj1.getValue().toString())==Integer.parseInt(obj2.getValue().toString()))
		           return 0;
		       else
		          return -1;
		   }
		});
		for(Iterator<Map.Entry<String, Integer>> ite = list.iterator(); ite.hasNext();) {
			Map.Entry<String, Integer> map2 = ite.next();
			map1.put(map2.getKey(),map2.getValue());
		//    System.out.println(map2.getKey()+"---->"+map2.getValue());
		}
	 //   System.out.println("------------------分割线-------------------");
		return map1;
	}
}
