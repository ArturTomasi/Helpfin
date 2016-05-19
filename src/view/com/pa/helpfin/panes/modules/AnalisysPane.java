package com.pa.helpfin.panes.modules;

import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.panes.PostingTabPane;
import com.pa.helpfin.view.inspectors.DrilldownPostings;
import com.pa.helpfin.view.inspectors.PostingCategoryDetails;
import com.pa.helpfin.view.util.PostingTree;
import java.util.Collections;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * @author artur
 */
public class AnalisysPane 
     extends 
        AbstractModulesPane
{

    public AnalisysPane() 
    {
        initComponents();
    }
    
    @Override
    public List<Button> getActions() 
    {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void refreshContent() 
    {
        Object source = tree.getSelectedNode();
        
        pane.getTabs().clear();
        
        if( source instanceof String )
        {
            drilldown.refreshContent();
            pane.getTabs().add( drilldown );
        }
        
        else if( source instanceof PostingCategory)
        {
            PostingCategory category = (PostingCategory) source;
            
            details.setSource( category );
            
            postingPane.setPostings( category );
             
            pane.getTabs().addAll( details, postingPane );
        }
      
//        if( source instanceof PostingType)
//        {
//            PostingType type = (PostingType) source;
//            
//            postingPane.setPostings( type );
//        }
    }

    @Override
    public void resizeComponents( double height, double width )
    {
        borderPane.setPrefSize( width, height );
        
        postingPane.resizeComponents( height - 40 , width - tree.getWidth() );
    }
    
    private void initComponents()
    {
        borderPane.setLeft( tree );
        borderPane.setCenter( pane );

        tree.addEventFilter( PostingTree.Events.ON_SELECT, new EventHandler<Event>() 
        {
            @Override
            public void handle(Event t) 
            {
                refreshContent();
            }
        } );
        
        getChildren().add( borderPane );
    }
    
    private BorderPane borderPane = new BorderPane();
    private PostingTree tree = new PostingTree();
    private PostingCategoryDetails details = new PostingCategoryDetails();
    private DrilldownPostings  drilldown = new DrilldownPostings();
    private PostingTabPane postingPane = new PostingTabPane();
    
    private TabPane pane = new TabPane( details, postingPane );
}
