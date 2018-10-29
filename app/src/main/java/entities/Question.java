package entities;

import java.io.Serializable;

public class Question implements Serializable {
    private int id;
    private int lessonId;
    private String questionType;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Question(int id, int lessonId, String questionType, String content) {

        this.id = id;
        this.lessonId = lessonId;
        this.questionType = questionType;
        this.content = content;
    }

    public Question() {

    }
}
