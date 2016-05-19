package com.pa.helpfin.view.editor;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Attachment;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.view.editor.postingEditor.AttachmentPane;
import com.pa.helpfin.view.editor.postingEditor.GeneralPane;
import com.pa.helpfin.view.editor.postingEditor.InfoPane;
import com.pa.helpfin.view.editor.postingEditor.ValuesPane;
import com.pa.helpfin.view.util.EditorCallback;
import com.pa.helpfin.view.util.FileUtilities;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

/**
 * @author artur
 */
public class PostingEditor 
    extends 
        AbstractEditor<Posting>
{
    public static final int MODE_NEW    = 0;
    public static final int MODE_EDIT   = 1;
    public static final int MODE_FINISH = 2;
    
    public int mode = MODE_NEW;
    
    public PostingEditor( EditorCallback<Posting> callback, int mode ) 
    {
        super( callback );
        
        this.mode = mode;
        
        initComponents();
        
        setTitle( "Editor de Lançamentos" );
        setHeaderText( "Editor de Lançamentos" );
        
        setSource( source );
        
    }

    
    
    @Override
    protected void resize() 
    {
        generalPane.resize( getWidth(), getHeight() );
        valuesPane.resize( getWidth(), getHeight() );
        
        getDialogPane().requestLayout();
    }

    
    
    @Override
    protected void obtainInput() 
    {
        generalPane.obtainInput( source );
        
        if( mode != MODE_FINISH )
            valuesPane.obtainInput( source, callback.properties );
        
        infoPane.obtainInput( source );
        attachmentPane.obtainInput( callback.properties );
    }

    
    
    @Override
    protected void onCancel() 
    {
        obtainInput();
        
        ( (List<Attachment>) callback.properties.get( "Attachments" ) ).forEach( (attachment) -> 
        {
            try
            {
                if( attachment.getId() == 0 )
                {
                    FileUtilities.deleteFile( attachment.getUrl() );
                }
            }
            
            catch( Exception e )
            {
                ApplicationUtilities.logException( e );
            }
        } );
    }
    
    
    
    
    @Override
    protected void validadeInput( List<String> erros ) throws Exception
    {
        erros.addAll( com.pa.helpfin.model.ModuleContext.getInstance()
                                            .getPostingManager()
                                            .isUnique( source ) );
        
        generalPane.validateInput( erros );
        
        if( mode != MODE_FINISH)
        {
            valuesPane.validateInput( erros );
        }
    }

    @Override
    protected void setSource( final Posting source )
    {
        if( source != null )
        {
            generalPane.setSource( source );
            generalPane.loadField( mode );
            infoPane.setSource( source );
            valuesPane.setSource( source );
            attachmentPane.setSource( source );
            
            if( mode == MODE_NEW )
            updateValuesTab();
        }
    }
    
    private void updateValuesTab()
    {
        if( source.getEstimateDate() != null && mode == MODE_NEW )
        {
            valuesPane.setSource( source );

            tabPane.getTabs().clear();

            tabPane.getTabs().add( generalPane );
            
            if( source.isRepeat() )
                tabPane.getTabs().add( valuesPane );

            tabPane.getTabs().add( infoPane );
            tabPane.getTabs().add( attachmentPane );
        }
    }
    
    private void initComponents()
    {
        if( mode == MODE_FINISH )
        {
            getDialogPane().setPrefSize( 650, 350 );
        }
        
        else
        {
            getDialogPane().setPrefSize( 800, 550 );
        }
        
        tabPane.getTabs().addAll( generalPane, infoPane, attachmentPane );
        
        getDialogPane().setContent( tabPane );
        
        generalPane.getContent().addEventHandler( GeneralPane.Events.ON_CHANGE_VALUES, new EventHandler<Event>()
        {
            @Override
            public void handle( Event t ) 
            {
                source =  (Posting) ( ( GridPane ) t.getTarget() ).getProperties().get( "posting" ) ;
                updateValuesTab();
            }
        } );
        
        valuesPane.setOnSelectionChanged( new EventHandler<Event>() {

            @Override
            public void handle( Event t ) 
            {
                generalPane.obtainInput( source );
                valuesPane.setSource( source );
            }
        } );
    }
    
    private TabPane tabPane         = new TabPane();
    private GeneralPane generalPane = new GeneralPane();
    private InfoPane infoPane       = new InfoPane();
    private ValuesPane valuesPane   = new ValuesPane();
    private AttachmentPane attachmentPane = new AttachmentPane();
}
