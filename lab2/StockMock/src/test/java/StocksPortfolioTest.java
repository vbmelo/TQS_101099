//Junit
import org.example.IStockMarketService;
import org.example.Stock;
import org.example.StocksPortfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

//Hamcrest
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

//Mockito
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StocksPortfolioTest {
    @Mock
    IStockMarketService mockMarket;
    @InjectMocks
    StocksPortfolio portfolio;

    @BeforeEach
    void setUp(){
        portfolio.setName("Manuel Gomes");
    }

    @Test
    void getTotalValueTest(){
        portfolio.addStock(new Stock("Positivo", 20));
        portfolio.addStock(new Stock("TiaoCompiuters", 10));

        Mockito.lenient().when(mockMarket.lookUpPrice("Positivo")).thenReturn(10.0);
        Mockito.lenient().when(mockMarket.lookUpPrice("TiaoCompiuters")).thenReturn(8.50);

        double correctTotal = 10*20 + 8.50*10;

        assertThat(portfolio.getTotalValue(),is(correctTotal));

        Mockito.verify(mockMarket, Mockito.times(2)).lookUpPrice(Mockito.anyString());
    }
}

