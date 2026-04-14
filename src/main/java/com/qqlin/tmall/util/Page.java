package com.qqlin.tmall.util;

/**
 * Pagination helper class for managing paginated database queries.
 *
 * <p>Encapsulates start index and page size for LIMIT/OFFSET queries in MyBatis.
 * Provides utility methods for navigation between pages (has previous, has next)
 * and calculating total pages.</p>
 *
 * <p>Usage example:</p>
 * <pre>
 *   Page page = new Page(0, 20);  // First page, 20 items per page
 *   // In MyBatis mapper: SELECT * FROM product LIMIT #{page.start}, #{page.count}
 * </pre>
 *
 * @author qqlin
 * @date 2020-5-18
 */
public class Page {

    /** Starting index (0-based), corresponds to OFFSET in SQL */
    private int start;

    /** Number of items per page, corresponds to LIMIT in SQL */
    private int count;

    /** Total number of items across all pages */
    private int total;

    /** Additional query parameters for pagination links */
    private String param;

    /** Default number of items per page when not specified */
    private static final int defaultCount = 5;

    /**
     * Gets the starting index for the current page.
     *
     * @return the 0-based start index for LIMIT clause
     */
    public int getStart() {
        return start;
    }

    /**
     * Sets the starting index for pagination.
     *
     * @param start the 0-based start index
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Gets the number of items per page.
     *
     * @return the page size (number of items per page)
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the number of items per page.
     *
     * @param count the page size
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Default constructor. Initializes with default page size (5 items).
     */
    public Page() {
        count = defaultCount;
    }

    /**
     * Constructor with explicit start index and page size.
     *
     * @param start the 0-based start index
     * @param count the number of items per page
     */
    public Page(int start, int count) {
        super();
        this.start = start;
        this.count = count;
    }

    /**
     * Checks if there is a previous page relative to the current start index.
     *
     * @return {@code true} if there is a page before the current one, {@code false} if on first page
     */
    public boolean isHasPreviouse() {
        if (start == 0)
            return false;
        return true;
    }

    /**
     * Checks if there is a next page relative to the current start index.
     *
     * @return {@code true} if there is a page after the current one, {@code false} if on last page
     */
    public boolean isHasNext() {
        if (start == getLast())
            return false;
        return true;
    }

    /**
     * Calculates the total number of pages based on total items and page size.
     *
     * <p>For example, with 50 total items and page size 5: 10 pages.
     * With 51 total items and page size 5: 11 pages.</p>
     *
     * @return the total number of pages, minimum 1 even if no items exist
     */
    public int getTotalPage() {
        int totalPage;
        // 假设总数是50，是能够被5整除的，那么就有10页
        if (0 == total % count) {
            totalPage = total / count;
        }
        // 假设总数是51，不能够被5整除的，那么就有11页
        else {
            totalPage = total / count + 1;
        }

        if (0 == totalPage) {
            totalPage = 1;
        }
        return totalPage;

    }

    /**
     * Calculates the start index of the last page.
     *
     * <p>For example, with 50 total items and page size 5: last page starts at 45.
     * With 51 total items and page size 5: last page starts at 50.</p>
     *
     * @return the start index of the last page, minimum 0
     */
    public int getLast() {
        int last;
        // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
        if (0 == total % count) {
            last = total - count;
        }
        // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
        else {
            last = total - total % count;
        }
        last = last < 0 ? 0 : last;
        return last;
    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPreviouse()=" + isHasPreviouse() + ", isHasNext()="
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }

    /**
     * Gets the total number of items across all pages.
     *
     * @return the total count of items
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the total number of items across all pages.
     *
     * @param total the total count of items
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Gets the additional query parameters for pagination links.
     *
     * @return the query parameters string
     */
    public String getParam() {
        return param;
    }

    /**
     * Sets the additional query parameters for pagination links.
     *
     * @param param the query parameters string
     */
    public void setParam(String param) {
        this.param = param;
    }

}
