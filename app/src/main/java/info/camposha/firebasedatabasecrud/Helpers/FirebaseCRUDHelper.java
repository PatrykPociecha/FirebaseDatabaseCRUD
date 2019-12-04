package info.camposha.firebasedatabasecrud.Helpers;

import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import info.camposha.firebasedatabasecrud.Data.MyAdapter;
import info.camposha.firebasedatabasecrud.Data.Scientist;
import info.camposha.firebasedatabasecrud.Views.ScientistsActivity;

import static info.camposha.firebasedatabasecrud.Helpers.Utils.DataCache;

public class FirebaseCRUDHelper {

    public void insert(final AppCompatActivity a,
                          final DatabaseReference mDatabaseRef,
                          final ProgressBar pb, final Scientist scientist) {
        //check if they have passed us a valid scientist. If so then return false.
        if (scientist == null) {
            Utils.showInfoDialog(a,"walidacja nie powidola sie","zawodnik jest pusty");
            return;
        } else {
            //otherwise try to push data to firebase database.
                Utils.showProgressBar(pb);
                //push data to FirebaseDatabase. Table or Child called Scientist will be
                // created.
                mDatabaseRef.child("Zawodnik").push().setValue(scientist).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Utils.hideProgressBar(pb);

                        if(task.isSuccessful()){
                            Utils.openActivity(a, ScientistsActivity.class);
                            Utils.show(a,"Gratulacje! doałes zawodnika");
                        }else{
                            Utils.showInfoDialog(a,"niepowodzenie",task.getException().
                            getMessage());
                        }
                    }

                });
        }
    }

    public void select(final AppCompatActivity a, DatabaseReference db,
                                         final ProgressBar pb,
                                         final RecyclerView rv,MyAdapter adapter) {
        Utils.showProgressBar(pb);

        db.child("Zawodnik").addValueEventListener(new ValueEventListener() {
        @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            DataCache.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Now get Scientist Objects and populate our arraylist.
                        Scientist scientist = ds.getValue(Scientist.class);
                        scientist.setKey(ds.getKey());
                        DataCache.add(scientist);
                    }

                    adapter.notifyDataSetChanged();

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Utils.hideProgressBar(pb);
                            rv.smoothScrollToPosition(DataCache.size());
                        }
                    });
                }else {
                    Utils.show(a,"No more item found");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("SPORTSAPP", databaseError.getMessage());
                Utils.hideProgressBar(pb);
                Utils.showInfoDialog(a,"ANULOWANO",databaseError.getMessage());
            }
        });
    }

    public void update(final AppCompatActivity a,
                       final DatabaseReference mDatabaseRef,
                       final ProgressBar pb,
                       final Scientist oldScientist,
                       final Scientist newScientist) {

			if(oldScientist == null){
				Utils.showInfoDialog(a,"WALIDACJA NIE POWIODLA SIE","zawonik jest pusty");
				return;
			}

        Utils.showProgressBar(pb);
            mDatabaseRef.child("ZawodniK").child(oldScientist.getKey()).setValue(
                newScientist)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Utils.hideProgressBar(pb);

                            if(task.isSuccessful()){
                                Utils.show(a, oldScientist.getName() + " Update powiodl sie");
                                Utils.openActivity(a, ScientistsActivity.class);
                            }else {
                                Utils.showInfoDialog(a,"NIEPOWODZENIE",task.getException().
                                getMessage());
                            }
                        }
                    });
    }


    public void delete(final AppCompatActivity a, final DatabaseReference mDatabaseRef,
                       final ProgressBar pb, final Scientist selectedScientist) {
        Utils.showProgressBar(pb);
        final String selectedScientistKey = selectedScientist.getKey();
        mDatabaseRef.child("ZawodniK").child(selectedScientistKey).removeValue().
        addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Utils.hideProgressBar(pb);

                if(task.isSuccessful()){
                    Utils.show(a, selectedScientist.getName() + " usunieto");
                    Utils.openActivity(a, ScientistsActivity.class);
                }else{
                    Utils.showInfoDialog(a,"NIEPOWODZENIE",task.getException().getMessage());
                }
            }
        });

    }
}
//end

