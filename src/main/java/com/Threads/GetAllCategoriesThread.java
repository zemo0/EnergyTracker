package com.Threads;

public class GetAllCategoriesThread extends DatabaseThread implements Runnable{
    @Override
    public void run() {
        super.getAllCategories();
    }
}
