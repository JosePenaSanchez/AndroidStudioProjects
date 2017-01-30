package janche.ripaxiety;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Element;

/**
 * AssessmentActivity: handles assessment activity
 *
 * Created by Liliana on 11/2/2016.
 *  Currently changes the text for the question and sets starting progress to zero...
 *  Next thing to work on: filling out the options
 *      for the buttons and controlling the actions
 *      and increment progress bar once an option has been selected
 */

public class AssessmentActivity extends AppCompatActivity
{
    int score=0, progress=0, max, counter=0;
    TextView assessmentText;
    ProgressBar progressBar;
    TextView assessmentHeader;
    Button button1, button2, button3, button4, button5;
    String getQuestionText[];
    String getHeaderText[];
    String getHeaderCategory[];
    String getFearChoiceText[];
    String getAvoidanceChoiceText[];
    String getAssesmentQuestion[];
    String getScoringScale[];
    String getAssessmentGuide[];
    String scoreRange;
    String[] headerText = new String[1];
        /*
         *  Set the display to assessment.xml
         */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment);


        assessmentText = (TextView)findViewById(R.id.Assesment_Question);
        progressBar = (ProgressBar)findViewById((R.id.Progress_Bar));
        assessmentHeader = (TextView)findViewById(R.id.Subject_Header);
        button1 = (Button) findViewById(R.id.buttonChoice1);
        button2 = (Button) findViewById(R.id.buttonChoice2);
        button3 = (Button) findViewById(R.id.buttonChoice3);
        button4 = (Button) findViewById(R.id.buttonChoice4);
        button5 = (Button) findViewById(R.id.moveOnBtn);
        getQuestionText = getResources().getStringArray(R.array.assessmentQuestions);
        getHeaderText = getResources().getStringArray(R.array.assessmentHeader);
        getHeaderCategory = getResources().getStringArray(R.array.assessmentCategories);
        getFearChoiceText = getResources().getStringArray(R.array.assessmentFear);
        getAvoidanceChoiceText = getResources().getStringArray(R.array.assessmentAvoidance);
        getAssesmentQuestion = getResources().getStringArray(R.array.assessmentQuestions);
        getScoringScale = getResources().getStringArray(R.array.scoringScale);
        getAssessmentGuide = getResources().getStringArray(R.array.assessmentGuide);

        setAssessmentText();

        assessmentGuide();



        setAssessmentButtons();

    }

    /*
     *  Set text for the assessment question
     *  Once an option is selected, increment progress bar
     *
     */
    public void setAssessmentText()
    {



        //even or odd number determines category option
        int parity = progress%2;
        headerText[0] = getHeaderText[0]+" "+getHeaderCategory[parity];

        int questionNum = (int) Math.floor((progress)/2);

        assessmentHeader.setText(headerText[0]);
        if (questionNum < getAssesmentQuestion.length) {
            assessmentText.setText(getAssesmentQuestion[questionNum]);
        }
        else
        {
            finishAssessment();
        }

        max = (getAssesmentQuestion.length+1) * 2;

        if(parity == 0 && progress < max)
        {
            button1.setText(getFearChoiceText[0]);
            button2.setText(getFearChoiceText[1]);
            button3.setText(getFearChoiceText[2]);
            button4.setText(getFearChoiceText[3]);
        }
        else if(parity == 1 && progress < max)
        {
            button1.setText(getAvoidanceChoiceText[0]);
            button2.setText(getAvoidanceChoiceText[1]);
            button3.setText(getAvoidanceChoiceText[2]);
            button4.setText(getAvoidanceChoiceText[3]);
        }

        progress++;
        progressBar.setProgress(progress);
    }

    public void assessmentGuide()
    {
        final AlertDialog.Builder guide = new AlertDialog.Builder(AssessmentActivity.this);
        guide.setMessage(getAssessmentGuide[0]).setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                counter++;
                if (counter == 1)
                {
                    guide.setMessage(getAssessmentGuide[1]);
                    dialog.dismiss();
                }
                else if(counter == 2)
                {
                    guide.setMessage(getAssessmentGuide[2]);
                    dialog.dismiss();
                }
                else if(counter == 3)
                {
                    guide.setMessage(getAssessmentGuide[3]);
                    dialog.dismiss();
                }
                else if(counter==4)
                {
                    guide.setMessage(getAssessmentGuide[4]);
                    guide.setCancelable(true);
                    dialog.dismiss();
                }
            }
        });
        guide.show();
    }

    public void finishAssessment()
    {
        //Get range for the score
        if(score<55)
        {
            scoreRange = getScoringScale[0];
        }
        else if(score>=55 && score <=65)
        {
            scoreRange = getScoringScale[1];
        }
        else if (score>65 && score<=80)
        {
            scoreRange = getScoringScale[2];
        }
        else if (score>80 && score<=95)
        {
            scoreRange = getScoringScale[3];
        }
        else
        {
            scoreRange = getScoringScale[4];
        }

        //Make a dialogue box with the numerical score and description of range
        AlertDialog.Builder showScore = new AlertDialog.Builder(AssessmentActivity.this);
        showScore.setTitle("Your score was: "+ score);
        showScore.setMessage("Your score is in the range"+ " " + scoreRange);

        //Set buttons for quiz to unclickable
        //show got it button
        button1.setClickable(false);
        button2.setClickable(false);
        button3.setClickable(false);
        button4.setClickable(false);
        showScore.show();
        button5.setText("Got it");
        button5.setClickable(true);
        button5.setVisibility(View.VISIBLE);
    }

    public void setAssessmentButtons()
    {
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAssessmentText();
                //score does not change
            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAssessmentText();
                score++;
            }
        });
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAssessmentText();
                score+=2;
            }
        });
        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAssessmentText();
                score+=3;
            }
        });
        button5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AssessmentActivity.this, HomepageActivity.class);
                Bundle b = new Bundle();
                startActivity(intent);
                finish();
            }
        });
    }


}
