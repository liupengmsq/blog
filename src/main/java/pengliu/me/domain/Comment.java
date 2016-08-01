package pengliu.me.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by peng on 16-4-8.
 */
@Entity
@Table(name = "comment", schema = "myblog", catalog = "")
public class Comment
{
    private Integer id;
    private String content;
    private Timestamp createTime;
    private Blog blog;
    private CommentUser commentUser;

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
    @Column(name = "create_time", nullable = false)
    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        if (createTime != null ? !createTime.equals(comment.createTime) : comment.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_id", nullable = false)
    public Blog getBlog()
    {
        return blog;
    }

    public void setBlog(Blog blog)
    {
        this.blog = blog;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_user_id", nullable = false)
    public CommentUser getCommentUser()
    {
        return commentUser;
    }

    public void setCommentUser(CommentUser commentUser)
    {
        this.commentUser = commentUser;
    }
}
