/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pa.helpfin.view.util;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingType;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

/**
 *
 * @author artur
 */
public class PostingTree
    extends 
        TreeView<Object>
{
    private Image ICON_TYPE = new Image( ResourceLocator.getInstance().getImageResource( "types.png" ) );
    private Image ICON_CATEGORY = new Image( ResourceLocator.getInstance().getImageResource( "category.png" ) );
    private Image ICON_ROOT = new Image( ResourceLocator.getInstance().getImageResource( "posting.png" ) );
    
    public static class Events
    {
        public static final EventType ON_SELECT = new EventType( "onSelectNode" );
    }
    
    public PostingTree() 
    {
        initComponents();
        
        loadData();
    }
    
    private void loadData()
    {
        try
        {
            TreeItem<Object> rootItem = new TreeItem<>( "LANÇAMENTOS" );
            rootItem.setExpanded( true );
           
            ImageView imageRoot = new ImageView();
            imageRoot.setImage( ICON_ROOT );
            imageRoot.setFitHeight( 20 );
            imageRoot.setFitWidth( 20 );
            imageRoot.setCache( true );
            imageRoot.setCacheHint( CacheHint.SPEED );
            rootItem.setGraphic( imageRoot ); 
            
            loadRecurcive( rootItem);

            setRoot( rootItem );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    private void loadRecurcive( TreeItem<Object> root ) throws Exception
    {
        List<PostingType> types = com.pa.helpfin.model.ModuleContext.getInstance().getPostingTypeManager().getValues();
      
        for( PostingType type : types )
        {
            TreeItem nodeType = new TreeItem( type );
            
            ImageView imageType = new ImageView();
            imageType.setImage( ICON_TYPE );
            imageType.setFitHeight( 20 );
            imageType.setFitWidth( 20 );
            imageType.setCache( true );
            imageType.setCacheHint( CacheHint.SPEED );
            
            nodeType.setGraphic( imageType ); 

            List<PostingCategory> categories = com.pa.helpfin.model.ModuleContext.getInstance().getPostingCategoryManager().getValues( type.getId() );

            for( PostingCategory category : categories )
            {
                  TreeItem nodeCategory = new TreeItem( category );

                  ImageView imageCategory = new ImageView();
                  imageCategory.setImage( ICON_CATEGORY );
                  imageCategory.setFitHeight( 20 );
                  imageCategory.setFitWidth( 20 );
                  imageCategory.setCache( true );
                  imageCategory.setCacheHint( CacheHint.SPEED );
                  nodeCategory.setGraphic( imageCategory ); 

                  nodeType.getChildren().add( nodeCategory );
            }

            root.getChildren().add( nodeType );
        }
    }

    public PostingCategory getPostingCategory()
    {
        PostingCategory category = null;
        
        TreeItem item = getSelectionModel().getSelectedItem();
        
        if( item != null )
        {
            if( item.getValue() instanceof PostingCategory )
            {
                category = (PostingCategory) item.getValue();
            }
        }
            
        return category;
    }

    public PostingType getPostingType()
    {
        PostingType type = null;
        
        TreeItem item = getSelectionModel().getSelectedItem();
        
        if( item != null )
        {
            if( item.getValue() instanceof PostingType )
            {
                type = (PostingType) item.getValue();
            }
        }
            
        return type;
    }

    public Object getSelectedNode()
    {
        TreeItem item = getSelectionModel().getSelectedItem();
        
        if( item != null )
        {
            if( item.getValue() instanceof PostingType )
            {
                return (PostingType) item.getValue();
            }
            
            if( item.getValue() instanceof PostingCategory )
            {
                return (PostingCategory) item.getValue();
            }
           
            if( item.getValue() instanceof String )
            {
                return (String) item.getValue();
            }
        }
            
        return null;
    }
    
    private void initComponents()
    {
        setCache( true );
        setCacheHint(CacheHint.SPEED);
        setCacheShape( true );
        setShowRoot( true );
        setCursor( Cursor.HAND );

        getStylesheets().add( "config/tree.css" );
        setStyle( "-fx-background-color: transparent; -fx-border-color: #dadada;" );
        
        getSelectionModel().selectedItemProperty().addListener( new ChangeListener() 
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) 
            {
                PostingTree.this.fireEvent( new Event( Events.ON_SELECT ) );
            }
      } );
    }
}
