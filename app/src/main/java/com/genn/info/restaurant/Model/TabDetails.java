package com.genn.info.restaurant.Model;

import com.genn.info.restaurant.Fragment.Product_fragment;

public class TabDetails {
    private String tabName;
    private Product_fragment.PlaceholderFragment fragment;

    public TabDetails(String tabName, Product_fragment.PlaceholderFragment fragment) {
        this.tabName = tabName;
        this.fragment = fragment;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public Product_fragment.PlaceholderFragment getFragment() {
        return fragment;
    }

    public void setFragment(Product_fragment.PlaceholderFragment fragment) {
        this.fragment = fragment;
    }

}
