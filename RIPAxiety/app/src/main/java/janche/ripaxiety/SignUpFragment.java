package janche.ripaxiety;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by josepena on 11/15/16.
 * Modified by josepena on 12/5/2016.
 * Modified by: Patrick Abbey 12/6/16.
 * Modified by: Jose Pena 12/7/16
 */




public class SignUpFragment extends Fragment
{

    //This part is the alert box (aka dialog box). This is where the contents of the database will be displayed. For testing purposes.
    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Initializing Instances of methods
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
        Button signUpButton = (Button) view.findViewById(R.id.sign_up_submit_login);
        Button signUpTestButton = (Button) view.findViewById(R.id.testbutton);

        final TextView username = (TextView) view.findViewById(R.id.sign_up_username_input);
        final TextView password = (TextView) view.findViewById(R.id.sign_up_password_input);


        //Setting ONCLICK listener for singUpButton
        signUpButton.setOnClickListener(new View.OnClickListener()
        {

            //Onclick action
            @Override
            public void onClick(View view)
            {

                //DatabaseHelper Initializer to
                DatabaseHelper database = new DatabaseHelper(getActivity());
                String userInputUserName = username.getText().toString();
                String confirmationResult = database.checkUser(userInputUserName);

                if(confirmationResult == "Okay to add")
                {
                    showMessage("Result", confirmationResult);

                    Boolean result = database.insertNewUsersData(userInputUserName, password.getText().toString());

                    if(result)
                        Toast.makeText(getActivity(),"Data Inserted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getActivity(),"Data Not Inserted",Toast.LENGTH_LONG).show();

                    //I commented this part out to prevent the automatic Activity change. To give us a chance to press the
                    //Test button
                    //Intent intent = new Intent(getActivity(),HomepageActivity.class);
                    //startActivity(intent);
                }


                else if(confirmationResult == "Username already exists, Not okay to add")
                {
                    showMessage("Choose different username", confirmationResult);

                }

                else if(confirmationResult == "there is a duplicate users with the same username in database")
                {
                    showMessage("Error", confirmationResult);
                }


                else
                {
                    showMessage("Super Error","none of the conditions where meet. something else is going on.");
                }

            }
        });




        //Setting ONCLICK listener for signUpTestButton...for Testing purposes
        signUpTestButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatabaseHelper database = new DatabaseHelper(getActivity());
                Cursor result = database.getAllData();

                //result = 0 when no data was found
                if (result.getCount() == 0)
                    showMessage("Error","No data is found");


                else
                {

                    StringBuffer buffer = new StringBuffer();
                    while(result.moveToNext())
                    {
                        buffer.append("ID :" + result.getString(0) +"\n");
                        buffer.append("UserName :" + result.getString(1) +"\n");
                        buffer.append("Password :" + result.getString(2 ) +"\n");
                    }
                    showMessage("Success?", buffer.toString());
                }
            }
        });

        return view;
    }
}


//Bottom is old version for merging purposes incase of falure, don't delete yet!
/*
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



 * Created by: Jose Pena 11/15/16
 * Modified by: Patrick Abbey 12/6/16
 


//This code is not a regular activity.. It is a fragment. So do not treat it like an activity if you will modify it.
//I suggest you don't spend time modifying it though, because it works, and I'm still using it, so I'll just override
//your modifications in the near future (if you do anything.)

public class SignUpFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);

        final TextView username = (TextView) view.findViewById(R.id.sign_up_username_input);
        final TextView password = (TextView) view.findViewById(R.id.sign_up_password_input);

        Button button = (Button) view.findViewById(R.id.sign_up_submit_login);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatabaseHelper signUp = new DatabaseHelper(getActivity());
                Boolean result = signUp.insertNewUsersData(username.getText().toString(), password.getText().toString());
                if(result)
                    Toast.makeText(getActivity(),"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(),"Data Not Inserted",Toast.LENGTH_LONG).show();


            }
        });



        //This part of the code is just to test database. it will SELECT * FROM USER's table DATABASE
        Button button1 = (Button) view.findViewById(R.id.testbutton);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //if Cursor's value is 0, then there is not data.
                DatabaseHelper db = new DatabaseHelper(getActivity());
                Cursor res = db.getallData();
                if (res.getCount() == 0)
                    showMessage("Error","No data is found");

                    //If Curso's value is not 0, then it has data, and we must convert that cursor's value onto a string.
                    //we do that by making a new String buffer instance, and append a table's data onto it, usng getString(int).
                    //The int is the column number.
                    //moveToNext() causes the Cursor instance (which i've stored in res) to point to the next row in the table.
                else
                {

                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext())
                    {
                        buffer.append("ID :" + res.getString(0) +"\n");
                        buffer.append("UserName :" + res.getString(1) +"\n");
                        buffer.append("Password :" + res.getString(2 ) +"\n");
                    }
                    showMessage("Success?", buffer.toString());
                }
            }
        });
        return view;
    }


    //This part is the alert box (aka dialog box). This is where the contents of the database will be displayed. For testing purposes.
    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}

*/
