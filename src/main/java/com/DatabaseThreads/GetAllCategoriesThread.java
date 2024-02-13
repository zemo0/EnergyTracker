package com.DatabaseThreads;

public class GetAllCategoriesThread extends DatabaseThread implements Runnable{
    @Override
    public void run() {
        super.getAllCategories();
    }
}
