package co.marlonlom.utils.mapping.sql.tests.blog_posts;

import co.marlonlom.utils.mapping.sql.annotation.SqlColumn;
import co.marlonlom.utils.mapping.sql.annotation.SqlJoin;
import co.marlonlom.utils.mapping.sql.annotation.SqlTable;

import java.io.Serializable;
import java.util.Date;

@SqlTable
public class BlogPost implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -9046487342158467608L;

    @SqlColumn(name = "post_entry_id", type = Long.class)
    private Long entryId;
    @SqlColumn(name = "post_title", type = String.class)
    private String entryTitle;
    @SqlColumn(name = "post_owner", type = String.class)
    private String entryAuthor;
    @SqlColumn(name = "post_content", type = String.class)
    private String entryDetail;
    @SqlColumn(name = "post_date_creation", type = Date.class)
    private Date entryDate;
    @SqlJoin(from = BlogCategory.class)
    private BlogCategory entryCategory;

    /**
     * Auto-generated constructor stub
     */
    public BlogPost() {
        super();
    }

    public BlogCategory getEntryCategory() {
        return entryCategory;
    }

    public void setEntryCategory(BlogCategory entryCategory) {
        this.entryCategory = entryCategory;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryDetail() {
        return entryDetail;
    }

    public void setEntryDetail(String entryDetail) {
        this.entryDetail = entryDetail;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getEntryTitle() {
        return entryTitle;
    }

    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    @Override
    public String toString() {
        return "BlogPost [entryId=" + entryId + ", entryTitle=" + entryTitle + ", entryAuthor=" + entryAuthor
                + ", entryDetail=" + entryDetail + ", entryDate=" + entryDate + ", entryCategory=" + entryCategory
                + "]";
    }

}
