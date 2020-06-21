package application.example.mytodo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import application.example.mytodo.database.DBHelper;
import application.example.mytodo.model.UserModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    TextInputLayout textInputUsername;
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        textInputUsername = view.findViewById(R.id.text_input_username);
        textInputEmail = view.findViewById(R.id.text_input_email);
        textInputPassword = view.findViewById(R.id.text_input_password);
        Button button = view.findViewById(R.id.confirm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textInputUsername.getEditText().getText().toString().trim();
                String email = textInputEmail.getEditText().getText().toString().trim();
                String password = textInputPassword.getEditText().getText().toString().trim();

                registerUser(username, email, password);
            }
        });

        return view;
    }

    private void registerUser(String username, String email, String password) {

        if(validateFields(username, email, password)) {

            DBHelper helper = new DBHelper(getActivity());
            if (helper.insertUser(new UserModel(username, email, password))) {
                startActivity(new Intent(getActivity(), TodoListActivity.class)
                        .putExtra("username", username));
            }

        }

    }

    private boolean validateFields(String username, String email, String password) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            if (username.isEmpty()) {
                textInputUsername.setErrorEnabled(true);
                textInputUsername.setError("This field can't be empty");
            }
            if (email.isEmpty()) {
                textInputEmail.setErrorEnabled(true);
                textInputEmail.setError("This field can't be empty");
            }
            if (password.isEmpty()) {
                textInputPassword.setErrorEnabled(true);
                textInputPassword.setError("This field can't be empty");
            }
            return false;
        } else if (password.length()<6){
            textInputPassword.setErrorEnabled(true);
            textInputPassword.setError("Password Too Short");
            return false;
        } else{
            return true;
        }
    }

}
