package com.pa.helpfin.view.inspectors;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Attachment;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.view.editor.postingEditor.AttachmentPane;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author artur
 */
public class InspectView 
{
    private Posting posting;
    
    public InspectView( Posting posting )
    {
        initComponents();

        setSource( posting );
    }

    public void show()
    {
        stage.show();
    }
    
    public void setSource( Posting posting )
    {
        this.posting = posting;
        
        stage.setTitle( "Detalhes do Lançamento: " + posting );
        details.setSource( posting );
        attachmentPane.setSource( posting );
    }
    
    
    private void initComponents() 
    {
        tabPane.getTabs().addAll( details, attachmentPane );
        
        pane.getChildren().add( tabPane );

        stage.setScene( new Scene( pane, 800, 600 ) );
        
        stage.setOnCloseRequest( new EventHandler<WindowEvent>() 
        {
            @Override
            public void handle( WindowEvent t )
            {
                List<Attachment> attachments = attachmentPane.getAttachments();
                
                if( ! attachments.isEmpty() )
                {
                    attachments.forEach( attachment -> 
                    {
                        try
                        {
                            if( attachment.getId() == 0 )
                            {
                                attachment.setPosting( posting.getId() );
                                com.pa.helpfin.model.ModuleContext.getInstance().getAttachmentManager().addValue( attachment );
                            }
                        }
                        
                        catch( Exception e )
                        {
                            ApplicationUtilities.logException( e );
                        }
                    } );
                }
            }
        } );
    }
    
    private TabPane tabPane               = new TabPane();
    private PostingDetails details     = new PostingDetails();
    private AttachmentPane attachmentPane = new AttachmentPane();
    
    private StackPane pane = new StackPane();
    private Stage stage = new Stage();
    
}
