package com.pa.helpfin.view.util;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import org.controlsfx.dialog.ProgressDialog;

/**
 * @author artur
 */
public class Prompts 
{
    public static void info ( String message )
    {
        info( null, message );
    }
    
    public static void info ( String title, String message )
    {
        Alert dialogoInfo = new Alert( Alert.AlertType.INFORMATION );
        dialogoInfo.getDialogPane().setPrefSize( 400, 200 );
        dialogoInfo.setTitle( "Informação" );
        dialogoInfo.setHeaderText( title == null ? "Informação" : title );
        dialogoInfo.setContentText( message );
        dialogoInfo.showAndWait();
    }
    
    public static void error ( String message )
    {
        error( null, message );
    }
    
    public static void error ( String title, String message )
    {            
        Alert dialogoErro = new Alert( Alert.AlertType.ERROR );
        dialogoErro.getDialogPane().setPrefSize( 800,  400 );
        dialogoErro.setTitle( "Erro" );
        dialogoErro.setHeaderText( title == null ? "Ocorreu um erro inesperado" : title );
        dialogoErro.setContentText( message );
        dialogoErro.showAndWait();
    }
    
    public static void alert( String message )
    {
        alert( null, message );
    }
    
    public static void alert( String title, String message )
    {
        Alert dialogoAlert = new Alert( Alert.AlertType.WARNING );
        dialogoAlert.getDialogPane().setPrefSize( 400,  200 );
        dialogoAlert.setTitle( "Aviso" );
        dialogoAlert.setHeaderText( title == null ? "Aviso Importante..." : title );
        dialogoAlert.setContentText( message );
        dialogoAlert.showAndWait();
    }
    
    public static boolean confirm( String message )
    {
        return confirm( null, message );
    }
    
    public static boolean confirm( String title, String message )
    {
        ButtonType btnSim = new ButtonType( "Sim" );
        ButtonType btnNao = new ButtonType( "Não" );
        
        Alert dialogoConfirm = new Alert( Alert.AlertType.CONFIRMATION );
        dialogoConfirm.getDialogPane().setPrefSize( 400,  200 );
        dialogoConfirm.setTitle( "Confirmação" );
        dialogoConfirm.setHeaderText( title == null ? "Você tem certeza ?" : title );
        dialogoConfirm.setContentText( message );
        dialogoConfirm.getButtonTypes().setAll( btnSim, btnNao );
        dialogoConfirm.showAndWait();
        
        return dialogoConfirm.getResult() == btnSim;
    }
    
    public static String input ( String message, String content )
    {
        TextInputDialog dialogInput = new TextInputDialog();
        dialogInput.getDialogPane().setPrefSize( 400,  200 );
        dialogInput.setTitle( "Entrada de Dados" );
        dialogInput.setHeaderText( message );
        dialogInput.setContentText( content );
        dialogInput.getDialogPane().getButtonTypes().remove( ButtonType.CANCEL );
        dialogInput.showAndWait();
        
        return dialogInput.getResult();
    }
    
    
    public static void process( String message, Task<Void> task )
    {
        Service<Void> service = new Service<Void>() 
        {
            @Override
            protected Task<Void> createTask() 
            {
                return task;
            }
        };
        
        ProgressDialog dialog = new ProgressDialog( service );
        dialog.setTitle( "Aguarde..." );
        dialog.setHeaderText( message );
        
        service.start();
    }
}
