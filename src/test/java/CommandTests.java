package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Paths;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

final class CommandTests {

  private GameServer server;

  // This method is automatically run _before_ each of the @Test methods
  @BeforeEach
  void setup() {
      server = new GameServer();
      // Create some test objects - will probably change this once reading dot file works
      Location testLoc1 = new Location("Location 1", "Test location");
      Location testLoc2 = new Location("Location 2", "Test location");
      Path testPath = new Path(testLoc1, testLoc2);
      Artefact testArte = new Artefact("Artefact 1", "Test artefact");
      Character testCharacter = new Character("Character 1", "Test character");
      Player testPlayer = new Player("Tester", testLoc1);
      testLoc1.addArtefact(testArte);
  }

  @Test
  void testGetCommand(){
    // TO ADD LATER: Player cannot pick up non-artefacts

    // Set up test objects
    Location testLoc1 = new Location("Location1", "Test location");
    Location testLoc2 = new Location("Location2", "Test location");
    Artefact testArte1 = new Artefact("Artefact1", "Test artefact");
    Artefact testArte2 = new Artefact("Artefact2", "Test artefact");
    Player testPlayer = new Player("Tester", testLoc1);
    testLoc1.addArtefact(testArte1);
    testLoc2.addArtefact(testArte2);

    // Valid input:
    String response1 = server.handleCommand("Daniel: get Artefact1");
    assertTrue(response1.contains("You picked up"), "No or incorrect response to Daniel: get Artefact1");
    assertTrue(response1.contains("Artefact1"), "Incorrect artefact in server response to Daniel: get Artefact1");
    assertFalse(testLoc1.getArtefacts().contains(testArte1), "Location still contains artefact after 'get'");
    assertTrue(testPlayer.listArtefacts().contains("Artefact1"), "Player inventory does not contain artefact after 'get'");

    // No artefact:
    String response2 = server.handleCommand("Daniel: get Excalibur");
    assertTrue(response2.contains("No such artefact"), "No or incorrect response to Daniel: 'get Excalibur'");

    // Artefact in different location
    String response3 = server.handleCommand("Daniel: get Artefact2");
    assertTrue(response3.contains("No such artefact"), "No or incorrect response to Daniel: get Artefact2");
    assertFalse(testPlayer.listArtefacts().contains("Artefact2"), "Player inventory updates after 'get' despite artefact in different location");
  }

  @Test
  void testGoToCommand(){
    // Set up test objects
    Location testLoc1 = new Location("Location1", "Test location");
    Location testLoc2 = new Location("Location2", "Test location");
    Location testLoc3 = new Location("Location3", "Test location");
    Path testPath = new Path(testLoc1, testLoc2);
    Player testPlayer = new Player("Tester", testLoc1);

    // Valid input:
    String response1 = server.handleCommand("Daniel: goto Location2");
    assertTrue(response1.contains("You move to"), "No or incorrect response to Daniel: goto Location2");
    assertTrue(response1.contains("Location2"), "Incorrect location in response to Daniel: goto Location2");
    assertEquals(testPlayer.getLocation(), testLoc2, "Player location does not change after 'goto'");

    // No path:
    String response2 = server.handleCommand("Daniel: goto Location3");
    assertTrue(response2.contains("You cannot move there"), "No or incorrect response to Daniel: goto Location3");
    assertEquals(testPlayer.getLocation(), testLoc2, "Player moves after 'goto' despite no path");

    // No location:
    String response3 = server.handleCommand("Daniel: goto Narnia");
    assertTrue(response3.contains("No such location"), "No or incorrect response to Daniel: goto Narnia");
    assertEquals(testPlayer.getLocation(), testLoc2, "Player moves after 'goto' despite invalid location");
  }

  @Test
  void testDropCommand(){
    // Set up test objects
    Location testLoc1 = new Location("Location1", "Test location");
    Player testPlayer = new Player("Tester", testLoc1);
    Artefact testArte = new Artefact("Artefact1", "Test artefact");
    testPlayer.addArtefact(testArte);

    // Valid input:
    String response1 = server.handleCommand("Daniel: drop Artefact1");
    assertTrue(response1.contains("You dropped"), "No or incorrect response to Daniel: drop Artefact1");
    assertFalse(testPlayer.listArtefacts().contains("Artefact1"), "Inventory still contains artefact after 'drop'");
    assertTrue(testLoc1.getArtefacts().contains(testArte), "Location does not contain artefact after 'drop'");

    // No artefact in inventory:
    String response2 = server.handleCommand("Daniel: drop PhilosophersStone");
    assertTrue(response2.contains("No such artefact"), "No or incorrect response to Daniel: drop PhilosophersStone");
  }

  @Test
  void testResetCommand(){
      /* TO ADD:
          - properly resets the game state
          - reloads the config file
       */
  }

  @Test
  void testLookCommand() {
    // TO ADD: 'Look' lists correct location, artefacts and paths.
    String response = server.handleCommand("Daniel: look");
    assertTrue(response.contains("The location you are currently in is"), "No location returned by `look`");
    assertTrue(response.contains("There are the following artefacts in this location"), "No artefacts returned by `look`");
    assertTrue(response.contains("There are paths to the following locations"), "No paths returned by `look`");
  }

  @Test
  void testInventoryCommand() {
    // TO ADD: Inventory lists correct items
    String response = server.handleCommand("Daniel: inv");
    assertTrue(response.contains("You have the following items in your inventory"), "Inventory not listed");
  }

}
