package janche.ripaxiety;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.database.*;


/*
*    ChecklistActivity is an activity class that displays the
*    personal checklist activity
*
*    created by: Patrick Abbey 10/17/16
*    modified: Patrick Abbey 10/18/16
*    modified: Patrick Abbey 10/20/16
*    modified: Patrick Abbey 10/21/16
*    modified: Patrick Abbey 10/22/16
*    modified: Patrick Abbey 10/26/16
*    modified: Patrick Abbey 10/27/16
*    modified: Patrick Abbey 12/1/16
*    modified: Patrick Abbey 12/5/16
*    modified: Patrick Abbey 12/6/16
*    modified: Patrick Abbey 12/7/16
*/
public class ChecklistActivity extends AppCompatActivity
{

    /*
    *    sets the display to show activity_checklist.xml
    *    also sets up the checklists from the database data
    *
    *    created by: Patrick Abbey 10/17/16
    */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        DatabaseHelper db = new DatabaseHelper(ChecklistActivity.this);
        Cursor cur = db.getAllChecklistData();

        // Iterates through the rows of the checklist table
        // Basically, I retrieve the relevant info from the table
        //  and use then call the function used for adding an item
        //  as if the "Add Goal" button was pressed, but with values
        //  that indicate that the goal was loaded and to treat it as such
        if (cur.getCount() > 0)
        {
            while (cur.moveToNext())
            {
                // Testing for all column values in the row
                //  "Scaffolding"

                //AlertDialog.Builder dialog = new AlertDialog.Builder(ChecklistActivity.this);
                //dialog.setMessage(cur.getString(0) + cur.getString(1) + cur.getString(2)
                //                  + cur.getString(3) + cur.getString(4) + cur.getString(5));
                //dialog.show();


                // Initialize array and retrieve day value from the current goal row
                String act_array[];
                int day = cur.getInt(cur.getColumnIndex("GOAL_PROGRESS"));

                // If a goal has been checked and it's a new day, update the day value
                //  and the current goal row's date column to use in the next check
                //  for a new day
                if ((cur.getString(cur.getColumnIndex("COMPLETE")).equals("YES")) &&
                    !(cur.getString(cur.getColumnIndex("GOAL_DATE")).equals(db.getDateTime())))
                {
                    day++;
                    db.updateGoalProgress(cur, day);
                    db.updateGoalDate(cur);
                }

                // Get the respective array of activity strings from the strings.xlm resource
                switch (cur.getString(cur.getColumnIndex("GOAL_SENTENCE")))
                {
                    case "talk":
                        act_array = getResources().getStringArray(R.array.talkTo);
                        break;
                    case "hang":
                        act_array = getResources().getStringArray(R.array.hangOut);
                        break;
                    case "call":
                        act_array = getResources().getStringArray(R.array.phoneCall);
                        break;
                    default:
                        act_array = new String[1];
                }

                // Call the addGoal to let rest of the program handle (re-)adding the checklist
                //  item to the list appropriately
                addGoal(act_array, day, cur);
            }
        }
    }

    /*
    *    expects a planned number of days the goal will take, an intensity
    *    of the challenge slope, an index representing one of the checkboxes,
    *    and an array of strings filled with individual goals for certain days,
    *    and sets a goal next to a checkbox for the user to check once they
    *    complete that goal
    *    returns nothing
    *
    *    created by: Patrick Abbey 10/18/16
    */
    public void makeCheckBox(int plan, int intensity, int cbox_index, String[] activity, int day, Cursor cursor_arg)
    {
        final Cursor cursor = cursor_arg;
        String getText[] = activity;
        CheckBox checkText = null;
        switch (cbox_index)
        {
            case 0: checkText = (CheckBox)findViewById(R.id.checkBox); break;
            case 1: checkText = (CheckBox)findViewById(R.id.checkBox2); break;
            case 2: checkText = (CheckBox)findViewById(R.id.checkBox3); break;
            case 3: checkText = (CheckBox)findViewById(R.id.checkBox4); break;
        }

        // Code that appears to break the program, will fix another time, leaving this
        //  for the live demo

/*       checkText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                DatabaseHelper db = new DatabaseHelper(ChecklistActivity.this);
                Cursor cur = cursor;

                if (cur != null)
                {
                    if (isChecked)
                    {
                        db.updateGoalChecked(cur, true);
                    }
                    else
                    {
                        db.updateGoalChecked(cur, false);
                    }
                }
            }
        });
*/
        checkText.setVisibility(View.VISIBLE);
        int indexToGet = (int)(Math.floor((day-1)/(6-intensity)));
        if (indexToGet < 0)
        {
            indexToGet = 0;
        }
        if (day <= plan)
        {
            checkText.setText(getText[indexToGet]);
        }
        else
        {
            checkText.setVisibility(View.INVISIBLE);
        }

    }

    /*
    *    expects an index representing one of the checkboxes and a string
    *    describing a custom daily goal set by the user in the addOwnGoal
    *    method, and sets a goal next to a checkbox for the user to check
    *    once they complete that goal
    *    returns nothing
    *
    *    created by: Patrick Abbey 10/20/16
    */
    public void makeCustomCheckbox(int cbox_index, String goal)
    {
        CheckBox checkText = null;
        switch (cbox_index)
        {
            case 0: checkText = (CheckBox)findViewById(R.id.checkBox); break;
            case 1: checkText = (CheckBox)findViewById(R.id.checkBox2); break;
            case 2: checkText = (CheckBox)findViewById(R.id.checkBox3); break;
            case 3: checkText = (CheckBox)findViewById(R.id.checkBox4); break;
        }
        checkText.setVisibility(View.VISIBLE);
        checkText.setText(goal);
    }

    /*
    *    expects a view object and creates a popup that prompts the user to select
    *    what they'd like to work on, which will then be used to determine an
    *    appropriate goal for them, or they may choose to add their own goal
    *    returns nothing
    *
    *    created by: Patrick Abbey 10/20/16
    */
    public void selectGoal(final View v)
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(ChecklistActivity.this);
        dialog.setTitle("What would you like to work on?");
        dialog.setItems(R.array.possibleActivities, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dlg, int which)
                    {
                        DatabaseHelper db = new DatabaseHelper(ChecklistActivity.this);

                        switch (which)
                        {
                            case 0: addGoal(getResources().getStringArray(R.array.talkTo), 1, null);
                                    db.insertNewGoalData("talk");
                                    break;
                            case 1: addGoal(getResources().getStringArray(R.array.hangOut), 1, null);
                                    db.insertNewGoalData("hang");
                                    break;
                            case 2: addGoal(getResources().getStringArray(R.array.phoneCall), 1, null);
                                    db.insertNewGoalData("call");
                                    break;
                            case 3: addOwnGoal(v);
                        }
                    }
                }
        );
        dialog.show();
    }

    /*
    *    expects a view object and a array of strings
    *    representing the goals selected based on what
    *    the user desires to work on, and sends array
    *    of strings to the makeCheckbox method
    *    returns nothing
    *
    *    created by: Patrick Abbey 10/18/16
    */
    public void addGoal(String[] activity, int day, Cursor cur)
    {
        DatabaseHelper db = new DatabaseHelper(ChecklistActivity.this);

        int intensity;
        intensity = db.getIntensity();
        int plan = 0;

        switch (intensity)
        {
            case 1: plan = 25; break;
            case 2: plan = 20; break;
            case 3: plan = 15; break;
            case 4: plan = 10; break;
            case 5: plan = 5; break;
        }

        if (findViewById(R.id.checkBox).getVisibility() == View.INVISIBLE)
        {
            makeCheckBox(plan, intensity, 0, activity, day, cur);
        }
        else if (findViewById(R.id.checkBox2).getVisibility() == View.INVISIBLE)
        {
            makeCheckBox(plan, intensity, 1, activity, day, cur);
        }
        else if (findViewById(R.id.checkBox3).getVisibility() == View.INVISIBLE)
        {
            makeCheckBox(plan, intensity, 2, activity, day, cur);
        }
        else if (findViewById(R.id.checkBox4).getVisibility() == View.INVISIBLE)
        {
            makeCheckBox(plan, intensity, 3, activity, day, cur);
        }
    }

    /*
    *    expects a view object and creates a dialog
    *    prompting the user to input the custom goal
    *    for the day, and sends that message to the
    *    makeCustomCheckbox method
    *    returns nothing
    *
    *    created by: Patrick Abbey 10/20/16
    */
    public void addOwnGoal(View v)
    {
        final EditText input = new EditText(this);

        AlertDialog.Builder custom_goal = new AlertDialog.Builder(ChecklistActivity.this);
        custom_goal.setTitle("Enter in a daily goal: ");
        custom_goal.setView(input);
        custom_goal.setNeutralButton("Add", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    String goal = input.getText().toString();
                    if (findViewById(R.id.checkBox).getVisibility() == View.INVISIBLE)
                    {
                        makeCustomCheckbox(0, goal);
                    }
                    else if (findViewById(R.id.checkBox2).getVisibility() == View.INVISIBLE)
                    {
                        makeCustomCheckbox(1, goal);
                    }
                    else if (findViewById(R.id.checkBox3).getVisibility() == View.INVISIBLE)
                    {
                        makeCustomCheckbox(2, goal);
                    }
                    else if (findViewById(R.id.checkBox4).getVisibility() == View.INVISIBLE)
                    {
                        makeCustomCheckbox(3, goal);
                    }
                }

            }
        );
        custom_goal.show();
    }

}
