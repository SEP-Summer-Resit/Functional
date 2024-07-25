package edu.uob;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionsFileParserTest {

    @Test
    void testParseActions() {
        String actionFile = "config" + File.separator + "actions.xml";
        List<Action> actions = ActionsFileParser.parseActions(actionFile);
        assertEquals(8, actions.size());
        assertEquals(Arrays.asList("open", "unlock"), actions.get(0).getTriggers());
        assertEquals(Arrays.asList("trapdoor", "key"), actions.get(0).getSubjects());
        assertEquals(Collections.singletonList("key"), actions.get(0).getConsumed());
        assertEquals(Collections.singletonList("cellar"), actions.get(0).getProduced());
        assertEquals("You unlock the door and see steps leading down into a cellar", actions.get(0).getNarration());

        assertEquals(Arrays.asList("chop", "cut", "cutdown"), actions.get(1).getTriggers());
        assertEquals(Arrays.asList("tree", "axe"), actions.get(1).getSubjects());
        assertEquals(Collections.singletonList("tree"), actions.get(1).getConsumed());
        assertEquals(Collections.singletonList("log"), actions.get(1).getProduced());
        assertEquals("You cut down the tree with the axe", actions.get(1).getNarration());

        assertEquals(Collections.singletonList("drink"), actions.get(2).getTriggers());
        assertEquals(Collections.singletonList("potion"), actions.get(2).getSubjects());
        assertEquals(Collections.singletonList("potion"), actions.get(2).getConsumed());
        assertEquals(Collections.singletonList("health"), actions.get(2).getProduced());
        assertEquals("You drink the potion and your health improves", actions.get(2).getNarration());

        assertEquals(Arrays.asList("fight", "hit", "attack"), actions.get(3).getTriggers());
        assertEquals(Collections.singletonList("elf"), actions.get(3).getSubjects());
        assertEquals(Collections.singletonList("health"), actions.get(3).getConsumed());
        assertEquals(Collections.emptyList(), actions.get(3).getProduced());
        assertEquals("You attack the elf, but he fights back and you lose some health", actions.get(3).getNarration());

        assertEquals(Collections.singletonList("pay"), actions.get(4).getTriggers());
        assertEquals(Arrays.asList("elf", "coin"), actions.get(4).getSubjects());
        assertEquals(Collections.singletonList("coin"), actions.get(4).getConsumed());
        assertEquals(Collections.singletonList("shovel"), actions.get(4).getProduced());
        assertEquals("You pay the elf your silver coin and he produces a shovel", actions.get(4).getNarration());

        assertEquals(Collections.singletonList("bridge"), actions.get(5).getTriggers());
        assertEquals(Arrays.asList("log", "river"), actions.get(5).getSubjects());
        assertEquals(Collections.singletonList("log"), actions.get(5).getConsumed());
        assertEquals(Collections.singletonList("clearing"), actions.get(5).getProduced());
        assertEquals("You bridge the river with the log and can now reach the other side", actions.get(5).getNarration());

        assertEquals(Collections.singletonList("dig"), actions.get(6).getTriggers());
        assertEquals(Arrays.asList("ground", "shovel"), actions.get(6).getSubjects());
        assertEquals(Collections.singletonList("ground"), actions.get(6).getConsumed());
        assertEquals(Arrays.asList("hole", "gold"), actions.get(6).getProduced());
        assertEquals("You dig into the soft ground and unearth a pot of gold !!!", actions.get(6).getNarration());

        assertEquals(Collections.singletonList("blow"), actions.get(7).getTriggers());
        assertEquals(Collections.singletonList("horn"), actions.get(7).getSubjects());
        assertEquals(Collections.emptyList(), actions.get(7).getConsumed());
        assertEquals(Collections.singletonList("lumberjack"), actions.get(7).getProduced());
        assertEquals("You blow the horn and as if by magic, a lumberjack appears !", actions.get(7).getNarration());
    }
}