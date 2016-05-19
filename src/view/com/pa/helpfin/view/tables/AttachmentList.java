package com.pa.helpfin.view.tables;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.Attachment;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.view.util.FileUtilities;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author artur
 */
public class AttachmentList 
    extends 
        ListView<Attachment>
{
    public AttachmentList() 
    {
         setCellFactory( new Callback<ListView<Attachment>, ListCell<Attachment>>() 
         {
            @Override
            public ListCell<Attachment> call( ListView<Attachment> list )
            {
                return new AttachmentListCell();
            }
        } );
        
        setOnMouseClicked( new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle( MouseEvent t )
            {
                if( t.getButton().equals( MouseButton.PRIMARY ) && 
                        t.getClickCount() == 2  && getSelectedAttachment() != null )
                {   
                    try 
                    {
                        FileUtilities.open( getSelectedAttachment().getUrl() );
                    } 
                    catch ( Exception e )
                    {
                        ApplicationUtilities.logException( e );
                    }
                }
            }
        } );
        
        getSelectionModel().setSelectionMode( SelectionMode.SINGLE );
        
        getStylesheets().add( "config/table.css" );
        
        
    }
    
    
    
    public void setPosting( Posting posting )
    {
        try
        {
            setItems( FXCollections.observableArrayList( com.pa.helpfin.model.ModuleContext
                                            .getInstance()
                                            .getAttachmentManager()
                                            .getValues( posting ) ) );
        }
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    public void setItems( List<Attachment> attachments )
    {
        Platform.runLater( new Runnable() 
        {
            @Override
            public void run()
            {
                ObservableList atts = FXCollections.observableArrayList( attachments );
                        
                getItems().clear();

                setItems( atts );

                requestLayout();
            }
        } );
    }
    
    
    public Attachment getSelectedAttachment()
    {
        return getSelectionModel().getSelectedItem();
    }
    
    
    private class AttachmentListCell extends ListCell<Attachment> 
    {
        private final List EXTENSIONS = Arrays.asList( "other", "avi", "doc", "html", "js", "jpg", "png", "mp3", "pdf", "png", "txt" );
        
        @Override
        protected void updateItem( Attachment attachment, boolean empty ) 
        {
            super.updateItem( attachment, empty );

            if ( attachment != null && ! empty ) 
            {
                StackPane st = new StackPane();

                String type = attachment.getUrl().substring( attachment.getUrl().lastIndexOf( "." ) + 1 );
                
                if( ! EXTENSIONS.contains( type ) )
                    type = "other";
                
                ImageView image = new ImageView( new Image( ResourceLocator.getInstance().getImageResource( type + ".png" ) ) );
                image.setFitHeight( 42 );
                image.setFitWidth( 42 );

                StackPane imagePane = new StackPane();
                imagePane.setMaxWidth( 42 );
                imagePane.setMaxHeight( 42 );
                imagePane.getChildren().add( image );

                Text name = new Text();
                name.setFont( Font.font( "castellar", FontWeight.BOLD, 15 ) );
                name.setText( attachment.getName() );

                Text info = new Text();
                info.setFont( Font.font( "castellar", 12 ) );
                info.setText( attachment.getInfo()  );
                
                StackPane.setAlignment( imagePane, Pos.CENTER_LEFT );
                
                StackPane.setAlignment( name, Pos.CENTER_LEFT );
                StackPane.setMargin( name, new Insets( 0, 0, 0, 60 ) );
                
                StackPane.setAlignment( info, Pos.BOTTOM_LEFT );
                StackPane.setMargin( info, new Insets( 0, 0, 0, 75 ) );

                st.getChildren().addAll( imagePane, name, info );

                setGraphic( st );
            }
            else
            {
                setGraphic( null );
            }

        }
    }    
}
