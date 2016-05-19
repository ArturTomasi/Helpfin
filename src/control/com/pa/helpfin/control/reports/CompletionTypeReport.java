package com.pa.helpfin.control.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.CompletionType;
import com.pa.helpfin.model.data.Entity;
import java.util.List;

/**
 * @author artur
 */
public class CompletionTypeReport 
    extends 
        AbstractReport
{
    private List<CompletionType> completionTypes;
    
    public CompletionTypeReport() 
    {
    }
    
  
    public void setSource( List<CompletionType> completionTypes )
    {
        this.completionTypes = completionTypes;
    }

    
    
    @Override
    protected void generateDocument( Document document ) throws Exception 
    {
        setTitle( "Relatório de Tipos de Finalização" );
        setSubTitle( "Relatório de Gerencial dos Tipos de Finalização do HelpFin" );
        
        newLine();

        Table table = new Table( 0.1f, 0.5f, 0.4f );
        table.setWidthPercentage( 100f );
        
        table.setHeader(  "#", "Nome", "Tipo"  );

        for ( CompletionType type : completionTypes ) 
        {
            table.addRow( Image.getInstance( ResourceLocator.getInstance().getImageResource( type.getState() == Entity.STATE_ACTIVE ? "finish.png" : "delete.png" ) ),
                          type.getName(),
                          CompletionType.TYPES[ type.getType() ] );
        }

        document.add( table );
    }
    
}
