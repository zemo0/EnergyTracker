package com.Threads;

import com.models.Appliance;
import com.models.Category;

public class UpdateCategoryThread extends DatabaseThread implements Runnable{
    private final Category category;
    private final Long id;
    public UpdateCategoryThread(Category category, Long id){
        this.category = category;
        this.id = id;
    }
    @Override
    public void run() {
        super.updateCategory(category, id);
    }
}
