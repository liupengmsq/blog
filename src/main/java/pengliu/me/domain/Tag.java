package pengliu.me.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by peng on 16-4-8.
 */
@Entity
@Table(name = "tag", schema = "myblog", catalog = "")
public class Tag
{
    private Integer id;
    private String name;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Set<Blog> blogs = new HashSet<Blog>();

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

        Tag tag = (Tag) o;

        if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
        if (name != null ? !name.equals(tag.name) : tag.name != null) return false;
        if (createTime != null ? !createTime.equals(tag.createTime) : tag.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(tag.updateTime) : tag.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags", cascade = CascadeType.ALL)
    public Set<Blog> getBlogs()
    {
        return blogs;
    }

    public void setBlogs(Set<Blog> blogs)
    {
        this.blogs = blogs;
    }


}
