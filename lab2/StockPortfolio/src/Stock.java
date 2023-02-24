public class Stock {
    private String label;
    private Integer quantity;

    public Stock(String l, Integer q){
        this.label = l;
        this.quantity = q;
    }

    public String getLabel() {
        return this.label;
    }
    public Integer getQuantity() {
        return this.quantity;
    }
    public void setLabel(String newLabel){
        this.label = newLabel;
    }
    public void setQuantity(Integer newQuantity) {
        this.quantity = newQuantity;
    }
}
