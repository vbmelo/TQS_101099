import java.util.ArrayList;

public class StocksPortfolio {
    private String name;
    private IStockMarketService stockMarketService;
    private ArrayList<Stock> stocks = new ArrayList<>();
    public IStockMarketService getStockMarketService(){
        return this.stockMarketService;
    }
    public void setStockMarketService(IStockMarketService newStockMarketService){
        this.stockMarketService = newStockMarketService;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public double getTotalValue(){
        double total = 0;
        for (Stock s : this.stocks){
            total += (stockMarketService.lookUpPrice(s.getLabel())*s.getQuantity());
        }
        return total;
    }
    public void addStock(Stock newStock){
        this.stocks.add(newStock);
    }
}
