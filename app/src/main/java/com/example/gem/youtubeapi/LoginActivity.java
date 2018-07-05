package com.example.gem.youtubeapi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gem on 7/5/18.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

  private static final int RC_SIGN_IN = 1;
  private static final String TAG = LoginActivity.class.getSimpleName();

  @BindView(R.id.iv_account)
  ImageView mAccountIv;

  @BindView(R.id.tv_email)
  TextView mEmailTv;

  @BindView(R.id.bt_sign_in)
  SignInButton mSignInBt;

  private GoogleSignInClient mGoogleSignInClient;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_layout);
    ButterKnife.bind(this);

    mSignInBt.setSize(SignInButton.SIZE_STANDARD);
    mSignInBt.setOnClickListener(this);

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
  }

  @Override
  protected void onStart() {
    super.onStart();
    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    updateUI(account);
  }

  private void updateUI(GoogleSignInAccount account) {
    if (account != null) {
      Log.e(TAG, "updateUI: ");
      mEmailTv.setText(account.getEmail());
      Picasso.with(this)
          .load(account.getPhotoUrl())
          .fit()
          .into(mAccountIv);
    } else {
      Log.e(TAG, "updateUI: null");
    }
  }

  private void signIn() {
    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      handleSignInResult(task);
    }
  }

  private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
    try {
      GoogleSignInAccount account = completedTask.getResult(ApiException.class);

      // Signed in successfully, show authenticated UI.
      updateUI(account);
    } catch (ApiException e) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
      updateUI(null);
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {

      case R.id.bt_sign_in:
        signIn();
        break;

      default:
        break;
    }
  }
}
