package project;

import java.util.Set;

public interface Template {
	boolean checkCondition(Set<String>rule, Set<String>body, Set<String>head);
	void populateData(String command) throws Exception;
}
