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
		StringTokenizer currentElementToken=new StringTokenizer(command," \t()");
		int count=1;
		while(currentElementToken.hasMoreElements())
		{
			String currentElement=currentElementToken.nextToken();
			if (currentElement.equals(" ") || currentElement.equalsIgnoreCase("SizeOf"))
				continue;
			
			currentElement=currentElement.replaceAll(" ", "");
			if(count==1)
			{
				if(currentElement.equalsIgnoreCase("rule")||currentElement.equalsIgnoreCase("body")||currentElement.equalsIgnoreCase("head"))
				{
					setType=currentElement;
				}
				else throw new Exception("Syntax Error");
				
			}
			if(count==2)
			{
				if(!(currentElement.equalsIgnoreCase(">=")))
				{
					throw new Exception("Syntax Error");
				}
				condition=currentElement;
									
			}
			if(count==3)
			{
				String strItems=currentElement;
				String str[]=strItems.split(",");
				for(int i=0;i<str.length;i++)
				{
					items=new HashSet<String>();
					items.add(str[i]);
				}
				
			}
			count++;
			  
		}
	}

}
