package entities;

public class Lesson {
    private int id;
    private String lessonGroup;
    private String name;

    public Lesson(int id, String lessonGroup, String name) {
        this.id = id;
        this.lessonGroup = lessonGroup;
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

    public String getLessonGroupId() {
        return lessonGroup;
    }

    public void setLessonGroupId(String lessonGroup) {
        this.lessonGroup = lessonGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
