package com.example.gem.youtubeapi;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;


public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

  private static final int RECOVERY_REQUEST = 1;
  YouTubePlayerView youTubePlayerView;

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
    if (!b) {
      youTubePlayer.cueVideo("C3KzANL6gTY");
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
}
