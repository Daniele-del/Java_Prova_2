package test;

import junit.framework.TestCase;
import applications.*;

import java.util.*;

public class TestR1 extends TestCase {
	HandleApplications ha;
	protected void setUp() throws Exception {
		super.setUp();
		ha = new HandleApplications();
		try{
			ha.addSkills("java", "pascal", "javascript", "sql", "html", "css", "xml", "sw design");
			ha.addPosition("developer", "pascal", "css");
			ha.addPosition("developer1", "java");
			ha.addPosition("senior developer", "pascal");
		}catch(Exception e){
            System.err.println("DUPLICATO");
			fail("no exception must be thrown");
		}
	}
	
	public void testSkillDuplicated() {
		try {
			ha.addSkills("php", "css", "ada"); //css duplicated
			fail("skill duplicated");
		} catch (ApplicationException e) {}
	}

	public void testGetSkill() {
		Skill skill = ha.getSkill("pascal");
		assertNotNull(skill);
		assertEquals("pascal",skill.getName());
	}
	
	public void testGetSkillEx1() {
		Skill skill = ha.getSkill("fortran");
		assertNull(skill);
	}
	

	public void testAddPositionsEx1() {
		try {
			ha.addPosition("developer","java");
			fail("position duplicated");
		} catch (ApplicationException e) {
			
		}
	}
	public void testAddPositionsEx2() {
		try {
			ha.addPosition("junior developer", "pascal", "fortran");
			fail("undefined skill in position");
		} catch (ApplicationException e) {
			
		}
	}
	public void testAddPositionsEx3() {
		try {
			ha.addPosition("senior developer", "pappa", "fortran");
			fail("undefined skill in position");
		} catch (ApplicationException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testGetPosition() {
		Position position = ha.getPosition("developer");
		assertNotNull(position);
		assertEquals("developer",position.getName());
	
	}
	public void testGetPositions() {
		Skill skill = ha.getSkill("pascal");
		assertTrue(skill != null);
		List<Position> positions = skill.getPositions();
		assertNotNull(positions);
		System.out.println(positions);
		assertEquals(2,positions.size());
		assertEquals("developer",positions.get(0).getName());
	}
	
}
