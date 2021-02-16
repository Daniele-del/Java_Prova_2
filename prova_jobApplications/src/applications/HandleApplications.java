package applications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HandleApplications {

	public Map<String, Skill> skills = new HashMap<>();

	public Map<String, Position> positions = new HashMap<>();

	public Map<String, String> applications = new HashMap<>();

	public Map<String, String> positionApplicant = new HashMap<>();

	public Map<String, String> positionWinner = new HashMap<>();

	public Map<String, Map<String, Integer>> applicantSkillVote = new HashMap<>();

	public List<String> skill = new ArrayList<>();

	public void addSkills(String... names) throws ApplicationException {
		for (int i = 0; i < names.length; i++) {
			Skill skill = new Skill(names[i]);
			if (skills.containsKey(names[i])) {
				throw new ApplicationException();
			} else {
				skills.put(names[i], skill);
			}
		}
	}

	public void addPosition(String name, String... skillNames) throws ApplicationException {
		Position position = new Position(name);
		if (positions.containsKey(name)) {
			throw new ApplicationException();
		}
		for (int i = 0; i < skillNames.length; i++) {
			if (skills.containsKey(skillNames[i])) {
				position.getSkillForPosition().add(skillNames[i]);
			} else {
				throw new ApplicationException();
			}
		}
		positions.put(name, position);
		for (int i = 0; i < skillNames.length; i++) {
			if (skills.containsKey(skillNames[i])) {
				skills.get(skillNames[i]).getPositionForSkill().add(position);
			}
		}
	}

	public Skill getSkill(String name) {
		if (skills.containsKey(name)) {
			return skills.get(name);
		} else {
			return null;
		}
	}

	public Position getPosition(String name) {
		if (positions.containsKey(name)) {
			return positions.get(name);
		} else {
			return null;
		}
	}

	public void addApplicant(String name, String capabilities) throws ApplicationException {
		Map<String, Integer> skillVote = new HashMap<>();
		int vote = 0;
		if (applications.containsKey(name)) {
			throw new ApplicationException();
		}
		String[] split = capabilities.split(",");
		String[] split1 = capabilities.split(",|:");
		for (int i = 0, j = 1; i < split1.length && j < split1.length; i = i + 2, j = j + 2) {
			if (skills.containsKey(split1[i]) && split1[j].matches("[1-9]|10")) {
				vote = Integer.parseInt(split1[j]);
				skillVote.put(split1[i], vote);
				skill.add(split1[i]);
				continue;
			} else {
				throw new ApplicationException();
			}
		}
		String capability = Arrays.stream(split).sorted().collect(Collectors.joining(","));

		applications.put(name, capability);
		applicantSkillVote.put(name, skillVote);
	}

	public String getCapabilities(String applicantName) throws ApplicationException {
		if (!applications.containsKey(applicantName)) {
			throw new ApplicationException();
		}
		if (applications.get(applicantName) != null) {
			return applications.get(applicantName);
		} else {
			return "";
		}

	}

	public void enterApplication(String applicantName, String positionName) throws ApplicationException {
		boolean flag = false;
		if (!applications.containsKey(applicantName)) {
			throw new ApplicationException();
		}
		if (!positions.containsKey(positionName)) {
			throw new ApplicationException();
		}
		for (String skill : positions.get(positionName).skillForPosition) {
			for (String applicantSkill : applicantSkillVote.get(applicantName).keySet()) {
				if (applicantSkill.equals(skill)) {
					flag = true;
				}
			}
			if (flag == false) {
				throw new ApplicationException();
			}
			flag = false;
		}
		if (positionApplicant.containsKey(applicantName)) {
			throw new ApplicationException();
		} else {
			positionApplicant.put(applicantName, positionName);
			positions.get(positionName).applicantsForPosition.add(applicantName);
		}

	}

	public int setWinner(String applicantName, String positionName) throws ApplicationException {
		int sum = 0;
		if (!positionApplicant.containsKey(applicantName)) {
			throw new ApplicationException();
		}
		if (positionWinner.get(positionName) != null) {
			throw new ApplicationException();
		}
		if (!positions.get(positionName).getApplicants().contains(applicantName)) {
			throw new ApplicationException();
		}
		for (String skill : positions.get(positionName).skillForPosition) {
			sum = sum + applicantSkillVote.get(applicantName).get(skill);
		}
		if (sum / positions.get(positionName).skillForPosition.size() >= 6) {
			positionWinner.put(positionName, applicantName);
			positions.get(positionName).winnerForPosition.put(positionName, applicantName);
			return sum;
		} else {
			throw new ApplicationException();
		}
	}

	public SortedMap<String, Long> skill_nApplicants() {
		Map<String, Long> maxOrdered = new TreeMap<>();

		Map<String, Long> max = skill.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		maxOrdered.putAll(max);

		return (SortedMap<String, Long>) maxOrdered;
	}

	public String maxPosition() {
		Map<String, Long> max = positionApplicant.values().stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		String maxPos = Collections.max(max.entrySet(), Map.Entry.comparingByValue()).getKey();

		return maxPos;
	}
}
