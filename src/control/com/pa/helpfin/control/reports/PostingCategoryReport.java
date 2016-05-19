package com.pa.helpfin.control.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.PostingCategory;
import java.util.List;

/**
 * @author artur
 */
public class PostingCategoryReport 
    extends 
        AbstractReport
{
    private List<PostingCategory> postingCategories;
    
    public PostingCategoryReport() 
    {
        super( true );
    }
    
  
    public void setSource( List<PostingCategory> postingCategories )
    {
        this.postingCategories = postingCategories;
    }

    
    
    @Override
    protected void generateDocument( Document document ) throws Exception 
    {
        setTitle( "Relatório de Categorias de Lançamentos" );
        setSubTitle( "Relatório de Gerencial das Categorias de Lançamentos do HelpFin" );
        
        newLine();

        Table table = new Table( 0.1f, 0.5f, 0.4f );
        table.setWidthPercentage( 100f );
        
        table.setHeader(  "#", "Nome", "Tipo"  );

        for ( PostingCategory postingCategory : postingCategories ) 
        {
            table.addRow( Image.getInstance( ResourceLocator.getInstance().getImageResource( postingCategory.getState() == PostingCategory.STATE_ACTIVE ? "finish.png" : "delete.png" ) ),
                          postingCategory.getName(),
                          com.pa.helpfin.model.ModuleContext.getInstance().getPostingTypeManager().getValue( postingCategory.getPostingType() ) );
        }

        document.add( table );
    }
    
}
