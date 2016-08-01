package pengliu.me.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", schema = "myblog", catalog = "")
public class User
{
    private String name;
    private String password;
    private Integer enabled;
    private Set<Blog> blogs = new HashSet<Blog>();

    @Id
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
    @Column(name = "password", nullable = false)
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Basic
    @Column(name = "enabled", nullable = false)
    public Integer getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    public Set<Blog> getBlogs()
    {
        return blogs;
    }

    public void setBlogs(Set<Blog> blogs)
    {
        this.blogs = blogs;
    }
}
