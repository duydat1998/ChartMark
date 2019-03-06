package assignment.prm.chartmarkapplication.Model;

public class GeneralProduct {

    public int ID;
    public String category;
    public String name;
    public String brandId;
    public String image1;

    public GeneralProduct(){

    }

    public GeneralProduct(int ID, String category, String name, String brandId, String image1){
        this.ID = ID;
        this.brandId = brandId;
        this.category = category;
        this.name = name;
        this.image1 = image1;
    }
}
