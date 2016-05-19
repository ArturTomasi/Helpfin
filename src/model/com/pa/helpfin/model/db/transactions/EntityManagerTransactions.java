package com.pa.helpfin.model.db.transactions;

import com.pa.helpfin.model.data.Entity;
import com.pa.helpfin.model.data.EntityFilter;
import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.Schema.Entities.Columns;
import com.pa.helpfin.model.db.Schema.Entities;
import java.util.ArrayList;
import java.util.List;

/**
 * @author artur
 */
public class EntityManagerTransactions
{
    public void addEntity( Database db, Entity entity ) throws Exception
    {
        if( entity == null )
        {
            throw new Exception( "Entity cannot be null" );
        } 

        Entities E = Entities.table;

        String sql = "insert into " + E.name +
                     "( " +
                     E.columns.NAME          + ", " +
                     E.columns.CNPJ          + ", " +
                     E.columns.MAIL          + ", " +
                     E.columns.PHONE         + ", " +
                     E.columns.COMPANNY_NAME + ", " +
                     E.columns.STATE         + 
                     " ) values ( " +
                     db.quote( entity.getName() )         + ", " +
                     db.quote( entity.getCnpj() )         + ", " +
                     db.quote( entity.getMail() )         + ", " +
                     db.quote( entity.getPhone() )        + ", " +
                     db.quote( entity.getCompannyName() ) + ", " +
                     entity.getState()                    + 
                     " )";
        
        db.executeCommand( sql );
    }
    
    public void updateEntity( Database db, Entity entity ) throws Exception
    {
        if( entity == null )
        {
            throw new Exception( "Entity cannot be null" );
        } 

        Entities E = Entities.table;

        String sql = "update " + E.name + " set " +
                     E.columns.NAME          + " = " + db.quote( entity.getName() )         + ", " +
                     E.columns.CNPJ          + " = " + db.quote( entity.getCnpj() )         + ", " +
                     E.columns.COMPANNY_NAME + " = " + db.quote( entity.getCompannyName() ) + ", " +
                     E.columns.MAIL          + " = " + db.quote( entity.getMail() )         + ", " +
                     E.columns.PHONE         + " = " + db.quote( entity.getPhone() )        + ", " +
                     E.columns.STATE         + " = " + entity.getState()                    +
                     " where " +
                     E.columns.ID           + " = " + entity.getId();
        
        db.executeCommand( sql );
    }
    
    public void deleteEntity( Database db, Entity entity ) throws Exception
    {
        if( entity == null )
        {
            throw new Exception( "Entity cannot be null" );
        } 

        Entities E = Entities.table;

        String sql = "update " + E.name + " set " +
                     E.columns.STATE + " = " + Entity.STATE_INACTIVE +
                     " where " +
                     E.columns.ID    + " = " + entity.getId();
        
        db.executeCommand( sql );
    }
    
    public Entity getEntity( Database db, int id ) throws Exception
    {
        Entities E = Entities.table;

        String sql = E.select  +
                     " where " +
                     E.columns.STATE + " = " + Entity.STATE_ACTIVE +
                     " and "   +
                     E.columns.ID    + " = " + id;
        
        return db.fetchOne( sql, E.fetcher );
    }
    
    public Entity getEntity( Database db, String name ) throws Exception
    {
        Entities M = Entities.table;

        String sql = M.select  +
                     " where " +
                     M.columns.STATE                     + " = " + Entity.STATE_ACTIVE +
                     " and "   +
                     db.upper( M.columns.NAME )          + " = " + db.upper( db.quote( name ) ) +
                     " or "    +
                     db.upper( M.columns.COMPANNY_NAME ) + " = " + db.upper( db.quote( name ) );
        
        return db.fetchOne( sql, M.fetcher );
    }

    public List<Entity> getEntities( Database db, boolean showInactives ) throws Exception
    {
        Entities E = Entities.table;

        String sql = E.select;
        
        if( ! showInactives )
        {
            sql += " where " +
                    E.columns.STATE + " = " + Entity.STATE_ACTIVE;
        }
        
        sql += " order by " + E.columns.NAME;
        
        return db.fetchAll( sql, E.fetcher );
    }

    public List<Entity> getEntities( Database db, EntityFilter filter ) throws Exception
    {
        Entities E = Entities.table;

        StringBuilder sb = new StringBuilder();
                
        sb.append( E.select ).append( " where true " );
        
        
        filter.getConditions().forEach( ( key, values ) ->
        {
            String condition = " and ( ";
            
            for( int i = 0; i < values.size(); i++ )
            {
                switch ( key )
                {
                    case EntityFilter.CNPJ:
                    {
                        condition += E.columns.CNPJ + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case EntityFilter.COMPANNY_NAME:
                    {
                        condition += E.columns.COMPANNY_NAME + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case EntityFilter.MAIL:
                    {
                        condition += E.columns.MAIL + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case EntityFilter.NAME:
                    {
                        condition += E.columns.NAME + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case EntityFilter.PHONE:
                    {
                        condition += E.columns.PHONE + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case EntityFilter.STATE:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option option = (Option) values.get( i );
                            
                            condition += E.columns.STATE + " = " + option.getId();
                        }
                    }
                    break;
                }
                
                condition += i == values.size() - 1 ? " ) " : " or ";
            }
            
            sb.append( condition );
            
        } );
        
        sb.append( " order by " ).append( E.columns.NAME );
        
        return db.fetchAll( sb.toString(), E.fetcher );
    }
    
    public List<String> isUnique( Database db, Entity entity ) throws Exception
    {
        List<String> notUnique = new ArrayList();
        
        Columns C = Entities.table.columns;

        if( ! isUnique( db, entity, entity.getName(), C.NAME ) )
        {
            notUnique.add( "Nome: " + entity.getName() );
        }
        
        if( ! isUnique( db, entity, entity.getCompannyName(), C.COMPANNY_NAME ) )
        {
            notUnique.add( "Razão Social: " + entity.getCompannyName() );
        }
        
        if( ! isUnique( db, entity, entity.getCnpj(), C.CNPJ ) )
        {
            notUnique.add( "Cnpj: " + entity.getCnpj() );
        }
        
        if( ! isUnique( db, entity, entity.getMail(), C.MAIL ) )
        {
            notUnique.add( "Email: " + entity.getMail() );
        }
        
        if( ! isUnique( db, entity, entity.getPhone(), C.PHONE ) )
        {
            notUnique.add( "Telefone: " + entity.getPhone() );
        }
        
        if ( ! notUnique.isEmpty() )
        {
            notUnique.add( 0 , "\nNão são únicos:" );
        }
        
        return notUnique;
    }
    
    private boolean isUnique( Database db, Entity entity, String value, String column ) throws Exception
    {
        Entities E = Entities.table;
        
        String sql = " select count( * ) from " + 
                     E.name + 
                     " where " +
                     db.upper( column ) + " = " + db.upper( db.quote( value ) ) +
                     " and " +
                     E.columns.ID + " <> " + entity.getId();
        
        return value == null || db.queryInteger( sql ) == 0;
    }
}
