package DAL;

import com.google.firebase.firestore.QuerySnapshot;

public interface OnGetDataListener {
    void onStart();
    void onSuccess(QuerySnapshot data);
    void onFailed(Exception error);
}
