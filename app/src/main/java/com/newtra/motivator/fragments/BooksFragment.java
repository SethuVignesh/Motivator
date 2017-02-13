package com.newtra.motivator.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;

import com.newtra.motivator.beans.Book;
import com.newtra.motivator.MyIntentServiceDownload;
import com.newtra.motivator.R;
import com.newtra.motivator.Utility;
import com.newtra.motivator.adapters.BookListAdapter;

import java.io.File;
import java.util.ArrayList;


public class BooksFragment extends Fragment {
    ListView booksListView;
    private ProgressDialog mProgressDialog;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;


    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_books, container, false);

        populateListView(rootView);
        return rootView;
    }

    ArrayList<Book> mStoreListNames;

    private void populateListView(View rootView) {
        mStoreListNames = new ArrayList<Book>();
        mStoreListNames.add(new Book("create your own future", "", R.drawable.at_tamil, "https://github.com/SethuVignesh/Motivator/raw/master/books/1_BT-Create%20Your%20Own%20Future.pdf"));
        mStoreListNames.add(new Book("Brian Tracy - Million dollar habits", "", R.drawable.at_telugu, "https://github.com/SethuVignesh/Motivator/raw/master/books/2009893770241.pdf"));
        mStoreListNames.add(new Book("Brian Tracy - Focal-Point Book.pdf", "", R.drawable.at_kannada, "https://github.com/SethuVignesh/Motivator/raw/master/books/Brian%20Tracy%20-%20Focal-Point%20Book.pdf"));
        mStoreListNames.add(new Book("Learned-Optimism-Book-Summary.pdf", "", R.drawable.at_hindi, "https://github.com/SethuVignesh/Motivator/raw/master/books/Learned-Optimism-Book-Summary.pdf"));
        mStoreListNames.add(new Book("Psychology Of Selling - The Art Of Closing Sales - Brian Tracy.pdf", "", R.drawable.at_malay, "https://github.com/SethuVignesh/Motivator/raw/master/books/Psychology%20Of%20Selling%20-%20The%20Art%20Of%20Closing%20Sales%20-%20Brian%20Tracy.pdf"));
        mStoreListNames.add(new Book("Time Power By Brian Tracy", "", R.drawable.mmwv, "https://github.com/SethuVignesh/Motivator/raw/master/books/Time%20Power%20By%20Brian%20Tracy.pdf"));
        mStoreListNames.add(new Book("change your thinking change your life", "", R.drawable.rrr, "https://github.com/SethuVignesh/Motivator/raw/master/books/change-your-thinking-change-your-life-how-to-unlock-your-full-potential-for-success-and-achievement-0471448583-wiley_1406047252.pdf"));
        mStoreListNames.add(new Book("goals by Brian", "", R.drawable.wp, "https://github.com/SethuVignesh/Motivator/raw/master/books/goals.pdf"));
        mStoreListNames.add(new Book("Time Management by Brian Tracy", "", R.drawable.wp, "https://github.com/SethuVignesh/Motivator/raw/master/books/time_mgmnt_mini.pdf"));


        booksListView = (ListView) rootView.findViewById(R.id.listViewBooks);
        BookListAdapter adapter = new BookListAdapter(getActivity(), mStoreListNames);

        // Binds the Adapter to the ListView
        booksListView.setAdapter(adapter);
        /*booksListView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // GET the clicked book
                //check the folder if book available
                // if available open the book
                //else downlaod and save in the folder

            }
        });*/

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startDownload(mStoreListNames.get(position).getUrl(), mStoreListNames.get(position).getTitle());
            }
        });

    }

    private void startDownload(String url, String title) {
//        String url = "http://farm1.static.flickr.com/114/298125983_0e4bf66782_b.jpg";
//        new DownloadFileAsync().execute(url, title);
        File filepath = Environment.getExternalStorageDirectory();

        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath()
                + "/Save Image Tutorial/");
        dir.mkdirs();

        // Create a name for the saved image
        File file = new File(dir, title);
        if (Utility.checkExists(dir.toString(), title)) {
            String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(".PDF");

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), mime);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {
            MyIntentServiceDownload.startActionFoo(getActivity(), url, title);
        }
    }


}
