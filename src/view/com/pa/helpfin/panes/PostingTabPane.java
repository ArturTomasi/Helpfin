package com.pa.helpfin.panes;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingFilter;
import com.pa.helpfin.model.data.PostingType;
import com.pa.helpfin.view.tables.PostingTable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author artur
 */
public class PostingTabPane
    extends 
        Tab
{
    PostingFilter filter = new PostingFilter();
    
    public PostingTabPane() 
    {
        initComponents();
    }
    
    public void setPostings( PostingType type )
    {
        filter.clearConditions();
        
        filter.addCondition( PostingFilter.TYPE, type );
        
        refreshContent();
    }
    
    public void setPostings( PostingCategory category )
    {
        filter.clearConditions();
        
        filter.addCondition( PostingFilter.POSTING_CATEGORY, category );
        
        refreshContent();
    }
    
    public void resizeComponents( double height, double width ) 
    {
        legendPane.setPrefWidth( width );
        legendPane.setLayoutY( height - legendPane.getHeight() );
        
        postingTable.setPrefSize( width, height - legendPane.getHeight() );
    }
    
    private void refreshContent()
    {
        try
        {
            postingTable.setItems( com.pa.helpfin.model.ModuleContext.getInstance()
                                                    .getPostingManager()
                                                    .getValues( filter ) );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    private void initComponents()
    {
        
        setClosable( false );
        setText( "Lançamentos" );
        
        legendPane.addItems( new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_REGISTRED ], "new.png"    ),
                             new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_PROGRESS ],  "play.png"   ),
                             new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_FINISHED ],  "finish.png" ),
                             new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_DELETED ],   "delete.png" ) );
        
        pane.getChildren().addAll( postingTable, legendPane );
        
        setContent( pane );
    }
    
    private PostingTable postingTable = new PostingTable();
    private LegendPane legendPane = new LegendPane();
    private AnchorPane pane = new AnchorPane();
}
