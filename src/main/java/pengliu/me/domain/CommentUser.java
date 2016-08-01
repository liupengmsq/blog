package pengliu.me.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by peng on 16-4-8.
 */
@Entity
@Table(name = "comment_user", schema = "myblog", catalog = "")
public class CommentUser
{
    private Integer id;
    private String name;
    private String email;
    private String blogUrl;
    private String remoteIp;
    private Timestamp updateTime;
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
    @Column(name = "name", nullable = false)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Basic
    @Column(name = "email", nullable = false)
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Basic
    @Column(name = "blog_url", nullable = true)
    public String getBlogUrl()
    {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl)
    {
        this.blogUrl = blogUrl;
    }

    @Basic
    @Column(name = "remote_ip", nullable = true)
    public String getRemoteIp()
    {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp)
    {
        this.remoteIp = remoteIp;
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentUser that = (CommentUser) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (blogUrl != null ? !blogUrl.equals(that.blogUrl) : that.blogUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (blogUrl != null ? blogUrl.hashCode() : 0);
        result = 31 * result + (remoteIp != null ? remoteIp.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "commentUser")
    public Set<Comment> getComments()
    {
        return comments;
    }

    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
    }
}
