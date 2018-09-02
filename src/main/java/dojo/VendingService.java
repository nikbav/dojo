package dojo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.VendingMachine;

@Service
public class VendingService {

    @Autowired
    VendingMachine vendingMachine;

    @Autowired
    Validator voucherValidator;

    public List<Integer> acceptCoins() {
        final List<Integer> recievedCoins = this.vendingMachine.getCoins();
        final boolean isValidVoucher = this.voucherValidator.validate(recievedCoins);
        if (isValidVoucher) {
            return recievedCoins;
        }
        return null;
    }

    public int addCoins() {
        int voucherSum = 0;
        final List<Integer> voucher = this.acceptCoins();
        for (final Integer recievedCoins : voucher) {
            voucherSum += recievedCoins;
        }
        return voucherSum;
    }

    public String choseItem(final int itemId) {
        final Map<Integer, String> items = this.vendingMachine.getItems();
        for (final Map.Entry<Integer, String> entry : items.entrySet()) {
            if (entry.getKey() == itemId) {
                return entry.getValue();
            }
        }
        return null;
    }
}