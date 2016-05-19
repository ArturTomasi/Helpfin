package com.pa.helpfin.view.editor.postingEditor;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Attachment;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.panes.ActionPane;
import com.pa.helpfin.view.editor.AttachmentEditor;
import com.pa.helpfin.view.tables.AttachmentList;
import com.pa.helpfin.view.util.ActionButton;
import com.pa.helpfin.view.util.EditorCallback;
import com.pa.helpfin.view.util.FileUtilities;
import com.pa.helpfin.view.util.Prompts;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

/**
 * @author artur
 */
public class AttachmentPane 
    extends 
        Tab
{
    private List<Attachment> attachments = new ArrayList();
    
    
    public AttachmentPane() 
    {
        setText( "Anexos" );
        setClosable( false );
        
        initComponents();
    }
    
    
    
    public void setSource( Posting source )
    {
        list.setPosting( source );
        
        attachments.addAll( list.getItems() );
    }
    
    
    
    public void obtainInput( Properties properties )
    {
        properties.put( "Attachments", attachments );
    }
    
    
    
    public void addAttachment()
    {
        try 
        {
            new AttachmentEditor( new EditorCallback<Attachment>( new Attachment() ) 
            {
                @Override
                public void handle( Event t )
                {
                    Prompts.process( "Importando " + source.getName() + "..." , new Task<Void>() 
                    {
                        @Override
                        protected Void call() throws Exception 
                        {
                            FileUtilities.copyFile( source.getOrigin(), source.getUrl() );

                            attachments.add( source );

                            list.setItems( attachments );

                            return null;
                        }
                    } );
                }
            }, false ).open();
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    public void editAttachment()
    {
        Attachment attachment = list.getSelectedAttachment();
        
        if( attachment != null )
        {
            new AttachmentEditor( new EditorCallback<Attachment>( attachment ) 
            {
                @Override
                public void handle( Event t )
                {
                    try
                    {
                        list.setItems( attachments );
                    }
                    
                    catch ( Exception e )
                    {
                        ApplicationUtilities.logException( e );
                    }
                }
            }, true ).open();
        }
        else
        {
            Prompts.alert( "Selecione um anexo para editar !" );
        }
    }
    
    
    private void saveAttachment()
    {
        try
        {
            Attachment attachment = list.getSelectedAttachment();
            
            if( attachment != null )
            {
                File file = FileUtilities.saveFile( "Salvar Anexo", attachment.getUrl() );
                
                if( file != null && ! file.exists() )
                {
                    Prompts.process( "Salvando " + attachment.getName() + "..." , new Task<Void>() 
                    {
                        @Override
                        protected Void call() throws Exception 
                        {
                            FileUtilities.download( attachment.getUrl(), file.getAbsolutePath() );
                            
                            return  null;
                        }
                    } );
                }
            }
            
            else
            {
                Prompts.alert( "Selecione um anexo para salvar" );
            }
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    private void deleteAttachment()
    {
        try
        {
            Attachment attachment = list.getSelectedAttachment();
            
            if( attachment != null )
            {
                if( Prompts.confirm( "Você tem certeza que deseja excluir o anexo: " + attachment.getName() ) )
                {
                    com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getAttachmentManager()
                                    .deleteValue( attachment );
                    
                    attachments.remove( attachment );
                    list.setItems( attachments );
                }
            }
            
            else
            {
                Prompts.alert( "Selecione um anexo para excluir" );
            }
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }

    
    
    public List<Attachment> getAttachments() 
    {
        return attachments;
    }
    
    
    
    private void initComponents()
    {
        actionPane.setActions( Arrays.asList( add, edit, save, delete ) );
        
        borderPane.setRight( actionPane );
        borderPane.setCenter( list );

        borderPane.setStyle( "-fx-border-color: white; -fx-padding: 0 0 4 0" );
        
        setContent( borderPane );
    }
    
    private BorderPane borderPane = new BorderPane();
    private ActionPane actionPane = new ActionPane();
    private AttachmentList list = new AttachmentList();
    
    private ActionButton add = new ActionButton( "Novo", "new.png", new EventHandler() 
    {
        @Override
        public void handle( Event t ) 
        {
            addAttachment();
        }
    } );
    
    private ActionButton save = new ActionButton( "Salvar", "save.png", new EventHandler() 
    {
        @Override
        public void handle( Event t ) 
        {
            saveAttachment();
        }
    } );
    
    private ActionButton edit = new ActionButton( "Editar", "edit.png", new EventHandler() 
    {
        @Override
        public void handle( Event t ) 
        {
            editAttachment();
        }
    } );
   
    private ActionButton delete = new ActionButton( "Excluir", "delete.png", new EventHandler() 
    {
        @Override
        public void handle( Event t ) 
        {
            deleteAttachment();
        }
    } );
}
