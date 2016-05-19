package com.pa.helpfin.view.editor;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Attachment;
import com.pa.helpfin.view.selectors.FileSelector;
import com.pa.helpfin.view.util.EditorCallback;
import com.pa.helpfin.view.util.FileUtilities;
import com.pa.helpfin.view.util.MaskTextField;
import java.io.File;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * @author artur
 */
public class AttachmentEditor 
    extends 
        AbstractEditor<Attachment>
{
    private boolean edit;
    
    public AttachmentEditor( EditorCallback<Attachment> callback, boolean edit ) 
    {
        super( callback );

        this.edit = edit;
        
        setTitle( "Editor de Anexos" );
        setHeaderText( "Editor de Anexos" );
        
        initComponents();
        
        setSource( source );
    }

    @Override
    protected void validadeInput( List<String> erros ) throws Exception
    {
        if( ! nameField.isValid() )
            erros.add( "Nome" );
        
        if( fileSelector.getSelected() == null )
            erros.add( "Anexo" );
    }

    
    @Override
    protected void obtainInput() 
    {
        File file = fileSelector.getSelected();
        
        source.setName( nameField.getText() );
        source.setInfo( infoField.getText() );
        
        if( ! edit )
        {
            source.setOrigin( file != null ? file.getAbsolutePath() : "" );
            source.setUrl(  file  != null ? System.currentTimeMillis() + "_" + file.getName() : "" );
        }
    }

    @Override
    protected void resize() 
    {
        
    }

    @Override
    protected void setSource( Attachment source )
    {
        try
        {
            nameField.setText( source.getName() );
            infoField.setText( source.getInfo() );

            if( source.getUrl() != null && ! source.getUrl().isEmpty() )
                fileSelector.setSelected( FileUtilities.getAttachment( source.getUrl() ) );
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    private void initComponents()
    {
        fileSelector.setDisable( edit );
        
        gridPane.setVgap( 20 );
        gridPane.setHgap( 20 );
        gridPane.setStyle( "-fx-padding: 30" );
        
        gridPane.add( fileSelector, 0, 0, 2, 1 );
        
        gridPane.add( lbName,       0, 1, 1, 1 );
        gridPane.add( nameField,    1, 1, 1, 1 );
        
        gridPane.add( lbInfo,       0, 2, 1, 1 );
        gridPane.add( infoField,    1, 2, 1, 1 );
        
        getDialogPane().setContent( gridPane );
    }
    
    private GridPane gridPane = new GridPane();
    
    private MaskTextField nameField   = new MaskTextField();
    private Label lbName              = new Label( "Nome" );
    
    private TextArea infoField        = new TextArea();
    private Label lbInfo              = new Label( "Informações" );

    private FileSelector fileSelector = new FileSelector()
    {
        @Override
        protected void choice() 
        {
            super.choice();
            
            if( nameField.getText() == null || nameField.getText().isEmpty() )
                nameField.setText( fileSelector.getSelected().getName() );
        }
    };
    
}
