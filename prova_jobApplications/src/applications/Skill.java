package applications;

import java.util.*;
import java.util.stream.Collectors;

public class Skill {
	
	private String name;
	
	public List<Position> positionForSkill = new ArrayList<>();
	
	Skill(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Position> getPositions() {
		List<Position> pfs = positionForSkill.stream()
				.sorted((p1,p2) -> p1.getName().compareTo(p2.getName()))
				.collect(Collectors.toList());

		return pfs;
	}
	
	public List<Position> getPositionForSkill(){
		return positionForSkill;
	}
}