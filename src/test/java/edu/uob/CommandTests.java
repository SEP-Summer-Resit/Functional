package edu.uob;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

//import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.alexmerz.graphviz.ParseException;

final class CommandTests {

  private GameServer server;

  // This method is automatically run _before_ each of the @Test methods
  @BeforeEach
  void setup() throws FileNotFoundException, ParseException {
      this.server = new GameServer("testEntities.dot");
  }
  
  @Test
  void testGetCommand() throws FileNotFoundException, ParseException {
    // Setup:
    Location testLoc1 = server.getLocationByName("testLoc1");
    Location testLoc2 = server.getLocationByName("testLoc2");
    Artefact testArte1 = testLoc1.getArtefacts().get(0);
    Furniture testFurn1 = testLoc1.getFurniture().get(0);
    Character testChar1 = testLoc1.getCharacters().get(0);
    Artefact testArte2 = testLoc2.getArtefacts().get(0);
    Player testPlayer = server.getPlayer();

    // Valid input:
    String response1 = server.handleCommand("Daniel: get testArte1");
    assertTrue(response1.contains("You picked up the "), "Incorrect or no response to Daniel: get Artefact1");
    assertTrue(response1.contains("testArte1"), "Incorrect artefact in server response to Daniel: get Artefact1");
    assertFalse(testLoc1.getArtefacts().contains(testArte1), "Location still contains artefact after 'get'");
    assertTrue(testPlayer.listArtefacts().contains("testArte1"), "Player inventory does not contain artefact after 'get'");

    // No artefact:
    String response2 = server.handleCommand("Daniel: get Excalibur");
    assertTrue(response2.contains("No such artefact here. Are you sure you spelled it correctly?"), "Incorrect or no response to Daniel: 'get Excalibur'");

    // Artefact in different location
    String response3 = server.handleCommand("Daniel: get testArte2");
    assertTrue(response3.contains("No such artefact here. Are you sure you spelled it correctly?"), "Incorrect or no response to Daniel: get testArte2");
    assertTrue(testLoc2.getArtefacts().contains(testArte2), "Artefact incorrectly removed from different location after 'get'");
    assertFalse(testPlayer.listArtefacts().contains("testArte2"), "Player inventory updates after 'get' despite artefact in different location");

    // Try to pick up furniture:
    String response4 = server.handleCommand("Daniel: get testFurn1");
    assertTrue(response4.contains("No such artefact here. Are you sure you spelled it correctly?"), "Incorrect or no response to Daniel: get testFurn1");
    assertTrue(testLoc1.getFurniture().contains(testFurn1), "Furniture incorrectly removed from location after 'get'");
    assertFalse(testPlayer.listArtefacts().contains("testFurn1"), "Player inventory updates after 'get' despite furniture input");

    // Try to pick up character:
    String response5 = server.handleCommand("Daniel: get testChara1");
    assertTrue(response5.contains("No such artefact here. Are you sure you spelled it correctly?"), "Incorrect or no response to Daniel: get testChara1");
    assertTrue(testLoc1.getCharacters().contains(testChar1), "Character incorrectly removed from location after 'get'");
    assertFalse(testPlayer.listArtefacts().contains("testChara1"), "Player inventory updates after 'get' despite character input");

    // No artefact given:
    String response6 = server.handleCommand("Daniel: get");
    assertTrue(response6.contains("The command format seems off. Please use the correct format. Example: 'get sword', 'goto forest'."));
  }

  @Test
  void testGoToCommand() throws FileNotFoundException, ParseException {
    // Setup:
    Location testLoc1 = server.getLocationByName("testLoc1");
    Location testLoc2 = server.getLocationByName("testLoc2");
    Player testPlayer = server.getPlayer();

    // No path:
    String response1 = server.handleCommand("Daniel: goto testLoc3");
    assertTrue(response1.contains("There is no path to the specified location. Try a different route."), "Incorrect or no response to Daniel: goto Location3");
    assertEquals(testPlayer.getLocation(), testLoc1, "Player moves after 'goto' despite no path");

    // Same location:
    String response2 = server.handleCommand("Daniel: goto testLoc1");
    assertTrue(response2.contains("You are already here!"), "Incorrect or no response when using 'goto' to same location");
    assertEquals(testPlayer.getLocation(), testLoc1, "Player moves after 'goto' despite already being in location");

    // No location:
    String response3 = server.handleCommand("Daniel: goto Narnia");
    assertTrue(response3.contains("There is no such location. Perhaps you meant something else?"), "Incorrect or no response to Daniel: goto Narnia");
    assertEquals(testPlayer.getLocation(), testLoc1, "Player moves after 'goto' despite invalid location");

    // Valid input:
    String response4 = server.handleCommand("Daniel: goto testLoc2");
    assertTrue(response4.contains("You moved to "), "Incorrect or no response to Daniel: goto testLoc2");
    assertTrue(response4.contains("testLoc2"), "Incorrect location in response to Daniel: goto testLoc2");
    assertEquals(testPlayer.getLocation(), testLoc2, "Player location does not change after 'goto'");

    // No location specified:
    String response5 = server.handleCommand("Daniel: goto");
    assertTrue(response5.contains("The command format seems off. Please use the correct format. Example: 'get sword', 'goto forest'."));
  }

  @Test
  void testDropCommand() throws FileNotFoundException, ParseException {
    // Setup:
    Location testLoc1 = server.getLocationByName("testLoc1");
    Player testPlayer = server.getPlayer();
    Artefact testArte4 = new Artefact("testArte4", "Test artefact");
    testPlayer.addArtefact(testArte4);

    // Valid input:
    String response1 = server.handleCommand("Daniel: drop testArte4");
    assertTrue(response1.contains("You dropped the testArte4"), "Incorrect or no response to Daniel: drop testArte4");
    assertFalse(testPlayer.listArtefacts().contains("testArte4"), "Inventory still contains artefact after 'drop'");
    assertTrue(testLoc1.getArtefacts().contains(testArte4), "Location does not contain artefact after 'drop'");

    // Artefact not in inventory
    String response2 = server.handleCommand("Daniel: drop testArte4");
    assertTrue(response2.contains("You don't have such an artefact. Double-check your inventory."));

    // Invalid artefact input:
    String response3 = server.handleCommand("Daniel: drop PhilosophersStone");
    assertTrue(response3.contains("You don't have such an artefact. Double-check your inventory."), "Incorrect or no response to Daniel: drop PhilosophersStone");

    // No artefact given:
    String response4 = server.handleCommand("Daniel: drop");
    assertTrue(response4.contains("The command format seems off. Please use the correct format. Example: 'get sword', 'goto forest'."));
  }

  @Disabled
  @Test
  void testResetCommand() throws FileNotFoundException, ParseException {
    // Setup:
    Location testLoc1 = server.getLocationByName("testLoc1");
    Location testLoc2 = server.getLocationByName("testLoc2");
    Location testLoc3 = server.getLocationByName("testLoc3");
    Player testPlayer = server.getPlayer();
    Artefact testArte4 = new Artefact("testArte4", "Test artefact");
    testLoc1.addArtefact(testArte4);
    Furniture testFurn4 = new Furniture("testFurn4", "Test Furniture");
    testLoc1.addFurniture(testFurn4);
    Character testChara4 = new Character("testChar4", "Test Character");
    testLoc1.addCharacter(testChara4);
    testLoc1.removeArtefact("testArte1");
    testLoc1.createPath(testLoc3);
    testPlayer.setLocation(testLoc2);
    // Command:
    String response = server.handleCommand("Daniel: reset");
    assertTrue(response.contains("Game has been reset. All your progress is lost, but a new adventure begins!"), "Incorrect or no response to Daniel: reset");
    Location newTestLoc1 = server.getLocationByName("testLoc1");
    Location newTestLoc2 = server.getLocationByName("testLoc2");
    Location newTestLoc3 = server.getLocationByName("testLoc3");
    Player newTestPlayer = server.getPlayer();
    assertNotEquals(newTestLoc1, testLoc1, "Location did not reset after 'reset'");
    assertNotEquals(newTestLoc2, testLoc2, "Location did not reset after 'reset'");
    assertNotEquals(newTestLoc3, testLoc3, "Location did not reset after 'reset'");
    assertNotEquals(newTestPlayer, testPlayer, "Player did not reset after 'reset'");
    assertFalse(newTestLoc1.getArtefacts().contains(testArte4), "Artefact added to location not removed after 'reset'");
    assertFalse(newTestLoc1.getFurniture().contains(testFurn4), "Furniture added to location not removed after 'reset'");
    assertFalse(newTestLoc1.getCharacters().contains(testChara4), "Character added to location not removed after 'reset'");
    assertFalse(newTestLoc1.pathExists(newTestLoc3), "Paths not reset after 'reset'");
    assertEquals(newTestPlayer.getLocation(), newTestLoc1, "Player location did not reset after 'reset'");
  }

  @Test
  void testLookCommand() throws FileNotFoundException, ParseException {
    // Setup:
    Location testLoc1 = server.getLocationByName("testLoc1");
    // Command:
    String response = server.handleCommand("Daniel: look");
    assertTrue(response.contains("You are currently in: "), "No location returned by look");
    assertTrue(response.contains("You see the following artefacts here: "), "No artefacts returned by look");
    assertTrue(response.contains("You notice the following furniture: "), "No furniture returned by look");
    assertTrue(response.contains("You see the following characters: "), "No characters returned by look");
    assertTrue(response.contains("There are paths leading to: "), "No paths returned by look");
    assertTrue(response.contains(testLoc1.getName()), "Incorrect location returned by look");
    for (Artefact artefact : testLoc1.getArtefacts()) {
      assertTrue(response.contains(artefact.getName()), "Incorrect artefacts returned by look");
    }
    for (Furniture furniture : testLoc1.getFurniture()) {
      assertTrue(response.contains(furniture.getName()), "Incorrect furniture returned by look");
    }
    for (Character character : testLoc1.getCharacters()) {
      assertTrue(response.contains(character.getName()), "Incorrect characters returned by look");
    }
    for (Path path : testLoc1.getPaths()) {
      assertTrue(response.contains(path.getEnd().getName()), "Incorrect paths returned by look");
    }
  }

  @Test
  void testInventoryCommand() throws FileNotFoundException, ParseException {
    // Setup:
    Player testPlayer = server.getPlayer();
    Artefact testArte4 = new Artefact("testArte4", "Test artefact");
    Artefact testArte5 = new Artefact("testArte5", "Test artefact");
    testPlayer.addArtefact(testArte4);
    testPlayer.addArtefact(testArte5);
    // Command:
    String response = server.handleCommand("Daniel: inv");
    assertTrue(response.contains("You are carrying: "), "Inventory not listed by 'inv'");
    assertTrue(response.contains(testArte4.getName()) && response.contains(testArte5.getName()), "Incorrect artefacts listed by 'inv'");
  }

}
