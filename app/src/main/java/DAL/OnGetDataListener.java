package DAL;

import android.support.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;


public interface OnGetDataListener {
    void onStart();
    void onSuccess(QuerySnapshot data, DocumentReference docRef);
    void onFailed(Exception error);
}
