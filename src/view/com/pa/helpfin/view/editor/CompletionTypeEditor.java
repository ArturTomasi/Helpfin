package com.pa.helpfin.view.editor;

import com.pa.helpfin.model.data.CompletionType;
import com.pa.helpfin.view.selectors.TypeCompletionSelector;
import com.pa.helpfin.view.selectors.StateSelector;
import com.pa.helpfin.view.util.EditorCallback;
import com.pa.helpfin.view.util.LabelField;
import com.pa.helpfin.view.util.MaskTextField;
import java.util.List;
import javafx.geometry.HPos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;

/**
 * @author artur
 */
public class CompletionTypeEditor
    extends 
        AbstractEditor<CompletionType>
{

    public CompletionTypeEditor( EditorCallback<CompletionType> callback )
    {
        super( callback );
        
        setTitle( "Editor de Tipos de Finalizações" );
        setHeaderText( "Editor de Tipos de Finalizações" );
        
        initComponents();
        
        setSource( source );
    }
    
    

    @Override
    protected void validadeInput( List<String> erros ) throws Exception 
    {
        if( ! nameField.isValid() )
            erros.add( "Nome" );
        
        if( stateSelector.getSelectedIndex() == -1 )
            erros.add( "Situação" );
        
        if( completionTypeSelector.getSelectedIndex() == -1 )
            erros.add( "Tipo" );
        
        erros.addAll( com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getCompletionTypeManager()
                                        .isUnique( source ) );
    }
    
    

    @Override
    protected void obtainInput()
    {
        source.setType( completionTypeSelector.getSelectedIndex() );
        source.setName( nameField.getText() );
        source.setInfo( infoField.getHtmlText() );
        source.setState( stateSelector.getSelectedIndex() );
    }
    
    

    @Override
    protected void resize() 
    {
        nameField.setPrefWidth( getWidth() - lbName.getWidth() - 10 ); 
        
        getDialogPane().requestLayout();
    }
    
    

    @Override
    protected void setSource( CompletionType source ) 
    {
        nameField.setText( source.getName() );
        infoField.setHtmlText( source.getInfo() );
        completionTypeSelector.setSelectedIndex( source.getType() );
        stateSelector.setSelectedIndex( source.getState() );
    }
    
    
    
    private void initComponents()
    {
        getDialogPane().setPrefHeight( 500 );
        
        ColumnConstraints colName = new ColumnConstraints();
        colName.setPercentWidth( 10 );
        colName.setHalignment( HPos.LEFT );
     
        ColumnConstraints colField = new ColumnConstraints();
        colField.setPercentWidth( 90 );
        colField.setHalignment( HPos.LEFT );
        
        gridPane.getColumnConstraints().addAll( colName, colField );
        
        gridPane.setVgap( 20 );
        gridPane.setHgap( 20 );
        gridPane.setStyle( "-fx-padding: 30;" );
        
        gridPane.add( lbName,                 0, 0, 1, 1 );
        gridPane.add( nameField,              1, 0, 1, 1 );
        
        gridPane.add( lbType,                 0, 1, 1, 1 );
        gridPane.add( completionTypeSelector, 1, 1, 1, 1 );
        
        gridPane.add( lbState,                0, 2, 1, 1 );
        gridPane.add( stateSelector,          1, 2, 1, 1 );
        
        tabGeral.setText( "Geral" );
        tabGeral.setClosable( false );
        tabGeral.setContent( gridPane );
        
        tabInfo.setText( "Informações" );
        tabInfo.setClosable( false );
        tabInfo.setContent( infoField );
        
        tabPane.getTabs().add( tabGeral );
        tabPane.getTabs().add( tabInfo  );
         
        getDialogPane().setContent( tabPane );
    }
    
    private GridPane gridPane                             = new GridPane();
    
    private LabelField lbState                            = new LabelField( "Situação", true );
    private StateSelector stateSelector                   = new StateSelector();
    
    private LabelField lbType                             = new LabelField( "Tipo", true );
    private TypeCompletionSelector completionTypeSelector = new TypeCompletionSelector();
    
    private MaskTextField nameField                       = new MaskTextField();
    private LabelField lbName                             = new LabelField( "Nome", true );
    
    private HTMLEditor infoField                          = new HTMLEditor();
    
    private TabPane tabPane                               = new TabPane();
    private Tab tabGeral                                  = new Tab();
    private Tab tabInfo                                   = new Tab();
}
