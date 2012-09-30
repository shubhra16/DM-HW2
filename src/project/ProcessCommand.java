package project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class ProcessCommand {
	TemplateRules returnTempplates(String command) throws Exception
	{
		TemplateRules templateRulesToBeReturned = new TemplateRules();
		String commandCopy=command;
		List<Template> templateToBeReturned=new ArrayList<Template>();
		List <String> separators=new ArrayList<String>();
		List <String> commands=new ArrayList<String>();
		String stringTillNow="";
		StringTokenizer words=new StringTokenizer(commandCopy," \t"); 
		while(words.hasMoreElements())
		{
			String currentWord=words.nextToken();
			if(currentWord.equalsIgnoreCase("and"))
			{
				separators.add("and");
				commands.add(stringTillNow);
				stringTillNow="";
			}
			else if(currentWord.equalsIgnoreCase("or"))
			{
				separators.add("or");
				commands.add(stringTillNow);
				stringTillNow="";
			}
			else
			{
				stringTillNow=stringTillNow+" "+currentWord;
			}
			
		}
		commands.add(stringTillNow);
		//System.out.println(separators);
		//System.out.println(commands);
		Iterator<String> commandItr=commands.iterator();
		while(commandItr.hasNext())
		{
			String currentCommand=commandItr.next();
			Template givenTemplate=TemplateFactory.returnTemplate(currentCommand);
			givenTemplate.populateData(currentCommand);
			templateToBeReturned.add(givenTemplate);
		}
		templateRulesToBeReturned.templates=(ArrayList<Template>)templateToBeReturned;
		templateRulesToBeReturned.logicalSeparator=(ArrayList<String>)separators;
		return templateRulesToBeReturned;
	}
	public static void main(String args[]) throws Exception
	{
		ProcessCommand ps=new ProcessCommand();
		ps.returnTempplates("HEAD has (1) OF (DISEASE) AND BODY HAS (NONE) OF (DISEASE)");
	}

}
