package it.volta.ts.pcto.testphotoeditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.otaliastudios.zoom.ZoomLayout;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.ViewType;

public class MainActivity extends Activity
{
    private PhotoEditor     mPhotoEditor;
    private PhotoEditorView mPhotoEditorView;
    private ZoomLayout      zoomLayout;
    private View            selectZoomLine;
    private View            selectPencilLine;
    private View            selectEraserLine;
    private View            selectInsTextLine;

    //---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);
        initVariables();
        initView();
    }

    //---------------------------------------------------------------------------------------------

    private void initVariables()
    {
        mPhotoEditor = null;
    }

    //---------------------------------------------------------------------------------------------

    private void initView()
    {
        configDesignBar();
        configPhotoEditor();
        configZoomLayout();
        configureCheckButton();
    }

    //---------------------------------------------------------------------------------------------

    private void configDesignBar()
    {
        ImageButton imgButton = findViewById(R.id.pencil_btn);
        imgButton.setOnClickListener(e -> onPencilBtnClick());
        imgButton = findViewById(R.id.eraser_btn);
        imgButton.setOnClickListener(e -> onEraserBtnClick());
        imgButton = findViewById(R.id.undo_btn);
        imgButton.setOnClickListener(e -> onUndoBtnClick());
        imgButton = findViewById(R.id.redo_btn);
        imgButton.setOnClickListener(e -> onRedoBtnClick());
        imgButton = findViewById(R.id.delete_btn);
        imgButton.setOnClickListener(e -> onDeleteBtnClick());
        imgButton = findViewById(R.id.ins_text_btn);
        imgButton.setOnClickListener(e -> onInsTextBtnClick());
        imgButton = findViewById(R.id.zoom_btn);
        imgButton.setOnClickListener(e -> onZoomBtnClick());
        selectZoomLine = findViewById(R.id.select_line_zoom);
        selectZoomLine.setVisibility(View.VISIBLE);
        selectPencilLine  = findViewById(R.id.select_line_pencil);
        selectEraserLine  = findViewById(R.id.select_line_eraser);
        selectInsTextLine = findViewById(R.id.select_line_ins_text);
        initPencilMenu();
    }

    //---------------------------------------------------------------------------------------------

    private void initPencilMenu()
    {
        ImageButton imgButton = findViewById(R.id.black_btn);
        imgButton.setOnClickListener(e ->  onBlackBtnClicked());
        imgButton = findViewById(R.id.white_btn);
        imgButton.setOnClickListener(e -> onWhiteBtnClicked());
        imgButton = findViewById(R.id.sky_blue_btn);
        imgButton.setOnClickListener(e -> onSkyBlueBtnClicked());
        imgButton = findViewById(R.id.yellow_btn);
        imgButton.setOnClickListener(e -> onYellowBtnClicked());
        imgButton = findViewById(R.id.green_btn);
        imgButton.setOnClickListener(e -> onGreenBtnClicked());
        imgButton = findViewById(R.id.red_btn);
        imgButton.setOnClickListener(e -> onRedBtnClicked());
    }

    //---------------------------------------------------------------------------------------------

    private void configPhotoEditor()
    {
        mPhotoEditorView = findViewById(R.id.photo_editor_view);
        mPhotoEditorView.getSource().setImageResource(R.drawable.img_test_icon);
        Typeface mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);
        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                                                  .setPinchTextScalable(true)
                                                  .setClipSourceImage(true)
                                                  .setDefaultTextTypeface(mTextRobotoTf)
                                                  .build();
        configListeners();
    }

    //---------------------------------------------------------------------------------------------

    private void configZoomLayout()
    {
        zoomLayout = findViewById(R.id.zoom_layout);
        activeZoomLayout();
    }

    //---------------------------------------------------------------------------------------------

    private void activeZoomLayout()
    {
        zoomLayout.setZoomEnabled(true);
        zoomLayout.setHorizontalPanEnabled(true);
        zoomLayout.setVerticalPanEnabled(true);
    }

    //---------------------------------------------------------------------------------------------

    private void configureCheckButton()
    {
        ImageButton imgButton = findViewById(R.id.check_btn);
        imgButton.setOnClickListener(e -> onCheckBtnClick());
    }

    //---------------------------------------------------------------------------------------------

    private void disableZoomLayout()
    {
        zoomLayout.setZoomEnabled(false);
        zoomLayout.setHorizontalPanEnabled(false);
        zoomLayout.setVerticalPanEnabled(false);
    }

    //---------------------------------------------------------------------------------------------

    private void configListeners()
    {
        mPhotoEditor.setOnPhotoEditorListener(new OnPhotoEditorListener()
        {
            @Override
            public void onEditTextChangeListener(@Nullable View view, @Nullable String s, int i)
            {
                changeText(view, i);
            }
            @Override
            public void onAddViewListener(@Nullable ViewType viewType, int i) {}
            @Override
            public void onRemoveViewListener(@Nullable ViewType viewType, int i) {}
            @Override
            public void onStartViewChangeListener(@Nullable ViewType viewType) {}
            @Override
            public void onStopViewChangeListener(@Nullable ViewType viewType) {}
            @Override
            public void onTouchSourceImage(@Nullable MotionEvent motionEvent) {}
        });
    }

    //---------------------------------------------------------------------------------------------

    private void onPencilBtnClick()
    {
        disableZoomLayout();
        mPhotoEditor.setBrushDrawingMode(true);
        selectZoomLine.setVisibility(View.INVISIBLE);
        selectEraserLine.setVisibility(View.INVISIBLE);
        selectInsTextLine.setVisibility(View.INVISIBLE);
        selectPencilLine.setVisibility(View.VISIBLE);
    }

    //---------------------------------------------------------------------------------------------

    private void onEraserBtnClick()
    {
        disableZoomLayout();
        mPhotoEditor.brushEraser();
        selectZoomLine.setVisibility(View.INVISIBLE);
        selectPencilLine.setVisibility(View.INVISIBLE);
        selectInsTextLine.setVisibility(View.INVISIBLE);
        selectEraserLine.setVisibility(View.VISIBLE);
    }

    //---------------------------------------------------------------------------------------------

    private void onUndoBtnClick()
    {
        mPhotoEditor.undo();
    }

    //---------------------------------------------------------------------------------------------

    private void onRedoBtnClick()
    {
        mPhotoEditor.redo();
    }

    //---------------------------------------------------------------------------------------------

    private void onDeleteBtnClick()
    {
        mPhotoEditor.clearAllViews();
    }

    //---------------------------------------------------------------------------------------------

    private void onInsTextBtnClick()
    {
        disableZoomLayout();
        mPhotoEditor.addText("Enter Text", R.color.black);
        selectZoomLine.setVisibility(View.INVISIBLE);
        selectPencilLine.setVisibility(View.INVISIBLE);
        selectEraserLine.setVisibility(View.INVISIBLE);
        selectInsTextLine.setVisibility(View.VISIBLE);
    }

    //---------------------------------------------------------------------------------------------

    private void onZoomBtnClick()
    {
        mPhotoEditor.setBrushDrawingMode(false);
        activeZoomLayout();
        selectPencilLine.setVisibility(View.INVISIBLE);
        selectEraserLine.setVisibility(View.INVISIBLE);
        selectInsTextLine.setVisibility(View.INVISIBLE);
        selectZoomLine.setVisibility(View.VISIBLE);
    }

    //---------------------------------------------------------------------------------------------

    private void onBlackBtnClicked()
    {
        mPhotoEditor.setBrushColor(Color.parseColor("#000000"));
    }

    //---------------------------------------------------------------------------------------------

    private void onWhiteBtnClicked()
    {
        mPhotoEditor.setBrushColor(Color.parseColor("#FFFFFF"));
    }

    //---------------------------------------------------------------------------------------------

    private void onSkyBlueBtnClicked()
    {
        mPhotoEditor.setBrushColor(Color.parseColor("#0967D2"));
    }

    //---------------------------------------------------------------------------------------------

    private void onYellowBtnClicked()
    {
        mPhotoEditor.setBrushColor(Color.parseColor("#F0B429"));
    }

    //---------------------------------------------------------------------------------------------

    private void onGreenBtnClicked()
    {
        mPhotoEditor.setBrushColor(Color.parseColor("#18981D"));
    }

    //---------------------------------------------------------------------------------------------

    private void onRedBtnClicked()
    {
        mPhotoEditor.setBrushColor(Color.parseColor("#E12D39"));
    }

    //---------------------------------------------------------------------------------------------

    private void onCheckBtnClick()
    {
        mPhotoEditor.saveAsBitmap(new OnSaveBitmap()
        {
            @Override
            public void onBitmapReady(@Nullable Bitmap bitmap)
            {
                //"bitmap" è l'immagine modificata"
            }
            @Override
            public void onFailure(@Nullable Exception e) {}
        });
    }

    //---------------------------------------------------------------------------------------------

    private void changeText(View view, int i)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter text");
        final EditText editText = new EditText(this);
        builder.setView(editText);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            String inputText = editText.getText().toString();
            mPhotoEditor.editText(view, inputText, i);
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}