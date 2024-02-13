package com.DatabaseThreads;

import com.models.Category;

public class DeleteCategoryThread extends DatabaseThread implements Runnable{
    private final Category category;
    public DeleteCategoryThread(Category category){
        this.category = category;
    }
    public void run() {
        deleteCategory(category);
    }
}
