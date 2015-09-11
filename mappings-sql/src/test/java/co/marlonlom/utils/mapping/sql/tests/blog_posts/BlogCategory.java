package co.marlonlom.utils.mapping.sql.tests.blog_posts;

import co.marlonlom.utils.mapping.sql.annotation.SqlColumn;
import co.marlonlom.utils.mapping.sql.annotation.SqlTable;

import java.io.Serializable;

@SqlTable
public final class BlogCategory implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3030431343407425760L;
    @SqlColumn(name = "category_id", type = Long.class)
    private Long categoryId;
    @SqlColumn(name = "category_name", type = String.class)
    private String categoryTitle;

    /**
     * Auto-generated constructor stub
     */
    public BlogCategory() {
        super();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    @Override
    public String toString() {
        return "BlogCategory [categoryId=" + categoryId + ", categoryTitle=" + categoryTitle + "]";
    }

}
