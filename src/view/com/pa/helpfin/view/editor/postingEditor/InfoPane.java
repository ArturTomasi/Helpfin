package com.pa.helpfin.view.editor.postingEditor;

import com.pa.helpfin.model.data.Posting;
import javafx.scene.control.Tab;
import javafx.scene.web.HTMLEditor;

/**
 * @author artur
 */
public class InfoPane 
    extends 
        Tab
{
    public InfoPane() 
    {
        initComponents();
    }
    
    public void obtainInput( Posting posting )
    {
        posting.setInfo( hTMLEditor.getHtmlText() );
    }
    
    public void setSource( Posting posting )
    {
        hTMLEditor.setHtmlText( posting.getInfo() );
    }
    
    private void initComponents()
    {
        setClosable( false );
        setText( "Informações" );
        setContent( hTMLEditor );
    }
    
    private HTMLEditor hTMLEditor = new HTMLEditor();
}
