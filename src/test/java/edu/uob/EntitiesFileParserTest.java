package edu.uob;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class EntitiesFileParserTest {

    @Test
    public void testDatFileParser() {
        String entityFile = "config" + File.separator + "entities.dot";
        GameGraph gameGraph = EntitiesFileParser.parseGameGraph(entityFile);
        assertEquals(GameGraphNode.class, gameGraph.getNode("cabin").getClass());
        assertEquals(GameGraphNode.class, gameGraph.getNode("forest").getClass());
        assertEquals(GameGraphNode.class, gameGraph.getNode("riverbank").getClass());
        assertEquals(GameGraphNode.class, gameGraph.getNode("clearing").getClass());
        assertEquals(GameGraphNode.class, gameGraph.getNode("storeroom").getClass());
        assertEquals(GameGraphNode.class, gameGraph.getNode("cellar").getClass());
        assertEquals(3, gameGraph.getNode("cabin").getLocationEntity().getArtefacts().size());
        assertEquals(1, gameGraph.getNode("cabin").getLocationEntity().getFurniture().size());
        assertEquals(1, gameGraph.getNode("forest").getLocationEntity().getArtefacts().size());
        assertEquals(1, gameGraph.getNode("forest").getLocationEntity().getFurniture().size());
        assertEquals(1, gameGraph.getNode("cellar").getLocationEntity().getCharacters().size());
        assertEquals(1, gameGraph.getNode("riverbank").getLocationEntity().getArtefacts().size());
        assertEquals(1, gameGraph.getNode("riverbank").getLocationEntity().getFurniture().size());
        assertEquals(1, gameGraph.getNode("clearing").getLocationEntity().getFurniture().size());
        assertEquals(3, gameGraph.getNode("storeroom").getLocationEntity().getArtefacts().size());
        assertEquals(1, gameGraph.getNode("storeroom").getLocationEntity().getFurniture().size());
        assertEquals(1, gameGraph.getNode("storeroom").getLocationEntity().getCharacters().size());
        assertEquals(1, gameGraph.getToNodes("cabin").size());
        assertEquals(2, gameGraph.getFromNodes("cabin").size());
        assertEquals(2, gameGraph.getToNodes("forest").size());
        assertEquals(2, gameGraph.getFromNodes("forest").size());
        assertEquals(1, gameGraph.getToNodes("cellar").size());
        assertEquals(1, gameGraph.getToNodes("riverbank").size());
        assertEquals(2, gameGraph.getFromNodes("riverbank").size());
        assertEquals(1, gameGraph.getToNodes("clearing").size());
    }

}