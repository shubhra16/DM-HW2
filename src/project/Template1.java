package project;

import java.util.HashSet;
import java.util.List;
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
				for(int i=0;i<str.length;i++)
				{
					items=new HashSet<String>();
					items.add(str[i]);
				}
				
			}
			count++;
			  
		}
			
	}
	
	@Override
	public boolean checkCondition() {
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String args[])
	{
	  
	}
	

}
