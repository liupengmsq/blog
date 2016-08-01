package pengliu.me.domain;

import javax.persistence.*;

/**
 * Created by peng on 16-7-5.
 */
@Entity
@Table(name = "post_recommendation", schema = "myblog", catalog = "")
public class PostRecommendation
{
    private Integer id;
    private String url;
    private String displayName;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url", nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "display_name", nullable = false)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
