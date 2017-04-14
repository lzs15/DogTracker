package com.example.c65317.dogtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DogList extends AppCompatActivity {

    // Declaring layout button, edit texts
    public Button run;
    public Button insert;
    public Button delete;
    public Button update;
    public TextView message;
    public ProgressBar progressBar;

// End Declaring layout button, edit texts

    // Declaring connection variables
    public Connection con;
    String un, pass, db, ip;
//End Declaring connection variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list);
        // Getting values from button, texts and progress bar
        run = (Button) findViewById(R.id.RunButton);
        insert = (Button) findViewById(R.id.InsertButton);
        update = (Button) findViewById(R.id.UpdateButton);
        delete = (Button) findViewById(R.id.DeleteButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

// End Getting values from button, texts and progress bar

// Declaring Server ip, username, database name and password
        ip = "dogtracker.database.windows.net:1433";
        db = "DogTracker";
        un = "DogAdmin";
        pass = "Password1234";
// Declaring Server ip, username, database name and password

// Setting up the function when button login is clicked
        run.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       CheckLogin checkLogin = new CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                                       checkLogin.execute("");
                                   }
                               }
        );
// Setting up the function when button insert is clicked
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInsert checkInsert = new CheckInsert();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkInsert.execute("");
            }
        });
// Setting up the function when button update is clicked
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUpdate checkUpdate = new CheckUpdate();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkUpdate.execute("");
            }
        });
// Setting up the function when button delete is clicked
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckDelete checkDelete = new CheckDelete();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkDelete.execute("");
            }
        });

//End Setting up the function when button login is clicked
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";


        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(DogList.this, r, Toast.LENGTH_LONG).show();
            if (isSuccess) {
                message = (TextView) findViewById(R.id.textView2);
                message.setText(name1);

            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                con = connectionclass(); // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
// Change below query according to your own database.
                    String query = "select LastName from LakelySmith";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        name1 = rs.getString("LastName"); //Name is the string label of a column in database, read through the select query
                        z = "query successful";
                        isSuccess = true;
                        con.close();

                    } else {
                        z = "Invalid Query!";
                        isSuccess = false;
                    }
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();

                Log.d("sql error", z);
            }

            return z;
        }
    }

    public class CheckInsert extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";


        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(DogList.this, r, Toast.LENGTH_LONG).show();
            if (isSuccess) {
                message = (TextView) findViewById(R.id.textView2);
                message.setText(name1);

            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                con = connectionclass(); // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
// Change below query according to your own database.
                    String query = "INSERT INTO LakelySmith([FirstName], [LastName])" +
                            "Values (N'Austin', N'Smith')";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);

                    z = "Successfully added 'Austin Smith' to database";

                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();

                Log.d("sql error", z);
            }

            return z;
        }
    }

    public class CheckUpdate extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";


        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(DogList.this, r, Toast.LENGTH_LONG).show();
            if (isSuccess) {
                message = (TextView) findViewById(R.id.textView2);
                message.setText(name1);

            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                con = connectionclass(); // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
// Change below query according to your own database.
                    String query = "UPDATE LakelySmith " +
                            "SET LastName = 'Kalamazoo' WHERE FirstName = 'Lakely'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);

                    z = "Successfully updated last name for 'Lakely'";
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();

                Log.d("sql error", z);
            }

            return z;
        }
    }

    public class CheckDelete extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";


        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(DogList.this, r, Toast.LENGTH_LONG).show();
            if (isSuccess) {
                message = (TextView) findViewById(R.id.textView2);
                message.setText(name1);

            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                con = connectionclass(); // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
// Change below query according to your own database.
                    String query = "DELETE FROM LakelySmith " +
                            "WHERE LakelySmith.FirstName = 'Austin'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);

                    z = "Successfully deleted 'Austin' from records";
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();

                Log.d("sql error", z);
            }

            return z;
        }
    }


    @SuppressLint("NewApi")
    public Connection connectionclass() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//your database connection string goes below
            ConnectionURL = "jdbc:jtds:sqlserver://dogtracker.database.windows.net:1433;DatabaseName=DogTracker;user=DogAdmin@dogtracker;password=Password1234;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("error here 1 : ", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error here 2 : ", e.getMessage());
        } catch (Exception e) {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}

