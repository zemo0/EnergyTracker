package com.FileUtilsThreads;

import com.Serialization.CategorySerialization;

public class SerializeCategoriesThread extends FileUtilsThread implements Runnable{
    private CategorySerialization categorySerialization;
    public SerializeCategoriesThread(CategorySerialization categorySerialization){
        this.categorySerialization = categorySerialization;
    }
    @Override
    public void run(){
        serializeCategories(categorySerialization);
    }
}
