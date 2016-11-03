package roadgraph;

import java.util.ArrayList;

public class permHelper {
	
	
	
	public static ArrayList<ArrayList<Integer>> permutationBuilder(ArrayList<Integer> curList, int next)
	{
		ArrayList<ArrayList<Integer>> listOfNewPerm = new ArrayList<ArrayList<Integer>>();
		for (int i=0; i<=curList.size(); i++)
		{
			ArrayList<Integer> k = new ArrayList<Integer>(curList);
			k.add(i, next);
			listOfNewPerm.add(k);
		}
		return listOfNewPerm;
	}
	
	public static ArrayList<ArrayList<Integer>> permutationBuilder2(ArrayList<ArrayList<Integer>> inputArray, int k)
	{
		ArrayList<ArrayList<Integer>> listOfAllPerm = new ArrayList<ArrayList<Integer>>();
		for (int i=0; i<inputArray.size(); i++)
		{
			ArrayList<ArrayList<Integer>> listOfNewPerm = permutationBuilder(inputArray.get(i), k);
			for (int j=0; j<listOfNewPerm.size(); j++)
			{
				listOfAllPerm.add(  listOfNewPerm.get(j)   );
			}
		}
		return listOfAllPerm;
	}
	
	public static ArrayList<ArrayList<Integer>> permutationBuilderTotal(int num) // k is greater than 
	{
		ArrayList<ArrayList<Integer>> listOfAllPerm = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> j = new ArrayList<Integer>();
		j.add(1);
		listOfAllPerm.add(j);
		for (int i=2; i<=num; i++)
		{
			listOfAllPerm = permutationBuilder2(listOfAllPerm, i);
		}
		
		
		return listOfAllPerm;
	}
	
	 
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> m = new ArrayList<Integer>();
		m.add(0);
		
		
		
		ArrayList<ArrayList<Integer>> j = permutationBuilder(m, 1);
		ArrayList<ArrayList<Integer>> j1 = permutationBuilderTotal(4);
		
		System.out.println(j1);
		
		ArrayList<ArrayList<Integer>> k = new ArrayList<ArrayList<Integer>>();
	
		
		int num[] = {0, 1, 2, 3};
		int counter =0;
		
		
		
		
		
		
		

	}

}
