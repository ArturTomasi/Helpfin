package com.pa.helpfin.model.db;

import com.pa.helpfin.model.db.fetcher.*;

/**
 * @author artur
 */
public class Schema 
{
    
    public static class Users
    {
        public static Users alias( String alias )
        {
            return new Users( alias );
        }
        
        public  class Columns
        {
            public String ID;
            public String NAME;
            public String PHONE;
            public String CPF;
            public String BIRTH_DATE;
            public String RG;
            public String MAIL;
            public String GENDER;
            public String STATE;
            public String PASSWORD;
            public String LOGIN;
            public String ROLE;
            
            public Columns( String alias ) 
            {
                ID           = alias + "id";
                NAME         = alias + "name";
                PHONE        = alias + "phone";
                CPF          = alias + "cpf";
                BIRTH_DATE   = alias + "birth_date";
                RG           = alias + "rg";
                MAIL         = alias + "mail";
                GENDER       = alias + "gender";
                STATE        = alias + "state";
                PASSWORD     = alias + "password";
                LOGIN        = alias + "login";
                ROLE         = alias + "role";
            }
            
            @Override
            public String toString() 
            {
                return ID           + ", " +
                       NAME         + ", " +
                       PHONE        + ", " +
                       CPF          + ", " +
                       BIRTH_DATE   + ", " +
                       RG           + ", " +
                       MAIL         + ", " +
                       GENDER       + ", " +
                       STATE        + ", " +
                       PASSWORD     + ", " +
                       LOGIN        + ", " +
                       ROLE;
            }
        }
        
        private final String TABLE_NAME =  "users";

        public String name = TABLE_NAME;

        public final String select;
        
        public final UserFetcher fetcher = new UserFetcher();
        
        public static final Users table = new Users( null );
        
        public final Columns columns;

        private Users( String alias ) 
        {
            this.name = alias != null ? TABLE_NAME + " " + alias : TABLE_NAME;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
    }
    
    public static class PostingTypes
    {
        public static PostingTypes alias( String alias )
        {
            return new PostingTypes( alias );
        }
        
        public  class Columns
        {
            
            public String ID;
            public String NAME;
            public String STATE;
            public String INFO;
            
            public Columns( String alias ) 
            {
                ID     = alias + "id";
                NAME   = alias + "name";
                STATE  = alias + "state";
                INFO   = alias + "info";
            }
            

            @Override
            public String toString() 
            {
                return ID    + ", " +
                       NAME  + ", " +
                       STATE + ", " +
                       INFO;
            }
        }
        
        private final String TABLE_NAME =  "posting_types";

        public String name = TABLE_NAME;

        public final String select;
        
        public final PostingTypeFetcher fetcher = new PostingTypeFetcher();
        
        public static final PostingTypes table = new PostingTypes( null );
        
        public final Columns columns;

        private PostingTypes( String alias ) 
        {
            this.name = alias != null ? TABLE_NAME + " " + alias : TABLE_NAME;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
    }
    
    public static class PostingCategories
    {
        public static PostingCategories alias( String alias )
        {
            return new PostingCategories( alias );
        }
        
        public  class Columns
        {
            public String ID;
            public String NAME;
            public String INFO;
            public String REF_POSTING_TYPE;
            public String STATE;
            
            public Columns( String alias ) 
            {
                ID               = alias + "id";
                NAME             = alias + "name";
                INFO             = alias + "info";
                STATE            = alias + "state";
                REF_POSTING_TYPE = alias + "ref_posting_type";
            }
            

            @Override
            public String toString() 
            {
                return ID               + ", " +
                       NAME             + ", " +
                       INFO             + ", " +
                       STATE            + ", " +
                       REF_POSTING_TYPE;
            }
        }
        
        private final String TABLE_NAME =  "posting_categories";

        public String name = TABLE_NAME;

        public final String select;
        
        public final PostingCategoryFetcher fetcher = new PostingCategoryFetcher();
        
        public static final PostingCategories table = new PostingCategories( null );
        
        public final Columns columns;

        private PostingCategories( String alias ) 
        {
            this.name = alias != null ? TABLE_NAME + " " + alias : TABLE_NAME;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
    }
    
    public static class Entities
    {
        public static Entities alias( String alias )
        {
            return new Entities( alias );
        }
        
        public  class Columns
        {
            public String ID;
            public String NAME;
            public String COMPANNY_NAME;
            public String CNPJ;
            public String PHONE;
            public String MAIL;
            public String STATE;
            
            public Columns( String alias ) 
            {
                ID            = alias + "id";
                NAME          = alias + "name";
                COMPANNY_NAME = alias + "companny_name";
                CNPJ          = alias + "cnpj";
                PHONE         = alias + "phone";
                MAIL          = alias + "mail";
                STATE         = alias + "state";
            }
            

            @Override
            public String toString() 
            {
                return ID            + ", " +
                       NAME          + ", " +
                       COMPANNY_NAME + ", " +
                       CNPJ          + ", " +
                       PHONE         + ", " +
                       MAIL          + ", " +
                       STATE;
            }
        }
        
        private final String TABLE_NAME =  "entities";

        public String name = TABLE_NAME;

        public final String select;
        
        public final EntityFetcher fetcher = new EntityFetcher();
        
        public static final Entities table = new Entities( null );
        
        public final Columns columns;

        private Entities( String alias ) 
        {
            this.name = alias != null ? TABLE_NAME + " " + alias : TABLE_NAME;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
    }
    
    public static class CompletionTypes
    {
        public static CompletionTypes alias( String alias )
        {
            return new CompletionTypes( alias );
        }
        
        public  class Columns
        {
            public String ID;
            public String NAME;
            public String INFO;
            public String TYPE;
            public String STATE;
            
            public Columns( String alias ) 
            {
                ID               = alias + "id";
                NAME             = alias + "name";
                INFO             = alias + "info";
                TYPE             = alias + "type";
                STATE            = alias + "state";
            }
            

            @Override
            public String toString() 
            {
                return ID               + ", " +
                       NAME             + ", " +
                       INFO             + ", " +
                       TYPE             + ", " +
                       STATE;
            }
        }
        
        private final String TABLE_NAME =  "completion_types";

        public String name = TABLE_NAME;

        public final String select;
        
        public final CompletionTypeFetcher fetcher = new CompletionTypeFetcher();
        
        public static final CompletionTypes table = new CompletionTypes( null );
        
        public final Columns columns;

        private CompletionTypes( String alias ) 
        {
            this.name = alias != null ? TABLE_NAME + " " + alias : TABLE_NAME;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
    }
    
    public static class Postings
    {
        public static Postings alias( String alias )
        {
            return new Postings( alias );
        }
        
        public  class Columns
        {
            public String ID;
            public String NAME;
            public String INFO;
            public String REAL_DATE;
            public String ESTIMATE_DATE;
            public String FL_COMPLETION_AUTO;
            public String REAL_VALUE;
            public String ESTIMATE_VALUE;
            public String PORTION;
            public String PORTION_TOTAL;
            public String FL_REPEAT;
            public String STATE;
            public String REF_POSTING_CATEGORY;
            public String REF_USER;
            public String REF_COMPLETION_TYPE;
            public String REF_ENTITY;
            public String REF_POSTING;
            
            
            public Columns( String alias ) 
            {
                ID                    = alias + "id";
                NAME                  = alias + "name";
                INFO                  = alias + "info";
                REAL_DATE             = alias + "real_date";
                ESTIMATE_DATE         = alias + "estimate_date";
                FL_COMPLETION_AUTO    = alias + "fl_completion_auto";
                REAL_VALUE            = alias + "real_value";
                ESTIMATE_VALUE        = alias + "estimate_value";
                PORTION               = alias + "portion";
                PORTION_TOTAL         = alias + "portion_total";
                FL_REPEAT             = alias + "fl_repeat";
                STATE                 = alias + "state";
                REF_POSTING_CATEGORY  = alias + "ref_posting_category";
                REF_USER              = alias + "ref_user";
                REF_COMPLETION_TYPE   = alias + "ref_completion_type";
                REF_ENTITY            = alias + "ref_entity";
                REF_POSTING           = alias + "ref_posting";
            }
            

            @Override
            public String toString() 
            {
                return ID                    + ", " +
                       NAME                  + ", " +
                       INFO                  + ", " +
                       REAL_DATE             + ", " +
                       ESTIMATE_DATE         + ", " +
                       FL_COMPLETION_AUTO    + ", " +
                       REAL_VALUE            + ", " +
                       ESTIMATE_VALUE        + ", " +
                       PORTION               + ", " +
                       PORTION_TOTAL         + ", " +
                       FL_REPEAT             + ", " +
                       STATE                 + ", " +
                       REF_POSTING_CATEGORY  + ", " +
                       REF_USER              + ", " +
                       REF_COMPLETION_TYPE   + ", " +
                       REF_ENTITY            + ", " +
                       REF_POSTING;
            }
        }
        
        private final String TABLE_NAME =  "postings";

        public String name = TABLE_NAME;

        public final String select;
        
        public final PostingFetcher fetcher = new PostingFetcher();
        
        public static final Postings table = new Postings( null );
        
        public final Columns columns;

        private Postings( String alias ) 
        {
            this.name = alias != null ? TABLE_NAME + " " + alias : TABLE_NAME;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
    }
    
    public static class Attachments
    {
        public static Attachments alias( String alias )
        {
            return new Attachments( alias );
        }
        
        public  class Columns
        {
            public String ID;
            public String NAME;
            public String INFO;
            public String URL;
            public String REF_POSTING;
            
            public Columns( String alias ) 
            {
                ID                    = alias + "id";
                NAME                  = alias + "name";
                INFO                  = alias + "info";
                URL                   = alias + "url";
                REF_POSTING           = alias + "ref_posting";
            }
            

            @Override
            public String toString() 
            {
                return ID                    + ", " +
                       NAME                  + ", " +
                       INFO                  + ", " +
                       URL                   + ", " +
                       REF_POSTING;
            }
        }
        
        private final String TABLE_NAME =  "attachments";

        public String name = TABLE_NAME;

        public final String select;
        
        public final AttachmentFetcher fetcher = new AttachmentFetcher();
        
        public static final Attachments table = new Attachments( null );
        
        public final Columns columns;

        private Attachments( String alias ) 
        {
            this.name = alias != null ? TABLE_NAME + " " + alias : TABLE_NAME;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
    }
}
