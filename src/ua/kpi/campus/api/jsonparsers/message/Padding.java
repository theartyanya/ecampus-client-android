package ua.kpi.campus.api.jsonparsers.message;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 17.12.13
 * Time: 20:40
 * To change this template use File | Settings | File Templates.
 */
public class Padding {
      private  final int pageCount;
    private  final int totalItemCount;
    private  final int  pageNumber;
    private  final int pageSize;
    private  final boolean hasPreviousPage;
    private  final boolean hasNextPage;
    private  final boolean isFirstPage;
    private  final boolean isLastPage;
    private  final int firstItemOnPage;
    private  final int lastItemOnPage;

    public Padding(int pageCount, int totalItemCount, int pageNumber, int pageSize, boolean hasPreviousPage, boolean hasNextPage, boolean firstPage, boolean lastPage, int firstItemOnPage, int lastItemOnPage) {
        this.pageCount = pageCount;
        this.totalItemCount = totalItemCount;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.hasPreviousPage = hasPreviousPage;
        this.hasNextPage = hasNextPage;
        isFirstPage = firstPage;
        isLastPage = lastPage;
        this.firstItemOnPage = firstItemOnPage;
        this.lastItemOnPage = lastItemOnPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public int getFirstItemOnPage() {
        return firstItemOnPage;
    }

    public int getLastItemOnPage() {
        return lastItemOnPage;
    }
}
