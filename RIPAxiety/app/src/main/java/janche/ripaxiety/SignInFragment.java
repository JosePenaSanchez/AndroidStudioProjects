package janche.ripaxiety;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

/**
 * Created by josepena on 11/15/16.
 * Modified by josepena on 12/5/2016
 * Modified by: Patrick Abbey 12/6/16
 * Modified by: Jose Pena 12/7/16
 */
public class SignInFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        //Initializing instances required for program
        View view = inflater.inflate(R.layout.sign_in_fragment, container, false);
        Button signInButton = (Button) view.findViewById(R.id.sign_in_submit_login);
        final TextView username = (TextView) view.findViewById(R.id.username_input);
        final TextView password = (TextView) view.findViewById(R.id.password_input);


        //Setting Listener
        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //obtaining user's input on log-in information
                String usernameInput = username.getText().toString();
                String passwordInput = password.getText().toString();
                DatabaseHelper db = new DatabaseHelper(getActivity());


                switch (db.validateLogIn(usernameInput,passwordInput))
                {
                    case 0:
                        showMessage("Error","Incorrect username or password.");
                        break;

                    case 1:
                        Intent intent = new Intent(getActivity(),HomepageActivity.class);
                        startActivity(intent);
                        break;

                    case 2:
                        showMessage("Error", "Multiple records with same information exsists");
                        break;

                    default:
                        showMessage("Super Error", "A huge error occured. None of the cases are true");
                }

                Toast.makeText(getActivity(),"SIGN IN Button clicked",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}









//The bottom was commented as a back up just incase my merging messes up
//if the top works, the bottom can be deleted
/*
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;


 * Created by: Jose Pena 11/15/16
 * Modified by: Patrick Abbey 12/6/16
 

public class SignInFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.sign_in_fragment, container, false);
        Button button = (Button) view.findViewById(R.id.sign_in_submit_login);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Toast.makeText(getActivity(),"SIGN IN Button clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignInFragment.super.getActivity(), HomepageActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
*/
