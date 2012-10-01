package project;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class Template2 implements Template{
	String setType;
	String condition;
	Set<String> items;

	//template2
	public boolean checkCondition(Set<String> ruleSet, Set<String> bodySet, Set<String> headSet)
	{
		//rule : procedure and diagnosis set
		//body: procedure set
		//head: diagnosis set
	
		boolean result = false;
		int sizeOfset = 0;
		
		Set<String> requiredSet = null;
		
		if(setType.equalsIgnoreCase("Rule"))
			requiredSet = ruleSet;
		else if(setType.equalsIgnoreCase("body"))
			requiredSet = bodySet;
		else if(setType.equalsIgnoreCase("head"))
			requiredSet = headSet;
			
		sizeOfset = requiredSet.size();
		
		Iterator<String> itr = items.iterator();
		String str = itr.next();
		if(condition.equalsIgnoreCase(">="))
		{
			int number = Integer.parseInt(str);
			if(sizeOfset >= number)
				result = true;
		}		
		
		return result;
	}

	@Override
	public void populateData(String command) throws Exception {
		command = command.trim();
		String tokens[] = command.split("[ \t()]");
		
		// Check size
		if(tokens.length != 5)
			throw new Exception("Syntax Error");
		
		// Check has
		if(! (tokens[0].equalsIgnoreCase("SizeOf")) )
			throw new Exception("Syntax Error");
				
		// Check head/body/rule
		setType = tokens[1];
		if(! (setType.equalsIgnoreCase("HEAD") || setType.equalsIgnoreCase("BODY") || setType.equalsIgnoreCase("RULE")) )
			throw new Exception("Syntax Error");
		
		// Skip null
		
		// Check condition
		condition = tokens[3];
		if(! (condition.equalsIgnoreCase(">=")) )
			throw new Exception("Syntax Error");
		
		// Check item
		items=new HashSet<String>();
		items.add(tokens[4]);
	}
}
