package entities;

public class Lesson {
    private int id;
    private int lessonGroupId;
    private String name;

    public Lesson(int id, int lessonGroupId, String name) {
        this.id = id;
        this.lessonGroupId = lessonGroupId;
        this.name = name;
    }

    public Lesson() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLessonGroupId() {
        return lessonGroupId;
    }

    public void setLessonGroupId(int lessonGroupId) {
        this.lessonGroupId = lessonGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
