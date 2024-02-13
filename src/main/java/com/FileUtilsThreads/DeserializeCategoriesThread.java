package com.FileUtilsThreads;

public class DeserializeCategoriesThread extends FileUtilsThread implements Runnable{
    @Override
    public void run(){
        super.deserializeCategories();
    }
}
