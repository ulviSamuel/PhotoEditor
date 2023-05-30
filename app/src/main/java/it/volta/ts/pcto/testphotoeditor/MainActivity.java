package it.volta.ts.pcto.testphotoeditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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
    private View            colorMenu;
    private ImageButton     blackBtn;
    private ImageButton     whiteBtn;
    private ImageButton     skyBlueBtn;
    private ImageButton     yellowBtn;
    private ImageButton     greenBtn;
    private ImageButton     redBtn;

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
        imgButton.setOnLongClickListener(e -> onPencilBtnLongClick());
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
        initColorMenu();
    }

    //---------------------------------------------------------------------------------------------

    private void initColorMenu()
    {
        colorMenu = findViewById(R.id.color_menu);
        blackBtn  = findViewById(R.id.black_btn);
        blackBtn.setOnClickListener(e -> onBlackBtnClicked());
        whiteBtn = findViewById(R.id.white_btn);
        whiteBtn.setOnClickListener(e -> onWhiteBtnClicked());
        skyBlueBtn = findViewById(R.id.sky_blue_btn);
        skyBlueBtn.setOnClickListener(e -> onSkyBlueBtnClicked());
        yellowBtn = findViewById(R.id.yellow_btn);
        yellowBtn.setOnClickListener(e -> onYellowBtnClicked());
        greenBtn = findViewById(R.id.green_btn);
        greenBtn.setOnClickListener(e -> onGreenBtnClicked());
        redBtn = findViewById(R.id.red_btn);
        redBtn.setOnClickListener(e -> onRedBtnClicked());
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
            public void onAddViewListener(@Nullable ViewType viewType, int i)
            {
                hideColorMenu();
            }
            @Override
            public void onRemoveViewListener(@Nullable ViewType viewType, int i)
            {
                hideColorMenu();
            }
            @Override
            public void onStartViewChangeListener(@Nullable ViewType viewType)
            {
                if(viewType == ViewType.TEXT)
                    activeTextMode();
                hideColorMenu();
            }
            @Override
            public void onStopViewChangeListener(@Nullable ViewType viewType)
            {
                hideColorMenu();
            }
            @Override
            public void onTouchSourceImage(@Nullable MotionEvent motionEvent)
            {
                hideColorMenu();
            }
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

    private boolean onPencilBtnLongClick()
    {
        showColorMenu();
        selectZoomLine.setVisibility(View.INVISIBLE);
        selectEraserLine.setVisibility(View.INVISIBLE);
        selectInsTextLine.setVisibility(View.INVISIBLE);
        selectPencilLine.setVisibility(View.VISIBLE);
        return false;
    }

    //---------------------------------------------------------------------------------------------

    private void showColorMenu()
    {
        colorMenu.setVisibility(View.VISIBLE);
        blackBtn.setVisibility(View.VISIBLE);
        whiteBtn.setVisibility(View.VISIBLE);
        skyBlueBtn.setVisibility(View.VISIBLE);
        yellowBtn.setVisibility(View.VISIBLE);
        greenBtn.setVisibility(View.VISIBLE);
        redBtn.setVisibility(View.VISIBLE);
    }

    //---------------------------------------------------------------------------------------------

    private void onEraserBtnClick()
    {
        disableZoomLayout();
        hideColorMenu();
        mPhotoEditor.brushEraser();
        selectZoomLine.setVisibility(View.INVISIBLE);
        selectPencilLine.setVisibility(View.INVISIBLE);
        selectInsTextLine.setVisibility(View.INVISIBLE);
        selectEraserLine.setVisibility(View.VISIBLE);
    }

    //---------------------------------------------------------------------------------------------

    private void hideColorMenu()
    {
        colorMenu.setVisibility(View.INVISIBLE);
        blackBtn.setVisibility(View.INVISIBLE);
        whiteBtn.setVisibility(View.INVISIBLE);
        skyBlueBtn.setVisibility(View.INVISIBLE);
        yellowBtn.setVisibility(View.INVISIBLE);
        greenBtn.setVisibility(View.INVISIBLE);
        redBtn.setVisibility(View.INVISIBLE);
    }

    //---------------------------------------------------------------------------------------------

    private void onUndoBtnClick()
    {
        hideColorMenu();
        mPhotoEditor.undo();
    }

    //---------------------------------------------------------------------------------------------

    private void onRedoBtnClick()
    {
        hideColorMenu();
        mPhotoEditor.redo();
    }

    //---------------------------------------------------------------------------------------------

    private void onDeleteBtnClick()
    {
        hideColorMenu();
        mPhotoEditor.clearAllViews();
    }

    //---------------------------------------------------------------------------------------------

    private void onInsTextBtnClick()
    {
        activeTextMode();
        mPhotoEditor.addText("Enter Text", R.color.black);
    }

    //---------------------------------------------------------------------------------------------

    private void activeTextMode()
    {
        hideColorMenu();
        disableZoomLayout();
        selectZoomLine.setVisibility(View.INVISIBLE);
        selectPencilLine.setVisibility(View.INVISIBLE);
        selectEraserLine.setVisibility(View.INVISIBLE);
        selectInsTextLine.setVisibility(View.VISIBLE);
        mPhotoEditor.setBrushDrawingMode(false);
    }

    //---------------------------------------------------------------------------------------------

    private void onZoomBtnClick()
    {
        hideColorMenu();
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
        hideColorMenu();
        mPhotoEditor.setBrushColor(Color.parseColor("#000000"));
        whiteBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        skyBlueBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        yellowBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        greenBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        redBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        blackBtn.setBackground(new ColorDrawable(Color.parseColor("#4D808080")));
    }

    //---------------------------------------------------------------------------------------------

    private void onWhiteBtnClicked()
    {
        hideColorMenu();
        mPhotoEditor.setBrushColor(Color.parseColor("#FFFFFF"));
        blackBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        skyBlueBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        yellowBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        greenBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        redBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        whiteBtn.setBackground(new ColorDrawable(Color.parseColor("#4D808080")));
    }

    //---------------------------------------------------------------------------------------------

    private void onSkyBlueBtnClicked()
    {
        hideColorMenu();
        mPhotoEditor.setBrushColor(Color.parseColor("#0967D2"));
        blackBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        whiteBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        yellowBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        greenBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        redBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        skyBlueBtn.setBackground(new ColorDrawable(Color.parseColor("#4D808080")));
    }

    //---------------------------------------------------------------------------------------------

    private void onYellowBtnClicked()
    {
        hideColorMenu();
        mPhotoEditor.setBrushColor(Color.parseColor("#F0B429"));
        blackBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        whiteBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        skyBlueBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        greenBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        redBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        yellowBtn.setBackground(new ColorDrawable(Color.parseColor("#4D808080")));
    }

    //---------------------------------------------------------------------------------------------

    private void onGreenBtnClicked()
    {
        hideColorMenu();
        mPhotoEditor.setBrushColor(Color.parseColor("#18981D"));
        blackBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        whiteBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        yellowBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        skyBlueBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        redBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        greenBtn.setBackground(new ColorDrawable(Color.parseColor("#4D808080")));
    }

    //---------------------------------------------------------------------------------------------

    private void onRedBtnClicked()
    {
        hideColorMenu();
        mPhotoEditor.setBrushColor(Color.parseColor("#E12D39"));
        blackBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        whiteBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        yellowBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        greenBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        skyBlueBtn.setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        redBtn.setBackground(new ColorDrawable(Color.parseColor("#4D808080")));
    }

    //---------------------------------------------------------------------------------------------

    private void onCheckBtnClick()
    {
        hideColorMenu();
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