package org.mobop.flatseeker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mobop.flatseeker.model.Flat;


public class NoteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        final Intent intent = getIntent();
        final Flat flat = intent.getParcelableExtra(SearchExpandableListAdapter.EXTRA_MESSAGE);

        final EditText editText = (EditText)findViewById(R.id.noteNoteTbx);
        editText.setText(flat.getNote());

        Button saveBtn = (Button)findViewById(R.id.noteSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flat.setNote(editText.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("flat",flat);
                setResult(SearchExpandableListAdapter.TAG_NOTE,intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
