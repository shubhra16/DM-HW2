package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainBody {
	public static void main(String args[]) {
		int max_level = 10000;
		int support = 30;
		int confidence = 50;
		Map<String, ArrayList<String>> totalData=Utility.readFile("./association-rule-test-data.txt");
		
		// Get sample and element sets
		ArrayList<Set<String>> sample_sets = Utility.GetSampleSets(totalData);
		Set<String> procedures_set = Utility.GetProceduresSet(totalData);
		Set<String> diagnosis_set = Utility.GetDiagnosisSet(totalData);
		Set<Set<String>> association_sets = Utility.GetElementSets(sample_sets);
		Set<Set<String>> frequent_sets = new HashSet<Set<String>>();
		
		// Process sets
		for(int level = 1 ; level <= max_level ; level++) {
			association_sets = Utility.GetValidCombinations(association_sets, level);
			association_sets = Utility.RemoveInfrequentSets(association_sets,sample_sets, support);
			if(level > 1) {
				association_sets = Utility.RemoveFrequentSetsWithoutProcedureOrDiagnosis(association_sets, procedures_set, diagnosis_set);
				association_sets = Utility.RemoveLowConfidenceSets(association_sets, sample_sets, procedures_set, confidence);
			}
			Utility.PrintAssociationSets(association_sets, sample_sets, level);
			frequent_sets.addAll(association_sets);
			if(association_sets.size() == 0)
				break;
		}
		
		// Apply template rules
		TemplateRules templates;
		frequent_sets = Utility.RemoveSetsNotSatisfyingTemplateRules(frequent_sets, procedures_set, diagnosis_set, templates);
		
		System.out.print("\nTotal number of frequent sets : " + frequent_sets.size());
	}
}
