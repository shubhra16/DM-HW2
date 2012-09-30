package project;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utility {
	
	public static Map<String, ArrayList<String>> readFile(String path)
	{
		Map<String, ArrayList<String>> mapToBeReturned=new LinkedHashMap<String, ArrayList<String>>();
		 try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream(path);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
				  List<String> dataInALine=new ArrayList<String>();
				  String delims="[\t]+";
				  String str[]= strLine.split(delims);
				  for(int i=1;i<str.length;i++)
					  if(str[i]!=null && str[i].length()!=0)
					  {
						  str[i].trim();
						  dataInALine.add(str[i]+i);
					  }
				  mapToBeReturned.put(str[0], (ArrayList<String>) dataInALine);
			  }
			  //Close the input stream
			  in.close();
			    }catch (Exception e){//Catch exception if any
			    	System.err.println("Error: " + e.getMessage());
			  }
		 return mapToBeReturned;
	}
	
	static void PrintAssociationSets( Set<Set<String>> sets, ArrayList<Set<String>> sample_sets, int level) {
		System.out.print(level + "\t" + sets.size() + "");
		//for(int index = 0 ; index < sets.size(); index++) {
		//	System.out.print(sets.get(index) + " : " + GetSetOccurenceCount(sets.get(index), sample_sets) + ", ");
		//}
		System.out.print("\n");
	}
	
	static ArrayList<Set<String>> GetSampleSets(Map<String, ArrayList<String>> data) {
		ArrayList<Set<String>> sample_sets = new ArrayList<Set<String>>();
		for(ArrayList<String> sample : data.values()) {
			Set<String> set = new HashSet<String>();
			for(String element : sample) {
				set.add(element);
			}
			sample_sets.add(set);
		}
		return sample_sets;
	}
	
	static Set<Set<String>> GetElementSets(ArrayList<Set<String>> sample_sets) {
		Set<String> super_set = new HashSet<String>();
		for(int index = 0 ; index < sample_sets.size() ; index++) {
			super_set.addAll(sample_sets.get(index));
		}
		Object[] elements = super_set.toArray();
		Set<Set<String>> sets = new HashSet<Set<String>>();
		for(int index = 0 ; index < elements.length ; index++) {
			Set<String> set = new HashSet<String>();
			set.add(elements[index].toString());
			sets.add(set);
		}
		return sets;
	}
	
	static Set<Set<String>> RemoveInfrequentSets(Set<Set<String>> sets, ArrayList<Set<String>> sample_sets, int support) {
		if(sets.size() == 0)
			return sets;
		
		int threshold = support * sample_sets.size() / 100;
		Iterator<Set<String>> itr = sets.iterator();
		while(itr.hasNext()) {
			int set_occurence_count = GetSetOccurenceCount((Set<String>)itr.next(), sample_sets);
			if(set_occurence_count < threshold)
				itr.remove();
		}
		
		return sets;
	}
	
	public static int GetSetOccurenceCount(Set<String> set, ArrayList<Set<String>> sample_sets) {
		int count = 0;
		for(int index = 0; index < sample_sets.size() ; index++) {
			if(sample_sets.get(index).containsAll(set))
				count++;
		}
		return count;
	}
	
	static Set<Set<String>> GetValidCombinations(Set<Set<String>> available_sets, int valid_super_set_size) {
		// Special case for clean main function
		if(valid_super_set_size == 1)
			return available_sets;
		
		Map<String, Set<String>> combinations = new HashMap<String, Set<String>>();
		
		Iterator<Set<String>> itr1 = available_sets.iterator();
		Iterator<Set<String>> itr2 = null;
		
		while(itr1.hasNext()) {
			Set<String> item1 = itr1.next();
			itr1.remove();
			itr2 = available_sets.iterator();
			while(itr2.hasNext()) {
				Set<String> item2 = itr2.next();
				
				Set<String> key = new HashSet<String>();
				key.addAll(item1);
				key.addAll(item2);
				if(key.size() != valid_super_set_size)
					continue;
				
				Set<String> value = new HashSet<String>();
				if(combinations.containsKey(key.toString()))
					value.addAll(combinations.get(key.toString()));
				value.add(item1.toString());
				value.add(item2.toString());
				
				combinations.put(key.toString(), value);
			}
		}
		
		Set<Set<String>> valid_combinations = new HashSet<Set<String>>();
		for(String key:combinations.keySet()) {
			valid_combinations.add(ConvertStringToSet(key));
		}
		
		return valid_combinations;
	}
	
	static Set<String> ConvertStringToSet(String str) {
		Set<String> set = new HashSet<String>();
		str = str.replace("[", "");
		str = str.replace("]", "");
		String tokens[] = str.split(", ");
		for(int index = 0 ; index < tokens.length ; index++) {
			set.add(tokens[index]);
		}
		return set;
	}
	
	public static Set<String> GetProceduresSet(Map<String, ArrayList<String>> totalData)
	{
		Set<String> procedureSet = new HashSet<String>();
		for(Map.Entry<String, ArrayList<String>> entryTotalData : totalData.entrySet())
		{
			for(int i = 0; i < entryTotalData.getValue().size()-1; i++)
				procedureSet.add(entryTotalData.getValue().get(i));
		}
		return procedureSet;
	}
	
	public static Set<String> GetDiagnosisSet(Map<String, ArrayList<String>> totalData)
	{
		Set<String> diagnosisSet = new HashSet<String>();
		for(Map.Entry<String, ArrayList<String>> entryTotalData : totalData.entrySet())
		{
			diagnosisSet.add(entryTotalData.getValue().get(entryTotalData.getValue().size()-1));
		}
		return diagnosisSet;
	}
	
	public static int GetIntersectionCount(Set<String> itemSet, Set<String> totalItemSet)
	{
		int count = 0;
		
		Iterator<String> itrOnItemSet = itemSet.iterator();
		while(itrOnItemSet.hasNext())
		{
			String item = itrOnItemSet.next();
			if(totalItemSet.contains(item))
				count++;
		}
		return count;
	}
	public static Set<Set<String>> RemoveFrequentSetsWithoutProcedureOrDiagnosis(Set<Set<String>> association_sets, Set<String> procedures_set, Set<String> diagnosis_set)
	{
		Iterator<Set<String>> itrOnAssociationSets = association_sets.iterator();
		
		while(itrOnAssociationSets.hasNext())
		{
			Set<String> itemSet = itrOnAssociationSets.next();
			int procedureCount = GetIntersectionCount(itemSet, procedures_set);
			int diagnosisCount = GetIntersectionCount(itemSet, diagnosis_set);
			
			if(procedureCount <= 0 || diagnosisCount <= 0)
				itrOnAssociationSets.remove();
		}
		return association_sets;
	}
	
	static Set<String> GetProcedureItems(Set<String> itemSet, Set<String> procedureSet)
	{
		Set<String> setOfProcedureItems = new HashSet<String>();
		
		Iterator<String> itrOnItemSet = itemSet.iterator();
		
		while(itrOnItemSet.hasNext())
		{
			String str = itrOnItemSet.next();
			if(procedureSet.contains(str))
				setOfProcedureItems.add(str);
		}
		return setOfProcedureItems;
	}
	
	public static Set<Set<String>> RemoveLowConfidenceSets(Set<Set<String>> association_sets, ArrayList<Set<String>> sample_sets, Set<String> procedure_set, int confidence)
	{
		Iterator<Set<String>> itrOnAssociationSets = association_sets.iterator();
		while(itrOnAssociationSets.hasNext())
		{
			Set<String> set = itrOnAssociationSets.next();
			int occCountOfSetOfProcDiagItem = GetSetOccurenceCount(set, sample_sets); 
			
			Set<String> setOfProcedureItem = GetProcedureItems(set, procedure_set);
			int occCountOfProcItem = GetSetOccurenceCount(setOfProcedureItem, sample_sets);
			
			if( (float)occCountOfSetOfProcDiagItem / occCountOfProcItem * 100 < confidence)
				itrOnAssociationSets.remove();
		}
		return association_sets;
	}	
	
	static Set<Set<String>> RemoveSetsNotSatisfyingTemplateRules(Set<Set<String>> frequent_sets, Set<String> procedure_set, Set<String> diagnosis_set, TemplateRules templates) {
		Iterator<Set<String>> itr = frequent_sets.iterator();
		while(itr.hasNext()) {
			Set<String> rule = itr.next();
			Set<String> body = GetProcedureItems(rule, procedure_set);
			Set<String> head = new HashSet<String> (rule);
			head.removeAll(body);
			
			if(templates.VerifyRules(rule, body, head) == false)
				itr.remove();
		}
		return frequent_sets;
	}
}
