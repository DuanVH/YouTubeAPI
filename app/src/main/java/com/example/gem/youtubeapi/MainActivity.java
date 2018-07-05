package com.example.gem.youtubeapi;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;


public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener {

  private static final int RECOVERY_REQUEST = 1;
  private static final String TAG = MainActivity.class.getSimpleName();
  YouTubePlayerView youTubePlayerView;

  YouTubePlayer mYouTubePlayer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews();

  }

  private void initViews() {
    youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
    youTubePlayerView.initialize(Config.YOUTUBE_API_KEY, this);

  }

  @Override
  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
    mYouTubePlayer = youTubePlayer;
    mYouTubePlayer.setFullscreen(true);
    mYouTubePlayer.setPlayerStateChangeListener(this);
    if (!b) {
      mYouTubePlayer.cueVideo("C3KzANL6gTY");
    }
  }

  @Override
  public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
    if (errorReason.isUserRecoverableError()) {
      errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
    } else {
      String error = "error";
      Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == RECOVERY_REQUEST) {
      getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
    }
  }

  protected Provider getYouTubePlayerProvider() {
    return youTubePlayerView;
  }

  @Override
  public void onLoading() {
    Log.e(TAG, "onLoading: " );
  }

  @Override
  public void onLoaded(String s) {
    Log.e(TAG, "onLoaded: " );
  }

  @Override
  public void onAdStarted() {
    Log.e(TAG, "onAdStarted: " );
  }

  @Override
  public void onVideoStarted() {
    Log.e(TAG, "onVideoStarted: " );
  }

  @Override
  public void onVideoEnded() {
    Log.e(TAG, "onVideoEnded: " );
  }

  @Override
  public void onError(YouTubePlayer.ErrorReason errorReason) {
    Log.e(TAG, "onError: " );
    mYouTubePlayer.cueVideo("C3KzANL6gTY");
  }

}
