package com.pa.helpfin.view.editor;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.view.util.EditorCallback;
import com.pa.helpfin.view.util.Prompts;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Callback;

/**
 * @author artur
 * @param <T>
 */
public abstract class AbstractEditor<T>
    extends 
        Dialog<T>
{
    public EditorCallback callback;
    public T source;
    private ButtonType btSave   = new ButtonType( "Salvar",   ButtonData.OK_DONE );
    private ButtonType btCancel = new ButtonType( "Cancelar", ButtonData.CANCEL_CLOSE );
    private ButtonType selectedButton;
    
    private AbstractEditor(){}
    
    public AbstractEditor( EditorCallback<T> callback ) 
    {
        this.callback = callback;
        source = callback.source;
        
        initComponents();
    }

    private boolean onSave()
    {
        try
        {
            obtainInput();
            
            List<String> listError  = new ArrayList();

            validadeInput( listError  ) ;

            if( ! listError .isEmpty() )
            {
                String message = "";

                for( String error : listError )
                {
                    message += "\n   *" + error;
                }

                Prompts.alert( "Alguns campos não estão preenchidos ou estão inválidos", message );
                
                return false;
            }

            else
            {
                callback.handle( new Event( source, this, EventType.ROOT ) );
            }
        }
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
        
        return true;
    }
    
    protected void onCancel(){} //Sobreescrever somente em alguns casos

    private void initComponents()
    {
        setTitle( "Editor" );
        setHeaderText( "Editor de Items" );
        setResizable( false );
        
        getDialogPane().getButtonTypes().addAll( btCancel, btSave );
        
        widthProperty().addListener( new ChangeListener<Number>() 
        {
            @Override 
            public void changed( ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth )
            {
               resize();
            }
        } );
        
        heightProperty().addListener(new ChangeListener<Number>() 
        {
            @Override 
            public void changed( ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) 
            {
                resize();
            }
        } );
        
        setOnCloseRequest( new EventHandler() {

            @Override
            public void handle( Event t ) 
            {
                if(  selectedButton == btSave )
                {
                    if ( ! onSave() )
                    {
                        t.consume();
                    }
                }
                
                else
                {
                    onCancel();
                }
            }
        } );

        setResultConverter( new Callback() 
        {
            @Override
            public Object call(  Object p ) 
            {
                return selectedButton = (ButtonType) p;
            }
        } );
    }
    
    public void open()
    {
        showAndWait();
    }
    
    protected abstract void validadeInput( List<String> erros ) throws Exception;
    protected abstract void obtainInput();
    protected abstract void resize();
    protected abstract void setSource( T source );
}
