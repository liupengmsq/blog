package pengliu.me.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 *
 */
public class Page<T> implements Serializable {

    private static int DEFAULT_PAGE_SIZE = 20;

    private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

    private long startIndex; // 当前页第一条数据在List中的位置,从0开始

    private List<T> currentPageData; // 当前页中存放的记录,类型一般为List

    private long totalCount; // 总记录数

    /**
     * 构造方法，只构造空页.
     */
    public Page() {
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
    }

    /**
     * 默认构造方法.
     *
     * @param startIndex	 本页数据在数据库中的起始位置
     * @param totalCount 数据库中总记录条数
     * @param pageSize  本页容量
     * @param currentPageData	  本页包含的数据
     */
    public Page(long startIndex, long totalCount, int pageSize, List<T> currentPageData) {
        this.pageSize = pageSize;
        this.setStartIndex(startIndex);
        this.setTotalCount(totalCount);
        this.setCurrentPageData(currentPageData);
    }

    /**
     * 取总记录数.
     */
    public long getTotalCount() {
        return this.totalCount;
    }

    /**
     * 取总页数.
     */
    public long getTotalPageCount() {
        if (getTotalCount() % pageSize == 0)
            return getTotalCount() / pageSize;
        else
            return getTotalCount() / pageSize + 1;
    }

    /**
     * 取每页数据容量.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取当前页中的记录.
     */
    public List<T> getCurrentPageData() {
        return currentPageData;
    }

    /**
     * 取该页当前页码,页码从1开始.
     */
    public long getCurrentPageNo() {
        return getStartIndex() / pageSize + 1;
    }

    /**
     * 该页是否有下一页.
     */
    public boolean isHasNextPage() {
        return this.getCurrentPageNo() < this.getTotalPageCount();
    }

    /**
     * 该页是否有上一页.
     */
    public boolean isHasPreviousPage() {
        return this.getCurrentPageNo() > 1;
    }

    /**
     * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
     *
     * @see #getStartIndexOfPage(int,int)
     */
    protected static int getStartIndexOfPage(int pageNo) {
        return getStartIndexOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任一页第一条数据在数据集的位置.
     *
     * @param pageNo   从1开始的页号
     * @param pageSize 每页记录条数
     * @return 该页第一条数据
     */
    public static int getStartIndexOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }

    public void setCurrentPageData(List<T> currentPageData)
    {
        this.currentPageData = currentPageData;
    }

    public long getStartIndex()
    {
        return startIndex;
    }

    public void setStartIndex(long startIndex)
    {
        this.startIndex = startIndex;
    }

    public void setTotalCount(long totalCount)
    {
        this.totalCount = totalCount;
    }
}
