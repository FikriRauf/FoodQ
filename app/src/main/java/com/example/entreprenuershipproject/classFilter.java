package com.example.entreprenuershipproject;

import androidx.lifecycle.ViewModel;

public class classFilter extends ViewModel {
    String
            filterOption1,
            filterOption2,
            filterOption3,
            filterOption4;

    public classFilter() {
    }

    public classFilter(String filterOption1, String filterOption2, String filterOption3, String filterOption4) {
        this.filterOption1 = filterOption1;
        this.filterOption2 = filterOption2;
        this.filterOption3 = filterOption3;
        this.filterOption4 = filterOption4;
    }

    public String getFilterOption1() {
        return filterOption1;
    }

    public void setFilterOption1(String filterOption1) {
        this.filterOption1 = filterOption1;
    }

    public String getFilterOption2() {
        return filterOption2;
    }

    public void setFilterOption2(String filterOption2) {
        this.filterOption2 = filterOption2;
    }

    public String getFilterOption3() {
        return filterOption3;
    }

    public void setFilterOption3(String filterOption3) {
        this.filterOption3 = filterOption3;
    }

    public String getFilterOption4() {
        return filterOption4;
    }

    public void setFilterOption4(String filterOption4) {
        this.filterOption4 = filterOption4;
    }
}
