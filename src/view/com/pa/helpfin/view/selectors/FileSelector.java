package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.view.util.FileUtilities;
import com.sun.javafx.tk.Toolkit;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * @author artur
 */
public class FileSelector 
    extends 
        HBox
{
    private File file;
    
    public FileSelector() 
    {
        initComponents();
        
        textField.setPromptText( "Selecione um Anexo" );
    }
    
    @Override
    public void setWidth( double width )
    {
        super.setWidth( width );
        textField.setPrefWidth( width - Toolkit.getToolkit().getFontLoader().computeStringWidth( label.getText(), label.getFont() ) - 80 );
    }
    
  
    public void setSelected( File selected )
    {
        file = selected;
        textField.setText( selected == null ? null : selected.getName() );
    }
    
    public File getSelected()
    {
        return file;
    }
  
    protected void choice()
    {
        File f = FileUtilities.choiceFile( "Adicionar um anexo" );
            
        if( f != null  )
            setSelected( f );
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
        
        label.setStyle( "-fx-padding: 0 0 10 0" );
        
        getChildren().addAll( label, textField, choiceButton, clearButton );
        
        clearButton.setOnAction( new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle( ActionEvent t )
            {
                setSelected( null );
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
    private Label label         = new Label( "Anexo: " );
    private Button choiceButton = new Button();
    private Button clearButton  = new Button();
}
