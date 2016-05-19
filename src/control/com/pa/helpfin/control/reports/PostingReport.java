package com.pa.helpfin.control.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.UserCache;
import java.util.List;

/**
 * @author artur
 */
public class PostingReport 
    extends 
        AbstractReport
{
    private List<Posting> postings;

    public PostingReport() 
    {
        super( true );
    }

    
    public void setSource( List<Posting> postings )
    {
        this.postings = postings;
    }
    
    @Override
    protected void generateDocument( Document document ) throws Exception 
    {
        setTitle( "Relatório de Lançamentos" );
        setSubTitle( "Relatório de Lançamento customizado com filtros " );
        
        newLine();        
        
        if( postings != null )
        {
            Table table = new Table( 2f, 20f, 10f, 10f, 8f, 8f, 8f, 8f );
            
            table.setHeader( "#", "Nome", "Responsável", "Categoria", "Data Estimada", "Valor Estimado", "Data Real", "Valor Real" );
            
            postings.forEach( (posting) -> 
            {
                try
                {
                    Image state = Image.getInstance( ResourceLocator.getInstance().getImageResource( posting.getState() == Posting.STATE_FINISHED  ? "finish.png" : 
                                                                                                     posting.getState() == Posting.STATE_PROGRESS  ? "play.png"   :
                                                                                                     posting.getState() == Posting.STATE_REGISTRED ? "new.png" : "delete.png" ) );
                    
                    PostingCategory category = com.pa.helpfin.model.ModuleContext.getInstance().getPostingCategoryManager().getValue( posting.getPostingCategory() );
                    
                    table.addRow( state, 
                                  posting.getName(), 
                                  UserCache.getUser( posting.getUser() ),
                                  category.getName(),
                                  posting.getEstimateDate() != null ? df.format( posting.getEstimateDate() ) : "n/d",
                                  "R$" + posting.getEstimateValue(),
                                  posting.getRealDate() != null ? df.format( posting.getRealDate() ) : "n/d",
                                  "R$" + posting.getRealValue() );
                                  
                }
                
                catch( Exception e )
                {
                    ApplicationUtilities.logException( e );
                }
            } );
            
            document.add( table );
        }
        
        else
        {
            document.add( new Paragraph( "Sem conteúdo" ) );
        }
    }
    
}
