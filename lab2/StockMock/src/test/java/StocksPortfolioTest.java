//Junit
import org.example.IStockMarketService;
import org.example.StocksPortfolio;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

//Hamcrest
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

//Mockito
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
        portfolio.addStock(new Stock("Positivo Compiuters", 20));
        portfolio.addStock(new Stock("Tião co.", 10));

        Mockito.when(mockMarket.getPrice("Positivo Compiuters")).thenReturn

    }
}

