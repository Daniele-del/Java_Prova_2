package applications;

import java.util.*;
import java.util.stream.Collectors;

public class Position {
	
	private String name;
	
	public List<String> skillForPosition = new ArrayList<>();
	
	public List<String> applicantsForPosition = new ArrayList<>();
	
	public Map<String, String> winnerForPosition = new HashMap<>();
	
	Position(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<String> getApplicants() {
		List<String> afp = applicantsForPosition.stream()
				.sorted((p1,p2) -> p1.compareTo(p2))
				.collect(Collectors.toList());
		
		return afp;
	}

	public String getWinner() {
		if(winnerForPosition.containsKey(name)) {return winnerForPosition.get(name);}
		else {return null;}
	}
	
	public List<String> getSkillForPosition(){
		return skillForPosition;
	}
}