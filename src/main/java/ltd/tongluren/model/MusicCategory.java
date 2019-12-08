package ltd.tongluren.model;

public class MusicCategory {
    private String id;
    private String name;
    private String imgName;

    public MusicCategory(String id, String name, String imgName) {
        this.id = id;
        this.name = name;
        this.imgName = imgName;
    }

    public MusicCategory() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    @Override
    public String toString() {
        return "MusicCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imgName='" + imgName + '\'' +
                '}';
    }
}
