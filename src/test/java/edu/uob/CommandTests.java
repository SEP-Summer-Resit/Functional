package edu.uob;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
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
  }
/*
  @Test
  void testDropCommand(){
    // Set up test objects
    Location testLoc1 = new Location("Location1", "Test location");
    Player testPlayer = new Player(testLoc1);
    Artefact testArte = new Artefact("Artefact1", "Test artefact");
    testPlayer.addArtefact(testArte);

    // Valid input:
    String response1 = server.handleCommand("Daniel: drop Artefact1");
    assertTrue(response1.contains("You dropped"), "Incorrect or no response to Daniel: drop Artefact1");
    assertFalse(testPlayer.listArtefacts().contains("Artefact1"), "Inventory still contains artefact after 'drop'");
    assertTrue(testLoc1.getArtefacts().contains(testArte), "Location does not contain artefact after 'drop'");

    // No artefact in inventory:
    String response2 = server.handleCommand("Daniel: drop PhilosophersStone");
    assertTrue(response2.contains("No such artefact"), "Incorrect or no response to Daniel: drop PhilosophersStone");
  }
  */
  @Test
  void testResetCommand(){
      /* TO ADD:
          - properly resets the game state
          - reloads the config file
       */
  }
/*
  @Test
  void testLookCommand() throws FileNotFoundException, ParseException {
    // TO ADD: 'Look' lists correct location, artefacts and paths.
    String response = server.handleCommand("Daniel: look");
    assertTrue(response.contains("The location you are currently in is"), "No location returned by look");
    assertTrue(response.contains("There are the following artefacts in this location"), "No artefacts returned by look");
    assertTrue(response.contains("There are paths to the following locations"), "No paths returned by look");
  }

  @Test
  void testInventoryCommand() throws FileNotFoundException, ParseException {
    // TO ADD: Inventory lists correct items
    String response = server.handleCommand("Daniel: inv");
    assertTrue(response.contains("You have the following items in your inventory"), "Inventory not listed");
  }
 */
}
