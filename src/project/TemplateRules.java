package project;

import java.util.ArrayList;
import java.util.Set;

public class TemplateRules {

	ArrayList<Template> templates;
	ArrayList<String> logicalSeparator;
	
	boolean VerifyRules(Set<String> rule, Set<String> body, Set<String> head) {
		if(templates.size() == 0)
			return true;
	
		boolean verified_so_far = true;
		
		for(int template_index = 0 ; template_index < templates.size() && verified_so_far == true; template_index++) {
			boolean is_verified = templates.get(template_index).checkCondition(rule, body, head);
			if(template_index == 0)
				verified_so_far = is_verified;
			else
				verified_so_far = ApplyLogicalSeparator(verified_so_far, is_verified, logicalSeparator.get(template_index-1));
		}
		return verified_so_far;
	}
	
	boolean ApplyLogicalSeparator(boolean flag1, boolean flag2, String separator) {
		boolean result = false;
		if(separator.equals("and")) {
			result =  flag1 && flag2;
		}
		else if(separator.equals("or")) {
			result = flag1 || flag2;
		}
		return result;
	}
}
