package janche.ripaxiety;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.text.AttributedString;


/**
 * HomepageActivity is an activity class that displays the homepage
 * Created by Liliana on 11/8/2016.
 * modified by: Patrick Abbey 11/18/16
 * modified by: Patrick Abbey 11/30/16
 * modified by: Jose Pena 12/05/2016
 * modified by: Jose Pena 12/7/2016
 */

public class HomepageActivity extends AppCompatActivity
{
    Button assessmentBtn, checklistBtn, panicBtn;
    TextView homepageHeader;
    String getHeaderText[];
    String getHomepageMessage[];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        //homepageHeader();
        assessmentBtn();
        checklistBtn();
        panicBtn();

        Button logout = (Button) findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent =  new Intent(HomepageActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    /*
        set text and functionality for assessment button on homepage
     */
    public void assessmentBtn()
    {
        assessmentBtn = (Button)findViewById(R.id.homePageBtnLeft);
        assessmentBtn.setText("Assessment");
        assessmentBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent =  new Intent(HomepageActivity.this,AssessmentActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
        set text and functionality for checklist button on homepage
     */


    public void checklistBtn()
    {
        checklistBtn = (Button)findViewById(R.id.homePageBtnRight);
        checklistBtn.setText("Checklist");
        checklistBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent =  new Intent(HomepageActivity.this,ChecklistActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
        set text and functionality for panic button on homepage
     */
    public void panicBtn()
    {
        panicBtn = (Button)findViewById(R.id.homePageBtnPanic);
        panicBtn.setText("Panic");
        panicBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder show_links = new AlertDialog.Builder(HomepageActivity.this);
                WebView gif_view = new WebView(show_links.getContext());
                gif_view.loadUrl("file:///android_asset/webview_image.html");
                show_links.setView(gif_view);
                show_links.show();

            }
        });
    }


    //I commented out this header stuff because I don't think it was necessary
    //The reason is because I made 2 textviews for the header in the .xml file for this activity,
    /*
        set header for homepage

    public void homepageHeader()
    {
        homepageHeader = (TextView)findViewById(R.id.homePageHeader);
        getHeaderText = getResources().getStringArray(R.array.homepageHeader);
        getHomepageMessage= getResources().getStringArray(R.array.homepageMessage);
        String headerString = getHeaderText[0] + " " + getHomepageMessage[0];
        homepageHeader.setText(headerString);
        homepageHeader.setTextSize(14);

    }
    */
}





/*


 * HomepageActivity is an activity class that displays the homepage
 * Created by Liliana on 11/8/2016.
 * modified by: Patrick Abbey 11/18/16
 * modified by: Patrick Abbey 11/30/16
 

public class HomepageActivity extends AppCompatActivity
{
    Button assessmentBtn, checklistBtn, panicBtn;
    TextView homepageHeader;
    String getHeaderText[];
    String getHomepageMessage[];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        homepageHeader();
        assessmentBtn();
        checklistBtn();
        panicBtn();
    }

    
        set text and functionality for assessment button on homepage
     
    public void assessmentBtn()
    {
        assessmentBtn = (Button)findViewById(R.id.homePageBtnLeft);
        assessmentBtn.setText("Assessment");
        assessmentBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent =  new Intent(HomepageActivity.this,AssessmentActivity.class);
                startActivity(intent);
            }
        });
    }

    
        set text and functionality for checklist button on homepage
     
    public void checklistBtn()
    {
        checklistBtn = (Button)findViewById(R.id.homePageBtnRight);
        checklistBtn.setText("Checklist");
        checklistBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent =  new Intent(HomepageActivity.this,ChecklistActivity.class);
                startActivity(intent);
            }
        });
    }

    
        set text and functionality for panic button on homepage
     
    public void panicBtn()
    {
        panicBtn = (Button)findViewById(R.id.homePageBtnPanic);
        panicBtn.setText("Panic");
        panicBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder show_links = new AlertDialog.Builder(HomepageActivity.this);
                WebView gif_view = new WebView(show_links.getContext());
                gif_view.loadUrl("file:///android_asset/webview_image.html");
                show_links.setView(gif_view);
                show_links.show();

            }
        });
    }


    
        set header for homepage
     
    public void homepageHeader()
    {
        homepageHeader = (TextView)findViewById(R.id.homePageHeader);
        getHeaderText = getResources().getStringArray(R.array.homepageHeader);
        getHomepageMessage= getResources().getStringArray(R.array.homepageMessage);
        String headerString = getHeaderText[0] + " " + getHomepageMessage[0];
        homepageHeader.setText(headerString);
        homepageHeader.setTextSize(14);

    }
}

*/
