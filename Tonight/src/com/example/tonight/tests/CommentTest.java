package com.example.tonight.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.tonight.CommentActivity;
import com.example.tonight.R;


public class CommentTest extends ActivityInstrumentationTestCase2<CommentActivity> {
    private CommentActivity mCommentActivity;
    private EditText  mCommentText;
    private Button mPhotoButton;
    private Button mVideoButton;
    private ImageView mImageView;
    private VideoView mVideoView;



    public CommentTest() {super(CommentActivity.class);}

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        mCommentActivity = getActivity();
        mCommentText=(EditText)mCommentActivity.findViewById(R.id.commentText);
        mPhotoButton=(Button)mCommentActivity.findViewById(R.id.takePicture);
        mVideoButton=(Button)mCommentActivity.findViewById(R.id.takeVideo);
        mImageView=(ImageView)mCommentActivity.findViewById(R.id.photoView);
        mVideoView=(VideoView)mCommentActivity.findViewById(R.id.videoView);
    }

    @SmallTest
    public void testPreconditions() {
        assertNotNull("mCommentActivity is null", mCommentActivity);
        assertNotNull("mCommentText is null",mCommentText);
        assertNotNull("mPhotoButton is null",mPhotoButton);
        assertNotNull("mVideoButton",mVideoButton);
        assertNotNull("mImageView",mImageView);
        assertNotNull("mVideoView",mVideoView);

    }
    @MediumTest
    public void testLayout() {
        final ViewGroup.LayoutParams buttonParams =
                mPhotoButton.getLayoutParams();
        final ViewGroup.LayoutParams textParams =
                mCommentText.getLayoutParams();
        final ViewGroup.LayoutParams imageParams =
                mImageView.getLayoutParams();
        final ViewGroup.LayoutParams videoParams =
                mVideoView.getLayoutParams();
        final ViewGroup.LayoutParams videoButtonParams =
                mVideoButton.getLayoutParams();

        assertNotNull(buttonParams);
        assertNotNull(textParams);
        assertNotNull(imageParams);
        assertNotNull(videoParams);
        assertNotNull(videoButtonParams);

        assertEquals(buttonParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(buttonParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(textParams.width, WindowManager.LayoutParams.FILL_PARENT);
        assertEquals(textParams.height, WindowManager.LayoutParams.FILL_PARENT);
        assertEquals(imageParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(imageParams.height, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(videoParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(videoParams.height, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(videoButtonParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(videoButtonParams.height, WindowManager.LayoutParams.WRAP_CONTENT);

    }
}
