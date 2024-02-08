package com.Threads;

import com.models.Category;
import com.utils.DatabaseUtils;

public class DeleteCategoryThread extends DatabaseThread implements Runnable{
    private final Category category;
    public DeleteCategoryThread(Category category){
        this.category = category;
    }
    public void run() {
        deleteCategory(category);
    }
}
