package com.genn.info.restaurant.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.genn.info.restaurant.Adapter.DiscountlistAdapter;
import com.genn.info.restaurant.Model.Discountviewsearch;
import com.genn.info.restaurant.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Discount extends Fragment {
    View view;

    protected TextView selectColoursButton;

    protected CharSequence[] colours = { "ALL", "FAVORTE", "DRINK", "LUNCH", "DINNER", "ICE CREAM" };

    protected ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();
    ListView list;
    DiscountlistAdapter adapter;
    EditText editsearch;
    String[] rank;
    String[] country;
    String[] quantity;
    String[] population;
    int[] listviewImage;
    CircleImageView image;
    ArrayList<Discountviewsearch> arraylist = new ArrayList<Discountviewsearch>();

    String[] Company = {"ALL","FAVORTE","JUICE","LUNCH","DINNER","ICE CREAM"};
    String[] variant = {"ALL","FAVORTE","JUICE","LUNCH","DINNER","ICE CREAM"};
    int[] tax = {10,20,30,40,50,60};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_discount, container, false);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.discountaddlist, null);
                final EditText etUsername = alertLayout.findViewById(R.id.et_itemname);
                final EditText etEmail = alertLayout.findViewById(R.id.et_price);


                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user = etUsername.getText().toString();
                        String pass = etEmail.getText().toString();
                        Toast.makeText(getActivity(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

            }
        });


        // Generate sample data
        rank = new String[] { "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category" };

        country = new String[] { "Item", "Item", "Item",
                "Item", "Item", "Item", "Item", "Item",
                "Item", "Item" };

        population = new String[] { "\u20B9 1,354", "\u20B9 354",
                "\u20B9 315", "\u20B9 237", "\u20B9 193", "\u20B9 182",
                "\u20B9 170", "\u20B9 152", "\u20B9 143", "\u20B9 127" };
        quantity = new String[] { "20%", "20%",
                "20%", "40%", "20%", "20%",
                "30%", "20%", "21%", "2%" };
        listviewImage = new int[]{
                R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
        };
        // Locate the ListView in listview_main.xml
        list = view.findViewById(R.id.listview);


        editsearch=view.findViewById(R.id.search);


        for (int i = 0; i < rank.length; i++)
        {
            Discountviewsearch wp = new Discountviewsearch(country[i], quantity[i]);
            // Binds all strings into an array
            arraylist.add(wp);
        }

        // Pass results to ListsearchViewAdapter Class
        adapter = new DiscountlistAdapter(getActivity(), arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);



        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

/*
        selectColoursButton =view.findViewById(R.id.select_colours);

        selectColoursButton.setOnClickListener(this);*/
        return view;
    }

    /*    @Override

        public void onClick(View view) {

            switch(view.getId()) {

                case R.id.select_colours:

                    // TODO: Show the colours dialog

                    showSelectColoursDialog();

                    break;

                default:

                    break;

            }

        }*/
   /* protected void showSelectColoursDialog() {

        boolean[] checkedColours = new boolean[colours.length];

        int count = colours.length;

        for(int i = 0; i < count; i++)

            checkedColours[i] = selectedColours.contains(colours[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked)

                    selectedColours.add(colours[which]);

                else

                    selectedColours.remove(colours[which]);

                onChangeSelectedColours();

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Category");

        builder.setMultiChoiceItems(colours, checkedColours, coloursDialogListener);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("CLEAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedColours.clear();
                selectColoursButton.setText("");
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();




        dialog.show();

    }

    protected void onChangeSelectedColours() {

        StringBuilder stringBuilder = new StringBuilder();

        for(CharSequence colour : selectedColours)

            stringBuilder.append(colour + ",");

        selectColoursButton.setText(stringBuilder.toString());

    }
*/
    private void selectImage() {
        final CharSequence[] options = { "Choose from Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    image.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                image.setImageBitmap(thumbnail);
            }
        }
    }
}
