package project;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Template1 implements Template{
	String setType;
	String condition;
	Set<String> items;

	public void populateData(String command) throws Exception {
		command = command.trim();
		String tokens[] = command.split("[ \t]", 5);
		
		// Check size
		if(tokens.length != 5)
			throw new Exception("Syntax Error");
		
		// Check head/body/rule
		setType = tokens[0];
		if(! (setType.equalsIgnoreCase("HEAD") || setType.equalsIgnoreCase("BODY") || setType.equalsIgnoreCase("RULE")) )
			throw new Exception("Syntax Error");
		
		// Check has
		if(! (tokens[1].equalsIgnoreCase("has")) )
			throw new Exception("Syntax Error");
		
		// Check condition
		condition = tokens[2].split("[()]")[1];
		if(! (condition.equalsIgnoreCase("ANY") || condition.equalsIgnoreCase("NONE")) ) {
			try {
				Integer.parseInt(condition);
			}
			catch(Exception e) {
				throw new Exception("Syntax Error");
			}
		}
		
		// Check of
		if(! (tokens[3].equalsIgnoreCase("of")) )
			throw new Exception("Syntax Error");
		
		// Check items
		String items_tokens[]=tokens[4].split("[(),]");
		items=new HashSet<String>();
		for(int i=1;i<items_tokens.length;i++)
		{
			items.add(items_tokens[i].trim());
		}	
	}
	
	//template1
	public boolean checkCondition(Set<String> ruleSet, Set<String> bodySet, Set<String> headSet)
	{
		//rule : procedure and diagnosis set
		//body: procedure set
		//head: diagnosis set
		
		boolean result = false;
		Set<String> requiredSet = null;
		
		if(setType.equalsIgnoreCase("RULE"))
			requiredSet = ruleSet;
		else if(setType.equalsIgnoreCase("BODY"))
			requiredSet = bodySet;
		else if(setType.equalsIgnoreCase("HEAD"))
			requiredSet = headSet;
		
		int count = Utility.GetIntersectionCount(items,requiredSet);
		
		if(condition.equalsIgnoreCase("any"))
		{
			if(count > 0)
				result = true;
		}
		else if(condition.equalsIgnoreCase("none"))
		{
			if(count == 0)
				result = true;
		}	
		else
		{
			int number = Integer.parseInt(condition);
			
			if(count == number)
				result = true;
		}
		
		return result;
	}
	public static void main(String args[])
	{
	  
	}
}
