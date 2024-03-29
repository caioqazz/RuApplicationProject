package br.ufop.ruapplicationmvp.model.entity;

public class CategoryDish {
    private int id;
    private String title;
    private int image;

    public CategoryDish(int id, String title, int image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
