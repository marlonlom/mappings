package co.marlonlom.utils.mapping.sql.tests.blog_posts;

import co.marlonlom.utils.mapping.sql.results.SqlResultsMapper;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Test case for applying sql mapping on a sql table, using ms access database connection and example information
 *
 * @author marlonlom
 */
public final class BlogPostsMappingResultsTest extends TestCase {

    private Connection dbConn;

    private final Connection connect() throws Exception {
        String connectionString = "jdbc:odbc:Driver= "
                + "{Microsoft Access Driver (*.mdb, *.accdb)};DBQ=D:\\demo\\Database11.mdb;";
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        return DriverManager.getConnection(connectionString, "", "");
    }

    public void setUp() throws Exception {
        super.setUp();
        this.setDbConn(connect());
    }

    public void testToList() throws Exception {
        String sql = "SELECT bp.post_entry_id, bp.post_title, bc.category_id, bp.post_content, bp.post_owner, bp.post_date_creation FROM blog_categories bc INNER JOIN blog_posts bp ON bc.category_id = bp.post_category";
        PreparedStatement st = this.getDbConn().prepareStatement(sql);
        ResultSet query = st.executeQuery();
        SqlResultsMapper<BlogPost> mapper = SqlResultsMapper.create();
        List<BlogPost> posts = null;
        if (query != null) {
            posts = mapper.toList(query, BlogPost.class);
            query.close();
        }
        st.close();
        assertNotNull(posts);
    }

    public void testToObject() throws Exception {
        String sql = "SELECT * FROM blog_posts bp INNER JOIN blog_categories bc ON bc.category_id = bp.post_category WHERE bp.post_entry_id = ?";
        PreparedStatement st = this.getDbConn().prepareStatement(sql);
        st.setInt(1, 1);
        ResultSet query = st.executeQuery();
        SqlResultsMapper<BlogPost> mapper = SqlResultsMapper.create();
        BlogPost blogPost = null;
        if (query != null) {
            blogPost = mapper.toObject(query, BlogPost.class);
            query.close();
        }
        st.close();
        assertNotNull(blogPost);
    }

    public Connection getDbConn() {
        return dbConn;
    }

    public void setDbConn(Connection dbConn) {
        this.dbConn = dbConn;
    }
}