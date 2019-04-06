package com.mazerapp.tec.udacityculinaria.modules.recipe_view.video_view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mazerapp.tec.udacityculinaria.R;

public class VideoViewFragment extends Fragment {

    private SimpleExoPlayer player;
    private String videoUrl = "";
    private long position = 0;
    private int window = 0;
    private boolean playWhenReady = true;
    private PlayerView playerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_video_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getExtras();
        setupView(view);
    }

    public void setupView(View view){
        playerView = view.findViewById(R.id.player_view);
    }

    private void initPlayer(){
        player = ExoPlayerFactory.newSimpleInstance(
                getContext(),
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl()
        );

        playerView.setPlayer(player);
        prepareMediaSource();
        player.setPlayWhenReady(true);
        player.seekTo(window, position);

    }

    private void releasePlayer() {
        playWhenReady = false;
        if(player != null)
            player.release();
    }

    private void prepareMediaSource(){
        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri){
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("player-agent")).createMediaSource(uri);
    }

    public static VideoViewFragment newInstance(String videoUrl, long position, int window) {
        VideoViewFragment myFragment = new VideoViewFragment();

        Bundle args = new Bundle();
        args.putString("videoUrl", videoUrl);
        args.putLong("position", position);
        args.putInt("window", window);
        myFragment.setArguments(args);

        return myFragment;
    }

    public void getExtras(){
        videoUrl = getVideoUrl();
        position = getPosition();
        window = getWindow();
    }

    private String getVideoUrl() {
        if (getArguments() != null) {
            return getArguments().getString("videoUrl");
        }else{
            return "";
        }
    }
    private Long getPosition() {
        if (getArguments() != null) {
            return getArguments().getLong("position");
        }else{
            return 0L;
        }
    }
    private int getWindow() {
        if (getArguments() != null) {
            return getArguments().getInt("window");
        }else{
            return 0;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initPlayer();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
