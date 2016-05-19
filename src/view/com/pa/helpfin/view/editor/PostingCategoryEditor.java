package com.pa.helpfin.view.editor;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.view.selectors.PostingTypeSelector;
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
 *
 * @author artur
 */
public class PostingCategoryEditor 
    extends 
        AbstractEditor<PostingCategory>
{
    public PostingCategoryEditor( EditorCallback<PostingCategory> callback ) 
    {
        super( callback );
        
        initComponents();
        
        setTitle( "Editor de Categorias" );
        setHeaderText( "Editor de Categorias de Lançamentos" );
        
        setSource( source );
    }

    
    
    @Override
    protected void validadeInput( List<String> erros ) throws Exception 
    {
        if( ! nameField.isValid() )
            erros.add( "Nome" );
        
        if( stateSelector.getSelectedIndex() == -1 )
            erros.add( "Situação" );
        
        if( postingTypeSelector.getSelectedIndex() == -1 )
            erros.add( "Tipo" );
        
        erros.addAll( com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getPostingCategoryManager()
                                        .isUnique( source ) );
    }

    
    
    @Override
    protected void obtainInput() 
    {
        source.setPostingType( postingTypeSelector.getSelected() != null ? postingTypeSelector.getSelected().getId() : 0 );
        source.setName( nameField.getText() );
        source.setInfo( infoField.getHtmlText() );
        source.setState( stateSelector.getSelectedIndex() );
    }

    
    

    @Override
    protected void resize() 
    {
        getDialogPane().requestLayout();
    }
    
    

    @Override
    protected void setSource( PostingCategory source ) 
    {
        try
        {
            nameField.setText( source.getName() );
            infoField.setHtmlText( source.getInfo() );
            postingTypeSelector.setSelected( com.pa.helpfin.model.ModuleContext
                                                        .getInstance()
                                                        .getPostingTypeManager()
                                                        .getValue( source.getPostingType() ) );

            stateSelector.setSelectedIndex( source.getState() );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    private void initComponents()
    {
        getDialogPane().setPrefHeight( 500 );
       
        ColumnConstraints colName = new ColumnConstraints();
        colName.setPercentWidth( 18 );
        colName.setHalignment( HPos.LEFT );
     
        ColumnConstraints colField = new ColumnConstraints();
        colField.setPercentWidth( 82 );
        colField.setHalignment( HPos.LEFT );
        
        gridPane.getColumnConstraints().addAll( colName, colField );
        
        gridPane.setVgap( 20 );
        gridPane.setHgap( 20 );
        gridPane.setStyle( "-fx-padding: 30;" );
        
        gridPane.add( lbName,              0, 0, 1, 1 );
        gridPane.add( nameField,           1, 0, 1, 1 );
        
        gridPane.add( lbType,              0, 1, 1, 1 );
        gridPane.add( postingTypeSelector, 1, 1, 1, 1 );
        
        gridPane.add( lbState,             0, 2, 1, 1 );
        gridPane.add( stateSelector,       1, 2, 1, 1 );
        
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
    
    private GridPane gridPane                       = new GridPane();
    
    private LabelField lbState                      = new LabelField( "Situação", true );
    private StateSelector stateSelector             = new StateSelector();
    
    private LabelField lbType                       = new LabelField( "Tipo Lançamento", true );
    private PostingTypeSelector postingTypeSelector = new PostingTypeSelector();
    
    private MaskTextField nameField                 = new MaskTextField();
    private LabelField lbName                       = new LabelField( "Nome", true );
    
    private HTMLEditor infoField                    = new HTMLEditor();
    
    private TabPane tabPane                         = new TabPane();
    private Tab tabGeral                            = new Tab();
    private Tab tabInfo                             = new Tab();
}
