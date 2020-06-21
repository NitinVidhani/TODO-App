package application.example.mytodo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import application.example.mytodo.database.DBHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    TextInputLayout textInputUsername, textInputPassword;
    String username, password;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        textInputUsername = view.findViewById(R.id.text_input_username);
        textInputPassword = view.findViewById(R.id.text_input_password);
        Button button = view.findViewById(R.id.button_login);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = textInputUsername.getEditText().getText().toString();
                password = textInputPassword.getEditText().getText().toString();
                loginUser(username, password);
            }
        });

        return view;
    }

    private void loginUser(String username, String password) {
        validateUser(username, password);
    }

    private void validateUser(String username, String password) {
        DBHelper helper = new DBHelper(getContext());
        Log.e("username", "validateUser: "+username +" password: "+ password);
        if (helper.userExists(username, password)) {
            startActivity(new Intent(getActivity(), TodoListActivity.class)
                    .putExtra("username", username));
        } else {
            Toast.makeText(getActivity(), "User does not exist", Toast.LENGTH_SHORT).show();
        }

    }
}
