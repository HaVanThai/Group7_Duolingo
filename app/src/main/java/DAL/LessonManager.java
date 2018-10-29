package DAL;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import entities.Lesson;
import entities.Question;

public class LessonManager {

    private FirebaseFirestore db;

    public LessonManager(){
        db = FirebaseFirestore.getInstance();
    }

    public void getAllLessons(final OnGetDataListener listener) {
        listener.onStart();
        final ArrayList<Lesson> lessons = new ArrayList<>();
        db.collection("lessons")
                .orderBy("id")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess(task.getResult());

                        } else {
                            listener.onFailed(task.getException());
                        }
                    }
                });
    }

    public void getAllQuestions(final int lessonId, final OnGetDataListener listener) {
        listener.onStart();
        final ArrayList<Question> questions = new ArrayList<>();
        db.collection("questions")
                .whereEqualTo("lesson_id", lessonId)
//                .orderBy("id")    // Doesn't work with whereEqualTo
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess(task.getResult());

                        } else {
                            listener.onFailed(task.getException());
                        }
                    }
                });
    }
}
