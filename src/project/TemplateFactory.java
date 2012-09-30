package project;

public class TemplateFactory {
	public static Template returnTemplate(String command)
	{
		Template templateToBeReturned;
		if(command.contains("sizeof")|| command.contains("SizeOf")|| command.contains("SIZEOF"))
		{
			templateToBeReturned=new Template2();
		}
		else
			templateToBeReturned=new Template1();
		return templateToBeReturned;
	}

}
