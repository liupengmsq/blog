package pengliu.me.domain;

import pengliu.me.common.BlogStatus;
import pengliu.me.utils.TransferUtil;
import pengliu.me.vo.BlogVo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by peng on 16-4-8.
 */
@Entity
@Table(name = "blog", schema = "myblog", catalog = "")
public class Blog
{
    private Integer id;
    private String title;
    private String summary;
    private String content;
    private Timestamp updateTime;
    private Timestamp createTime;
    private BlogStatus status;
    private Integer showCount;
    private User user;
    private Category category;
    private String format;
    private Set<Tag> tags = new HashSet<Tag>();
    private Set<Comment> comments = new HashSet<Comment>();

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false)
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Basic
    @Column(name = "summary", nullable = false)
    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    @Basic
    @Column(name = "content", nullable = false)
    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Basic
    @Column(name = "update_time", nullable = false)
    public Timestamp getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime)
    {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public BlogStatus getStatus()
    {
        return status;
    }

    public void setStatus(BlogStatus status)
    {
        this.status = status;
    }

    @Basic
    @Column(name = "show_count", nullable = false)
    public Integer getShowCount()
    {
        return showCount;
    }

    public void setShowCount(Integer showCount)
    {
        this.showCount = showCount;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blog blog = (Blog) o;

        if (id != null ? !id.equals(blog.id) : blog.id != null) return false;
        if (title != null ? !title.equals(blog.title) : blog.title != null) return false;
        if (summary != null ? !summary.equals(blog.summary) : blog.summary != null) return false;
        if (content != null ? !content.equals(blog.content) : blog.content != null) return false;
        if (updateTime != null ? !updateTime.equals(blog.updateTime) : blog.updateTime != null) return false;
        if (createTime != null ? !createTime.equals(blog.createTime) : blog.createTime != null) return false;
        if (status != null ? !status.equals(blog.status) : blog.status != null) return false;
        if (showCount != null ? !showCount.equals(blog.showCount) : blog.showCount != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (showCount != null ? showCount.hashCode() : 0);
        return result;
    }

    @ManyToMany(fetch = FetchType.LAZY)//, cascade = CascadeType.REMOVE)
    @JoinTable(name = "blog_tag",
            joinColumns = {@JoinColumn(name = "blog_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false, updatable = false)})
    public Set<Tag> getTags()
    {
        return tags;
    }

    public void setTags(Set<Tag> tags)
    {
        this.tags = tags;
    }

    @ManyToOne(fetch = FetchType.LAZY)//, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_name", nullable = false)
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)//, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id", nullable = false)
    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "blog")
    public Set<Comment> getComments()
    {
        return comments;
    }

    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
    }

    @Basic
    @Column(name = "format", nullable = false)
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
