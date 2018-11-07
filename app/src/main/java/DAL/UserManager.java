package DAL;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import entities.User;

public class UserManager {
    private FirebaseFirestore db;

    public UserManager() {
        db = FirebaseFirestore.getInstance();
    }

    public void addNewUser(User user, final OnGetDataListener listener) {
        listener.onStart();
        db.collection("users")
                .add(user)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess(null, task.getResult());
                        } else {
                            listener.onFailed(task.getException());
                        }
                    }
                });
    }

    public void getUser(String email, final OnGetDataListener listener) {
        listener.onStart();
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess(task.getResult(), null);

                        } else {
                            listener.onFailed(task.getException());
                        }
                    }
                });
    }

    public void updateProgress(String progress, String documentId) {
//        listener.onStart();
        db.collection("users")
                .document(documentId)
                .update("progress", progress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
//                            listener.onSuccess(null, null);
                        } else {
//                            listener.onFailed(null);
                        }
                    }
                });
    }
}
