package com.pa.helpfin.view;

import com.pa.helpfin.control.LoginController;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.view.util.Prompts;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author artur
 */
public class LoginView 
    extends 
        Application
            implements 
                ApplicationView
{
    LoginController controller = LoginController.getInstance();
    
    public static void main( String[] args )
    {
        launch(args);
    }
 
    private void validLogin()
    {
        User user = controller.login( txLogin.getText(), txPassword.getText() );
        
        if( user == null )
        {
            Prompts.alert( "Login Inválido", "Seu Login ou senha não estão corretos. \nTente outra vez !" );
            
            txLogin.setText( "" );
            txPassword.setText( "" );
            txLogin.requestFocus();
        }
        
        else
        {
            try
            {
                new HomeView().start( new Stage() );
                stage.close();
            }
            catch ( Exception e )
            {
                ApplicationUtilities.logException( e );
            }
        }
    }
    
    @Override
    public void start( Stage stage ) throws Exception 
    {
        initComponents();
        
        resize();
        
        initListeners();
        
        stage.setTitle( "Login" );
        stage.setScene( scene );
        stage.getIcons().add( new Image( ResourceLocator.getInstance().getImageResource( "helpFin.png") ) );
        stage.show();
        
        this.stage = stage;
    }
    
    @Override
    public void resize()
    {
        hbox.setLayoutY( ( pane.getHeight() - vbox.getHeight() - 120 ) / 2  );
        hbox.setLayoutX( ( pane.getWidth() - vbox.getWidth()   - 120 ) / 2 );
    }
    
    @Override
    public void initComponents() throws Exception
    {
        txLogin.setPromptText( "Usuário" );
        txPassword.setPromptText( "Senha" );
        
        ImageView imageLogin =  new ImageView( new Image( ResourceLocator.getInstance().getImageResource( "login.png" ) ) );
        imageLogin.setFitHeight( 50 );
        imageLogin.setFitWidth( 50 );
        btLogin.setGraphic( imageLogin );
        btLogin.setStyle( "-fx-background-radius: 50;" );
        btLogin.setCursor( Cursor.HAND );

        vbox.autosize();
        vbox.setSpacing( 5 );
        vbox.getChildren().addAll( txLogin, txPassword );
        
        hbox.getChildren().addAll( vbox, btLogin );
        hbox.setSpacing( 10  );
        hbox.setStyle( "-fx-background-color: #415A78; -fx-background-radius: 10;" );
        hbox.setPadding( new Insets( 60 ) );
        
        pane.setPrefSize( 800, 600 );
        pane.getChildren().add( hbox );
        pane.setStyle( "-fx-background-color: linear-gradient( from 0% 0% to 100% 100%, #415A78 0%, silver 100%);" );
    }
    
    @Override
    public void initListeners()
    {
        btLogin.setOnAction( new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle( ActionEvent t )
            {
                validLogin();
            }
        } );
        
        scene.widthProperty().addListener( new ChangeListener<Number>() 
        {
            @Override 
            public void changed( ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth )
            {
               resize();
            }
        });
        
        scene.heightProperty().addListener(new ChangeListener<Number>() 
        {
            @Override 
            public void changed( ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) 
            {
                resize();
            }
        });
        
        txPassword.setOnKeyPressed( new EventHandler<KeyEvent>()
        {
            @Override
            public void handle( KeyEvent t ) 
            {
                if ( t.getCode().equals( KeyCode.ENTER ) )
                {
                    validLogin();
                }
            }
        } );
    }
    
    private AnchorPane pane = new AnchorPane();
    private Scene scene = new Scene( pane );
    
    private VBox vbox = new VBox();
    private HBox hbox = new HBox();
    
    private TextField txLogin = new TextField();
    private PasswordField txPassword = new PasswordField();
    
    private Button btLogin = new Button();
    
    private Stage stage;
}
