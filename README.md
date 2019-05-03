# Mic-Record---Android

Simple way to record audio with native Android

![Img Ex](https://i.ibb.co/bFyND53/Gravar-2019-05-03-18-01-46-628.gif)

First of all we must add these 3 permissions in the manifest file:

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.cleancode.easymicrecord">

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    
    <application...
</manifest>
```

After that, in the project there is a package named "mic", in it there are the 2 configuration files, to use them in a simple way just get into your Activity and implement its interface like this:

`public class MainActivity extends AppCompatActivity implements micRecord.onRecordListener {...`

It will implement 2 methods:

```
@Override
public void onRecordSuccess(String tag, String path) {

}

@Override
public void onRecordError(String tag) {

}
```

Now let's start the object for recording:
```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
                 //Activity //AnyView              //Tag
    new micRecord(this, findViewById(R.id.btnRec), "myTag");
}
```

Ready! Now holding down the View as a parameter will start recording!

# Depentions
===

In this project we used a library to create the Tooltip while holding the view of recording, to implement it just follow the [steps here:](https://github.com/sephiroth74/android-target-tooltip)
