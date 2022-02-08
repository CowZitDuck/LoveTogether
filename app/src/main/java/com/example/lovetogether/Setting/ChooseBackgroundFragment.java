package com.example.lovetogether.Setting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.BitmapCompat;
import androidx.fragment.app.Fragment;

import com.example.lovetogether.MainActivity;
import com.example.lovetogether.MainApplication;
import com.example.lovetogether.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChooseBackgroundFragment extends Fragment {

    public static final int CALL_GALLERY = 6658289;
    private static final int REQUEST_CODE_FROM_GALLERY = 234789;

    private Context context;
    private GridView gv_gallery;
    private ArrayList<Bitmap> listImage;
    private GalleryAdapter galleryAdapter;
    private ICommunicate iCommunicate;
    private View mView;

    public interface ICommunicate {
        void setBackground(Bitmap image);
    }
    public ChooseBackgroundFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_choose_image, container, false);
        return mView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iCommunicate = (ICommunicate) getActivity();
    }

    private void Init() {
        gv_gallery = (GridView) mView.findViewById(R.id.gv_gallery);
        listImage = new ArrayList<>();
        UpdateDataFromDataBase();
    }

    public void UpdateDataFromDataBase() {
        listImage.clear();
        listImage.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.add_background));
        listImage.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.wp_1));
        listImage.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.wp_2));
        listImage.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.wp_3));
        listImage.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.wp_4));
        listImage.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.wp_5));
        listImage.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.wp_6));
        listImage.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.wp_7));
        listImage.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.wp_8));
        listImage.add(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.wp_9));

        Cursor cursor = MainActivity.dataBase.getData("SELECT * FROM ImageTable");
        while (cursor.moveToNext()) {
            byte[] image = cursor.getBlob(1);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            listImage.add(bitmap);
        }
        galleryAdapter = new GalleryAdapter(context,R.layout.layout_cell_image, listImage);
        gv_gallery.setAdapter(galleryAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();
        setOnClick();
    }

    public void setOnClick() {
        gv_gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {
                    Intent it_chooseImage = new Intent(Intent.ACTION_PICK);
                    it_chooseImage.setType("image/*");
                    startActivityForResult(it_chooseImage, REQUEST_CODE_FROM_GALLERY);
                }
                else if (i >= 1 && i <= 9) {
                    iCommunicate.setBackground(listImage.get(i));
                    MainActivity.editor.putBoolean(MainActivity.HAS_BACKGROUND, true);
                    MainActivity.editor.putBoolean(MainActivity.IS_BACKGROUND_FROM_DATABASE, false);
                    MainActivity.editor.putInt(MainActivity.ID_BACKGROUND, i);
                    MainActivity.editor.commit();
                } else {
                    iCommunicate.setBackground(listImage.get(i));
                    MainActivity.editor.putBoolean(MainActivity.HAS_BACKGROUND, true);
                    MainActivity.editor.putBoolean(MainActivity.IS_BACKGROUND_FROM_DATABASE, true);
                    MainActivity.editor.putInt(MainActivity.ID_BACKGROUND, i - 9);
                    MainActivity.editor.commit();
                }
            }
        });
        gv_gallery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) Toast.makeText(context, "Vui lòng chọn một hình nền", Toast.LENGTH_SHORT).show();
                else {
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_menu_choose_background);
                    Button btn_setBackground = (Button) dialog.findViewById(R.id.btn_set_background);
                    Button btn_delete = (Button) dialog.findViewById(R.id.btn_delete);
                    Button btn_setBackgroundDevice = (Button) dialog.findViewById(R.id.btn_set_background_device);
                    btn_setBackground.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (i >= 1 && i <= 9) {
                                iCommunicate.setBackground(listImage.get(i));
                                MainActivity.editor.putBoolean(MainActivity.HAS_BACKGROUND, true);
                                MainActivity.editor.putBoolean(MainActivity.IS_BACKGROUND_FROM_DATABASE, false);
                                MainActivity.editor.putInt(MainActivity.ID_BACKGROUND, i);
                                MainActivity.editor.commit();
                            } else {
                                iCommunicate.setBackground(listImage.get(i));
                                MainActivity.editor.putBoolean(MainActivity.HAS_BACKGROUND, true);
                                MainActivity.editor.putBoolean(MainActivity.IS_BACKGROUND_FROM_DATABASE, true);
                                MainActivity.editor.putInt(MainActivity.ID_BACKGROUND, i - 9);
                                MainActivity.editor.commit();
                            }
                            dialog.dismiss();
                        }
                    });
                    btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (i >= 1 && i <= 9) Toast.makeText(context, "Không thể xóa hình nền mặc định", Toast.LENGTH_SHORT).show();
                            else {
                                AlertDialog.Builder dl_delete = new AlertDialog.Builder(context);
                                dl_delete.setMessage("Bạn có chắc muốn xóa hình nền này không ?");
                                dl_delete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int j) {
                                        MainActivity.dataBase.DeleteImage(MainActivity.IMAGE_TABLE, i - 9);
                                        UpdateDataFromDataBase();
                                    }
                                });
                                dl_delete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(context, "Hủy xóa hình nền", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dl_delete.show();
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == MainActivity.RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_FROM_GALLERY:
                    Uri uri = data.getData();
                    try {
                        InputStream inputStream = context.getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        if (BitmapCompat.getAllocationByteCount(bitmap) <= MainApplication.MAX_OF_BYTES) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] image = byteArrayOutputStream.toByteArray();
                            MainActivity.dataBase.InsertImage(MainActivity.IMAGE_TABLE, image);
                            UpdateDataFromDataBase();
                            int res = 0;
                            Cursor cursor = MainActivity.dataBase.getData("SELECT * FROM " + MainActivity.IMAGE_TABLE + "");
                            while (cursor.moveToNext()) {
                                res = cursor.getInt(0);
                            }
                            MainActivity.editor.putBoolean(MainActivity.HAS_BACKGROUND, true);
                            MainActivity.editor.putBoolean(MainActivity.IS_BACKGROUND_FROM_DATABASE, true);
                            MainActivity.editor.putInt(MainActivity.ID_BACKGROUND, res);
                            MainActivity.editor.commit();
                            iCommunicate.setBackground(bitmap);
                        } else {
                            Toast.makeText(context, "Kích thước tệp quá lớn", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
