package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.ResourceLocator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * @author artur
 * @param <T>
 */
public class ItemSelector<T>
    extends 
        HBox
{
    public static class Events
    {
        public static final EventType ON_SELECT = new EventType( "onSelectItem" );
    }
    
    public ItemSelector() 
    {
        this( null );
    }
    
    public ItemSelector( String text ) 
    {
        this( text, (T) null );
    }
    
    public ItemSelector( String text, T... items ) 
    {
        initComponents();
        
        textField.setPromptText( text );
        
        setItems( FXCollections.observableArrayList( items ) );
    }

    @Override
    public void setWidth( double width )
    {
        super.setWidth( width );
        textField.setPrefWidth( width );
    }
    
    public void setItems( List<T> items )
    {
        list.setItems( items );
    }
    
    public void setItems( T ... items )
    {
        list.setItems( FXCollections.observableArrayList( items ) );
    }
  
    public void setSelected( T selected )
    {
        if( selected == null )
        {
            textField.clear();
            list.setSelected( selected );
        }
        
        else
        {
            list.setSelected( selected );
            textField.setText( selected.toString() );
        }
    }
    
    public T getSelected()
    {
        return list.getSelected();
    }
  
    public void setSelectedIndex( int index )
    {
        list.setSelectedIndex( index );
        
        if( list.getSelected() != null )
            textField.setText( list.getSelected().toString() );
    }
    
    public int getSelectedIndex()
    {
        return list.getSelectedIndex();
    }
    
    private void choice()
    {
        list.open( textField.getPromptText() );
        
        if( getSelected() != null )
        {
            textField.setText( getSelected().toString() );
            fireEvent( new Event( Events.ON_SELECT ) );
        }
    }
    
    private void initComponents()
    {
        setSpacing( 0 );
        
        ImageView choiceImage = new ImageView( new Image( ResourceLocator.getInstance().getImageResource( "choice.png" ) ) );
        choiceImage.setFitHeight( 30 );
        choiceImage.setFitWidth( 30 );
        choiceButton.setCursor( Cursor.HAND );
        choiceButton.setGraphic( choiceImage );
        choiceButton.setStyle( "-fx-background-color: transparent" );
        choiceButton.setPadding( new Insets( 0, 0, 0, 5 ) );
        choiceButton.setAlignment( Pos.CENTER_LEFT );
        
        ImageView clearImage = new ImageView( new Image( ResourceLocator.getInstance().getImageResource( "clear.png" ) ) );
        clearImage.setFitHeight( 25 );
        clearImage.setFitWidth( 25 );
        clearButton.setCursor( Cursor.HAND );
        clearButton.setGraphic( clearImage );
        clearButton.setStyle( "-fx-background-color: transparent;" );
        clearButton.setAlignment( Pos.CENTER_LEFT );
        
        textField.setPromptText( "selecione um item" );
        textField.setEditable( false );
        
        getChildren().addAll( textField, choiceButton, clearButton );
        
        clearButton.setOnAction( new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle( ActionEvent t )
            {
                setSelected( null );
                fireEvent( new Event( Events.ON_SELECT ) );
            }
        } );
        
        choiceButton.setOnAction( new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle( ActionEvent t )
            {
                choice();
            }
        } );
    }
    
    private TextField textField = new TextField();
    private Button choiceButton = new Button();
    private Button clearButton  = new Button();
    private ListPicker<T> list  = new ListPicker();
}
