package com.models;

public class Category {
    private Long id;
    private String name;
    private String description;

    public Category(CategoryBuilder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
    }

    public static class CategoryBuilder{
        private Long id;
        private String name;
        private String description;
        public CategoryBuilder id(Long _id){
            this.id = _id;
            return this;
        }
        public CategoryBuilder name(String _name){
            this.name = _name;
            return this;
        }
        public CategoryBuilder description(String _description){
            this.description = _description;
            return this;
        }
        public Category build(){return new Category(this);}
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
