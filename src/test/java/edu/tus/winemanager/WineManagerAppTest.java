package edu.tus.winemanager;

import edu.tus.winemanager.service.WineXService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WineManagerAppTest {
    private WineXService wineService;

    @BeforeEach
    void setUp() {
        wineService = new WineXService();
    }

    @Test
    void testAddWine() {
        String wine = "Cabernet Sauvignon";
        wineService.addWine(wine);  // This should now work
        assertTrue(wineService.getAllWines().contains(wine));
    }


    @Test
    void testGetAllWines() {
        wineService.addWine("Merlot");
        List<String> wines = wineService.getAllWines();
        assertEquals(1, wines.size());
        assertEquals("Merlot", wines.get(0));
    }

    @Test
    void testRemoveWine() {
        String wine = "Pinot Noir";
        wineService.addWine(wine);
        assertTrue(wineService.removeWine(wine));
        assertFalse(wineService.getAllWines().contains(wine));
    }
}
