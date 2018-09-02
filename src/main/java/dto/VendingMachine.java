package dto;

import java.util.List;
import java.util.Map;

public class VendingMachine {
    private List<Integer> coins;
    private Map<Integer, String> items;

    public Map<Integer, String> getItems() {
        return this.items;
    }

    public List<Integer> getCoins() {
        return this.coins;
    }

}
