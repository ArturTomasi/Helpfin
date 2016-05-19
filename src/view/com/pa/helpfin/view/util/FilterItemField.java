package com.pa.helpfin.view.util;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.DefaultFilter.FilterItem;
import com.pa.helpfin.view.selectors.ItemSelector;
import java.sql.Date;
import java.util.List;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * @author artur
 */
public class FilterItemField 
    extends 
        GridPane
{
    private FilterItem filterItem;

    public FilterItemField( FilterItem filterItem ) 
    {
        this.filterItem = filterItem;
        
        adjustFilterItem();

        initComponents();
    }
    
    
    
    private void adjustFilterItem()
    {
        try
        {
            if( filterItem != null ) 
            {
                node = (Node) Class.forName( filterItem.getComponent() ).newInstance();
                
                label.setText( filterItem.getName() );
                
                if( node instanceof DateField )
                    node = new HBox( node, node.getClass().newInstance() );
                
                if( node instanceof  Spinner )
                    ( (Spinner) node ).setEditable( true );
            }
        }
        
        catch ( ClassNotFoundException | InstantiationException | IllegalAccessException e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    

    public Object getValue()
    {
        if( node instanceof MaskTextField )
        {
            return ( (MaskTextField) node ).getValue();
        }
        
        else if( node instanceof NumberTextField )
        {
            return ( (NumberTextField) node ).getDouble();
        }
        
        else if( node instanceof DateField )
        {
            return ( (DateField) node ).getDate();
        }
        
        else if( node instanceof DateBetweenField )
        {
            return ( (DateBetweenField) node ).getValue();
        }
        
        else if( node instanceof ItemSelector )
        {
            return ( (ItemSelector) node ).getSelected();
        }
        
        return null;
    }
    
    
    
    public void setValue( Object value )
    {
        if( value == null )
            return;
        
        if( node instanceof MaskTextField )
        {
            ( (MaskTextField) node ).setText( value.toString() );
        }
        
        else if( node instanceof NumberTextField )
        {
            ( (NumberTextField) node ).setDouble( (Double) value );
        }
        
        else if( node instanceof DateField )
        {
            ( (DateField) node ).setDate( (Date) value );
        }
        
        else if( node instanceof DateBetweenField )
        {
            ( (DateBetweenField) node ).setValue( (Date[]) value );
        }
        
        else if( node instanceof ItemSelector )
        {
            ( (ItemSelector) node ).setSelected( value );
        }
    }
     
     
    
    public void validadeField( List<String> erros )
    {
        if( node instanceof MaskTextField && ! ( (MaskTextField) node ).isValid() )
            erros.add( getName() );
        
        if( node instanceof NumberTextField && Double.isNaN( ( (NumberTextField) node ).getDouble() ) )
            erros.add( getName() );
        
        if( node instanceof DateField && ( (DateField) node ).getDate() == null )
            erros.add( getName() );
        
        if( node instanceof DateBetweenField && ( (DateBetweenField) node ).validDates() != null )
            erros.add( ( (DateBetweenField) node ).validDates() );
        
        if( node instanceof ItemSelector && ( (ItemSelector) node ).getSelected() == null )
            erros.add( getName() );
    }
    
    
    
    public int getFilterId()
    {
        if( filterItem != null )
            return filterItem.getId();
        
        return -1;
    }
    
    
    public boolean isSelected()
    {
        return checkBox.isSelected();
    }
    
    
    
    public String getName()
    {
        if( label != null && label.getText() != null )
            return label.getText();
        
        return "";
    }
    
    
    
    private void initComponents()
    {
        setVgap( 5 );
        setHgap( 5 );
        setStyle( "-fx-padding: 5;" );
        setPrefWidth( 500 );
        
        ColumnConstraints colCheck = new ColumnConstraints();
        colCheck.setPercentWidth( 5 );
        colCheck.setHalignment( HPos.CENTER );
    
        ColumnConstraints colName = new ColumnConstraints();
        colName.setPercentWidth( 30);
        colName.setHalignment( HPos.LEFT );
     
        ColumnConstraints colNode = new ColumnConstraints();
        colNode.setPercentWidth( 65 );
        colNode.setHalignment( HPos.LEFT );
        
        getColumnConstraints().addAll( colCheck, colName, colNode );
        
        checkBox.setPrefWidth( colCheck.getMaxWidth() );
        label.setPrefWidth( colName.getMaxWidth() );
        node.setLayoutX( colNode.getMaxWidth() );
        
        addColumn( 0, checkBox );
        addColumn( 1, label );
        addColumn( 2, node );
    }
    
    private Label label = new Label();
    private CheckBox checkBox = new CheckBox();
    private Node node;
}
