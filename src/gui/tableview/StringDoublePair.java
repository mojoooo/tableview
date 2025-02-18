package gui.tableview;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class StringDoublePair
{
    private SimpleStringProperty entgelt = new SimpleStringProperty();
    private SimpleDoubleProperty gehalt = new SimpleDoubleProperty();
    
    public String getEntgelt()
    {
        return this.entgelt.get();
    }
    
    public Double getGehalt()
    {
        return this.gehalt.get();
    }
    
    public void setEntgelt(String entgelt)
    {
        this.entgelt.set(entgelt);;
    }
    
    public void setGehalt(Double gehalt)
    {
        this.gehalt.set(gehalt);
    }
    
    public SimpleStringProperty getEntgeltProperty()
    {
        return this.entgelt;
    }

    public SimpleDoubleProperty getGehaltProperty()
    {
        return this.gehalt;
    }
    
    public StringDoublePair(String entgelt, Double gehalt)
    {
        this.setEntgelt(entgelt);
        this.setGehalt(gehalt);
    }
}
