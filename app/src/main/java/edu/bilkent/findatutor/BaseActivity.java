package edu.bilkent.findatutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import static com.google.firebase.auth.FirebaseAuth.getInstance;


public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog mProgressDialog;


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public String getUid() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        else
            return null;
    }
    public String getEmail() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            return FirebaseAuth.getInstance().getCurrentUser().getEmail();
        else
            return null;
    }

    public String getUserName() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        else
            return null;
    }

    public String getPhotoURL() {
        if (getInstance().getCurrentUser() == null)
            return null;

        if (getInstance().getCurrentUser().getPhotoUrl() != null)
            return getInstance().getCurrentUser().getPhotoUrl().toString();
        else
            return "https://lh3.googleusercontent.com/-EF9BoynKc9w/AAAAAAAAAAI/AAAAAAAAAAA/1au5roMkCC4/photo.jpg";
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ads) {
            startActivity(new Intent(this, MainActivity.class));

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_notfications) {
            startActivity(new Intent(this, NotificationListActivity.class));

        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(this, MessageListActivity.class));

        } else if (id == R.id.nav_sessions) {
            startActivity(new Intent(this, SessionListActivity.class));

        } else if (id == R.id.nav_statistics) {

        } else if (id == R.id.nav_settings) {


        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else
            return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
