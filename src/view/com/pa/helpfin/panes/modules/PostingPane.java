package com.pa.helpfin.panes.modules;

import com.pa.helpfin.control.PostingController;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.panes.LegendPane;
import com.pa.helpfin.view.editor.PostingEditor;
import com.pa.helpfin.view.inspectors.InspectView;
import com.pa.helpfin.view.tables.PostingTable;
import com.pa.helpfin.view.util.ActionButton;
import com.pa.helpfin.view.util.EditorCallback;
import com.pa.helpfin.view.util.Prompts;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * @author artur
 */
public class PostingPane 
    extends 
        AbstractModulesPane
{
    private PostingController controller = PostingController.getInstance();
    
    public PostingPane()
    {
        initComponents();
    }
    
    private void addPosting()
    {
        new PostingEditor( new EditorCallback<Posting>( new Posting() ) 
        {
            @Override
            public void handle( Event t )
            {
                controller.addPosting( source, properties );
                
                refreshContent();
            }
            
        }, PostingEditor.MODE_NEW ).open();
    }
    
    private void deletePosting()
    {
        Posting posting =  postingTable.getSelectedItem();
        
        String validate = controller.validateDelete( posting );
        
        if( validate == null )
        {
            String message = "Você tem certeza que deseja excluir o Lançamento: " + posting.getName();
            
            if( posting.isRepeat() )
                    message += "\n Todas os lançamentos recorrentes posteriores serão excluidos também !";
            
            if( Prompts.confirm( "Confirmar Exclusão",  message ) )
            {
                controller.deletePosting( posting );

                refreshContent();
            }
        }
        
        else
        {
            Prompts.info( validate );
        }
    }
    
    private void editPosting()
    {
        Posting posting = postingTable.getSelectedItem();
        
        String validate = controller.validateEdit( posting );
        
        if( validate == null )
        {
            new PostingEditor( new EditorCallback<Posting>( posting )
            {
                @Override
                public void handle( Event t )
                {
                    controller.editPosting( source, properties );

                    refreshContent();
                }

            }, PostingEditor.MODE_EDIT ).open();
        }
        
        else
        {
            Prompts.info( validate );
        }
    }
    
    
    
    private void copyPosting()
    {
        Posting posting =  postingTable.getSelectedItem();
        
        if( posting == null )
        {
            Prompts.alert( "Necessário selecionar um Lançamento para copiar !" );
            
            return;
        }
        
        if( Prompts.confirm( "Confirmar Cópia", "Você tem certeza que deseja copiar o Lançamento: \"" + posting.getName() + "\"" ) )
        {
             new PostingEditor( new EditorCallback<Posting>( posting.clone() ) 
            {
                @Override
                public void handle( Event t )
                {
                    controller.addPosting( source, properties );

                    refreshContent();
                }
            
             }, PostingEditor.MODE_NEW ).open();
                
        }
    }
    
    
    
    private void finishPosting()
    {
        Posting posting = postingTable.getSelectedItem();
        
        String validate = controller.validateFinish( posting );
        
        if( validate == null )
        {
            new PostingEditor( new EditorCallback<Posting>( posting )
            {
                @Override
                public void handle( Event t )
                {
                    controller.finishPosting( source );

                    refreshContent();
                }

            }, PostingEditor.MODE_FINISH ).open();
        }
       
        else
        {
            Prompts.info( validate );
        }
    }
    
    
    
    private void reversePosting()
    {
        Posting posting = postingTable.getSelectedItem();
        
        String validate = controller.validateReserve( posting );
        
        if( validate == null )
        {
            controller.reversePosting( posting );
            
            refreshContent();
            
            Prompts.info( "Extornado com sucesso !" );
        }
        
        else
        {
            Prompts.info( validate );
        }
    }
    
    
    
    private void inspectPosting()
    {
        Posting posting = postingTable.getSelectedItem();
        
        if( posting != null )
        {
            new InspectView( posting ).show();
        }
        
        else
        {
            Prompts.info( "Selecione um Lançamento" );
        }
    }
    
    @Override
    public List<Button> getActions()
    {
        clonePosting.setDisable( ! ApplicationUtilities.getInstance().hasPermission() );
        reversePosting.setDisable( ! ApplicationUtilities.getInstance().hasPermission() );
        
        List<Button> actions = new ArrayList();
        
        actions.add( addPosting );
        actions.add( editPosting );
        actions.add( deletePosting );
        actions.add( clonePosting );
        actions.add( finishPosting );
        actions.add( reversePosting );
        actions.add( inspectPosting );
        
        return  actions;
    }
    
    
    
    @Override
    public void refreshContent() 
    {
        try
        {
            User user = ApplicationUtilities.getInstance().getActiveUser();
            
            if( user.getRole() == User.ROLE_OPERATOR )
            {
                postingTable.setItems( com.pa.helpfin.model.ModuleContext.getInstance()
                                                        .getPostingManager()
                                                        .getCurrentPostings( user ) );
            }
            
            else
            {
                postingTable.setItems( com.pa.helpfin.model.ModuleContext.getInstance()
                                                        .getPostingManager()
                                                        .getNextPostings() );
            }
        }

        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }

    
    
    @Override
    public void resizeComponents( double height, double width ) 
    {
        legendPane.setPrefWidth( width );
        legendPane.setLayoutY( height - legendPane.getHeight() );
        
        postingTable.setPrefSize( width, height - legendPane.getHeight() );
    }
    
    
    
    private void initComponents()
    {
        legendPane.addItems( new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_REGISTRED ], "new.png"    ),
                             new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_PROGRESS ],  "play.png"   ),
                             new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_FINISHED ],  "finish.png" ),
                             new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_DELETED ],   "delete.png" ) );
        
        getChildren().addAll( postingTable, legendPane );
    }
    
    
    
    private PostingTable postingTable = new PostingTable();
    private LegendPane legendPane = new LegendPane();
    
    private ActionButton addPosting = new ActionButton( "Novo", "new.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            addPosting();
        }
    } );
    
    private ActionButton editPosting = new ActionButton( "Editar", "edit.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            editPosting();
        }
    } );
    
    private ActionButton deletePosting = new ActionButton( "Excluir", "delete.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            deletePosting();
        }
    } );
    
    private ActionButton finishPosting = new ActionButton( "Finalizar", "finish.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            finishPosting();
        }
    } );
    
    private ActionButton reversePosting = new ActionButton( "Extornar", "reverse.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            reversePosting();
        }
    } );
    
    private ActionButton inspectPosting = new ActionButton( "Detalhes", "details.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            inspectPosting();
        }
    } );
  
    private ActionButton clonePosting = new ActionButton( "Copiar", "copy.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            copyPosting();
        }
    } );
}

