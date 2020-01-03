package com.LeonTimmerman.notepad.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.LeonTimmerman.notepad.R
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class EditActivity : AppCompatActivity() {

    private lateinit var editViewModel: EditActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        supportActionBar?.title = "Edit Notepad"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        fabSave.setOnClickListener {
            editViewModel.note.value?.apply {
                title = txtTitle.text.toString()
                lastUpdated = Date()
                text = txtNotes.text.toString()
            }

            editViewModel.updateNote()
        }
    }

    private fun initViewModel() {
        editViewModel = ViewModelProviders.of(this).get(EditActivityViewModel::class.java)
        editViewModel.note.value = intent.extras?.getParcelable(EXTRA_NOTE)!!

        editViewModel.note.observe(this, Observer { note ->
            if (note != null) {
                txtTitle.setText(note.title)
                txtNotes.setText(note.text)
            }
        })

        editViewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        editViewModel.success.observe(this, Observer { success ->
            if (success) finish()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_NOTE = "EXTRA_NOTE"
    }
}
