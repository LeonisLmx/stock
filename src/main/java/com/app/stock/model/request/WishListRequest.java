package com.app.stock.model.request;

/**
 * Created by lmx
 * Date 2019/2/24
 */
public class WishListRequest extends VerifyRequest{

    private String wishMonth;
    private String wish;
    private String wishYear;
    private Integer isSelf;
    private Integer page;
    private Integer page_size;

    public String getWishMonth() {
        return wishMonth;
    }

    public void setWishMonth(String wishMonth) {
        this.wishMonth = wishMonth;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public String getWishYear() {
        return wishYear;
    }

    public void setWishYear(String wishYear) {
        this.wishYear = wishYear;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public Integer getPage() {
        return page == null?1:page == 0?1:page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPage_size() {
        return page_size == null?20:page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }
}
