package com.cleancode.easymicrecord.mic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import it.sephiroth.android.library.tooltip.Tooltip;

import static androidx.core.view.ViewCompat.setTooltipText;
import static com.cleancode.easymicrecord.mic.permissions.PERM_MIC;
import static com.cleancode.easymicrecord.mic.permissions.PERM_READ_EXTERNAL;
import static com.cleancode.easymicrecord.mic.permissions.PERM_WRITE_EXTERNAL;
import static com.cleancode.easymicrecord.mic.permissions.checkPermission;

public class micRecord {
    public interface onRecordListener { void onRecordSuccess(String tag, String path); void onRecordError(String tag);}

    private final Tooltip.TooltipView tooltip;
    private final onRecordListener mListener;
    private final Activity context;
    private MediaRecorder ready;
    private final String tag;
    private final View rec;
    private String path;

    public micRecord(Activity context, View rec, String tag){
        this.tooltip = getTooltip(context, rec, "Recing...");
        this.mListener = (onRecordListener) context;
        this.context = context;
        this.rec = rec;
        this.tag = tag;
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        setTooltipText(rec, "Recing...");
        rec.setOnLongClickListener(v -> {
            if(checkPermission(context, PERM_MIC, PERM_WRITE_EXTERNAL, PERM_READ_EXTERNAL)){
                tooltip.show();
                takeAudio();
            }
            return false;
        });

        rec.setOnTouchListener((v, event) -> {
            if(checkPermission(context, PERM_MIC, PERM_WRITE_EXTERNAL, PERM_READ_EXTERNAL)){
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    stop();
                }
            }
            return false;
        });
    }

    private void takeAudio() {
        File p = createAudioFile();
        if(p != null) {
            try {
                path = p.getAbsolutePath();
                ready = new MediaRecorder();
                ready.setAudioSource(MediaRecorder.AudioSource.MIC);
                ready.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                ready.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                ready.setOutputFile(path);
                ready.prepare();
                ready.start();
                Log.e("startRecAudio", "Now");

            } catch (IOException e) {
                Toast.makeText(context, "Error while recording audio, please try again!", Toast.LENGTH_SHORT).show();
                Log.e("Error takeAudio", e.toString());
                stopTooltip();
            }

        }else{
            Toast.makeText(context, "Error while recording audio, please try again!", Toast.LENGTH_SHORT).show();
            stopTooltip();
        }
    }

    private File createAudioFile(){
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "audios/");
        if(path.mkdirs()){
            return new File(path, "FileName_" + getKey() + ".3gp");

        }else{
            if(path.exists()){
                return new File(path, "FileName_" + getKey() + ".3gp");

            } else {
                return null;

            }
        }
    }

    private void stopTooltip(){
        if(tooltip.isShown()){
            tooltip.hide();
        }
    }

    private void stop(){
        if(ready != null) {
            Log.e("stopRecAudio", "Now");
            try {
                ready.stop();
                ready.release();
                ready = null;

                Toast.makeText(context, "Recording completed successfully!", Toast.LENGTH_SHORT).show();
                mListener.onRecordSuccess(tag, path);

            } catch (Exception e) {
                ready = null;
                Log.e("Error Stop", e.toString());
                if(new File(path).delete()) { Log.e("Error Stop", "Error File Deleted"); }
                Toast.makeText(context,"Recording canceled!", Toast.LENGTH_SHORT).show();
            }

            stopTooltip();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getKey() {
        return new SimpleDateFormat("ddMMyyyyHHmmssSS").format(Calendar.getInstance().getTime());
    }

    private Tooltip.TooltipView getTooltip(Context context, View view, String text){
        return Tooltip.make(context, new Tooltip.Builder(101)
                .anchor(view, Tooltip.Gravity.TOP)
                .text(text)
                .maxWidth(700)
                .withArrow(true)
                .withOverlay(true)
                .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                .build()
        );
    }
}