package dojo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import dto.VendingMachine;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class VendingServiceTest {

    private static final Integer[] ACCEPTABLE_COINS = { 5, 10, 20, 50 };
    private static final Integer[] ACCEPTABLE_ITEMS_ID = { 6, 7, 8 };

    @InjectMocks
    VendingService vendingService;

    @Mock
    VendingMachine vendingMachineMock;

    @Mock
    Validator voucherValidatorMock;

    @Mock
    CoinCache coinCacheMock;

    @Test
    public void testAcceptCoin() {

        // Given
        final List<Integer> recievedCoins = Arrays.asList(10, 20);
        final List<Integer> acceptableCoins = Arrays.asList(ACCEPTABLE_COINS);

        BDDMockito.given(this.vendingMachineMock.getCoins()).willReturn(recievedCoins);
        BDDMockito.given(this.voucherValidatorMock.validate(recievedCoins)).willReturn(true);
        // WHEN
        final List<Integer> acceptedCoins = this.vendingService.acceptCoins();
        // THEN
        BDDMockito.then(this.vendingMachineMock).should().getCoins();
        BDDMockito.then(this.voucherValidatorMock).should().validate(recievedCoins);
        BDDAssertions.then(acceptableCoins).containsAll(recievedCoins);
    }

    @Test
    void testAcceptCoinsFailure() {

        // Given
        final List<Integer> recievedCoins = Arrays.asList(10, 12);
        final List<Integer> acceptableCoins = Arrays.asList(ACCEPTABLE_COINS);

        BDDMockito.given(this.vendingMachineMock.getCoins()).willReturn(recievedCoins);
        BDDMockito.given(this.voucherValidatorMock.validate(recievedCoins)).willReturn(false);
        // WHEN
        final List<Integer> acceptedCoins = this.vendingService.acceptCoins();
        // THEN
        BDDMockito.then(this.vendingMachineMock).should().getCoins();
        BDDMockito.then(this.voucherValidatorMock).should().validate(recievedCoins);
        BDDAssertions.then(acceptedCoins).isNull();

    }

    @Test
    public void testAddCoins() {

        // Given
        final List<Integer> recievedCoins = Arrays.asList(10, 20);
        BDDMockito.given(this.vendingMachineMock.getCoins()).willReturn(recievedCoins);
        BDDMockito.given(this.voucherValidatorMock.validate(recievedCoins)).willReturn(true);
        // WHEN
        final int voucher = this.vendingService.addCoins();
        // THEN
        BDDAssertions.then(voucher).isEqualTo(30);
    }

    @Test
    void testChoseItem() {

        // Given
        final Map<Integer, String> items = new HashMap<Integer, String>();
        items.put(10, "Pofak Namaki");
        BDDMockito.given(this.vendingMachineMock.getItems()).willReturn(items);
        // WHEN
        final String item = this.vendingService.choseItem(10);
        // THEN
        BDDMockito.then(this.vendingMachineMock).should().getItems();
        BDDAssertions.then(item).isEqualTo("Pofak Namaki");

    }
}
