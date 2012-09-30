package project;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Template1 implements Template{
	String setType;
	String condition;
	Set<String> items;

	@Override
	public void populateData(String command) throws Exception {
		StringTokenizer currentElementToken=new StringTokenizer(command," \t()");
		int count=1;
		while(currentElementToken.hasMoreElements())
		{
			String currentElement=currentElementToken.nextToken();
			if (currentElement.equals(" "))
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
				if(!(currentElement.equalsIgnoreCase("has")))
				{
					throw new Exception("Syntax Error");
				}
									
			}
			if(count==3)
			{
				
				condition=currentElement;
				
			}
			if(count==4)
			{
				if(!(currentElement.equalsIgnoreCase("of")))
				{
					throw new Exception("Syntax Error");
				}
				
					
			}
			if(count==5)
			{
				String strItems=currentElement;
				String str[]=strItems.split(",");
				items=new HashSet<String>();
				for(int i=0;i<str.length;i++)
				{
					items.add(str[i]);
				}
				
			}
			count++;
			  
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
		
		if(setType.equalsIgnoreCase("Rule"))
			requiredSet = ruleSet;
		else if(setType.equalsIgnoreCase("body"))
			requiredSet = bodySet;
		else if(setType.equalsIgnoreCase("head"))
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
