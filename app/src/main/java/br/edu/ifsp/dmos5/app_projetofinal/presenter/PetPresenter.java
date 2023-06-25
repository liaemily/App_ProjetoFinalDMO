package br.edu.ifsp.dmos5.app_projetofinal.presenter;

import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import br.edu.ifsp.dmos5.app_projetofinal.constant.Constants;
import br.edu.ifsp.dmos5.app_projetofinal.model.Pet;
import br.edu.ifsp.dmos5.app_projetofinal.model.User;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.PetMVP;

public class PetPresenter implements PetMVP.Presenter{

    private PetMVP.View view;
    private FirebaseFirestore database;
    private String userID = "";
    private Pet pet = null;

    public PetPresenter(PetMVP.View view) {
        this.view = view;
        database = FirebaseFirestore.getInstance();
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void startListener() {

    }

    @Override
    public void stopListener() {

    }

    @Override
    public void savePet(String name, String animal, String age, String peso, String race, String color) {
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        pet = new Pet(name, animal, age, peso, race, color);

        CollectionReference petsRef = database.collection(Constants.PETS_COLLECTION);

        petsRef.document(userID)
                .set(pet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(view.getContext(), "Perfil do pet atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Erro ao atualizar perfil do pet.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void populate(EditText name, EditText animal, EditText age, EditText peso, EditText race, EditText color) {
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = database.collection(Constants.PETS_COLLECTION).document(userID);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null){
                    pet = value.toObject(Pet.class);
                    if(pet != null) {
                        name.setText(pet.getNome());
                        animal.setText(pet.getAnimal());
                        age.setText(pet.getIdade());
                        peso.setText(pet.getPeso());
                        race.setText(pet.getRaca());
                        color.setText(pet.getCor());
                    }
                }
            }
        });
    }
}
