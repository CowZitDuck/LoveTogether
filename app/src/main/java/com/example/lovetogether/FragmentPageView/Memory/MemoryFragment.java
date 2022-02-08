package com.example.lovetogether.FragmentPageView.Memory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MemoryFragment extends Fragment {
    private static final int REQUEST_FROM_GALLERY = 78450;
    private View mView;
    private MemoryAdapter memoryAdapter;
    private ListView listView;
    private ArrayList<Memory> list_memory;
    private Context context;
    private ImageButton btn_addImageMemory;
    private ImageButton btn_addMemory;
    private Button btn_ok;
    private Button btn_dismiss;
    private EditText et_name;
    private EditText et_date;
    private EditText et_content;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    public MemoryFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.pageview_memory, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init();
        setOnclick();
    }

    private void setOnclick() {
        btn_addMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_memory);
                btn_addImageMemory = (ImageButton) dialog.findViewById(R.id.btn_add_image_memory_add);
                Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok_add);
                btn_dismiss = (Button) dialog.findViewById(R.id.btn_dis_add);
                et_name = (EditText) dialog.findViewById(R.id.et_title_memory_add);
                et_date = (EditText) dialog.findViewById(R.id.et_date_memory_add);
                et_content = (EditText) dialog.findViewById(R.id.et_content_memory_add);

                btn_addImageMemory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent it_callFragment = new Intent(Intent.ACTION_PICK);
                        it_callFragment.setType("image/*");
                        startActivityForResult(it_callFragment, REQUEST_FROM_GALLERY);
                    }
                });
                et_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DATE);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                calendar.set(i, i1, i2);
                                et_date.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });
                btn_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = et_name.getText().toString().trim();
                        String date = et_date.getText().toString();
                        String content = et_content.getText().toString();
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) btn_addImageMemory.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] image = byteArrayOutputStream.toByteArray();
                        MainActivity.dataBase.InsertMemory(name, date, content, image);
                        dialog.dismiss();
                        UpdateFromDataBase();
                    }
                });
                dialog.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = i + 1;
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_menu_memory);
                Button btn_delete = dialog.findViewById(R.id.btn_delete_memory);
                Button btn_replace = dialog.findViewById(R.id.btn_replace_memory);
                btn_replace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Dialog dialog_add = new Dialog(context);
                        dialog_add.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog_add.setContentView(R.layout.dialog_replace_memory);
                        Cursor cursor = MainActivity.dataBase.getData("SELECT * FROM " + MainActivity.MEMORY_TABLE);
                        while (cursor.moveToNext()) {
                            if(cursor.getInt(0) == id) break;
                        }
                        btn_addImageMemory = (ImageButton) dialog_add.findViewById(R.id.btn_add_image_memory);
                        byte[] img_src = cursor.getBlob(4);
                        Bitmap bm_src = BitmapFactory.decodeByteArray(img_src, 0, img_src.length);
                        btn_addImageMemory.setImageBitmap(bm_src);
                        btn_ok = (Button) dialog_add.findViewById(R.id.btn_ok);
                        btn_dismiss = (Button) dialog_add.findViewById(R.id.btn_dis);
                        et_name = (EditText) dialog_add.findViewById(R.id.et_title_memory);
                        et_name.setText(cursor.getString(1));
                        et_date = (EditText) dialog_add.findViewById(R.id.et_date_memory);
                        et_date.setText(cursor.getString(2));
                        et_content = (EditText) dialog_add.findViewById(R.id.et_content_memory);
                        et_content.setText(cursor.getString(3));
                        btn_addImageMemory.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent it_callFragment = new Intent(Intent.ACTION_PICK);
                                it_callFragment.setType("image/*");
                                startActivityForResult(it_callFragment, REQUEST_FROM_GALLERY);
                            }
                        });
                        et_date.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                calendar = Calendar.getInstance();
                                int day = calendar.get(Calendar.DATE);
                                int month = calendar.get(Calendar.MONTH);
                                int year = calendar.get(Calendar.YEAR);
                                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                        calendar.set(i, i1, i2);
                                        et_date.setText(simpleDateFormat.format(calendar.getTime()));
                                    }
                                }, year, month, day);
                                datePickerDialog.show();
                            }
                        });
                        btn_dismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog_add.dismiss();
                            }
                        });
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String name = et_name.getText().toString().trim();
                                String date = et_date.getText().toString();
                                String content = et_content.getText().toString();
                                BitmapDrawable bitmapDrawable = (BitmapDrawable) btn_addImageMemory.getDrawable();
                                Bitmap bitmap = bitmapDrawable.getBitmap();
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] image = byteArrayOutputStream.toByteArray();
                                MainActivity.dataBase.ReplaceMemory(name, date, content, image, id);
                                dialog_add.dismiss();
                                UpdateFromDataBase();
                            }
                        });
                        dialog_add.show();
                    }
                });
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.dataBase.DeleteMemory(id);
                        UpdateFromDataBase();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void Init() {
        listView = (ListView) mView.findViewById(R.id.lv_memory);
        list_memory = new ArrayList<>();
        UpdateFromDataBase();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        btn_addMemory = (ImageButton) mView.findViewById(R.id.btn_add_memory);
    }

    public void UpdateFromDataBase() {
        list_memory.clear();
        Cursor cursor = MainActivity.dataBase.getData("SELECT * FROM " + MainActivity.MEMORY_TABLE);
        while (cursor.moveToNext()) {
            Memory memory = new Memory();
            memory.setName(cursor.getString(1));
            memory.setDate(cursor.getString(2));
            memory.setContent(cursor.getString(3));
            memory.setImage(cursor.getBlob(4));
            list_memory.add(memory);
        }
        memoryAdapter = new MemoryAdapter(R.layout.layout_memory_line, context, list_memory);
        listView.setAdapter(memoryAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == MainActivity.RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_FROM_GALLERY:
                    Uri uri = data.getData();
                    try {
                        InputStream inputStream = context.getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        if (BitmapCompat.getAllocationByteCount(bitmap) <= MainApplication.MAX_OF_BYTES) {
                            btn_addImageMemory.setImageBitmap(bitmap);
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

