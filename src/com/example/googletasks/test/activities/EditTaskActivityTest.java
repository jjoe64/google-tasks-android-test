package com.example.googletasks.test.activities;

import android.content.Intent;
import android.net.Uri;
import android.test.ActivityUnitTestCase;
import android.test.RenamingDelegatingContext;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.googletasks.R;
import com.example.googletasks.activities.EditTaskActivity;
import com.example.googletasks.content.TaskModel;
import com.example.googletasks.content.TasksContentProvider;
import com.example.googletasks.test.content.TasksContentProviderTest;

public class EditTaskActivityTest extends ActivityUnitTestCase<EditTaskActivity> {
	public EditTaskActivityTest() {
		super(EditTaskActivity.class);
	}

	public void testDueDateCheckedChange() {
		// start edit task activity
		Intent tasksIntent = new Intent(getInstrumentation().getTargetContext(), EditTaskActivity.class);
		startActivity(tasksIntent, null, null);

		// referenzen
		CheckBox cbDueDate = (CheckBox) getActivity().findViewById(R.id.edit_task_cb_due_date);
		DatePicker dueDate = (DatePicker) getActivity().findViewById(R.id.edit_task_due_date);

		// am anfang versteckt
		assertEquals(View.GONE, dueDate.getVisibility());

		// cbDueDate check
		cbDueDate.performClick();

		// sichtbar
		assertEquals(View.VISIBLE, dueDate.getVisibility());
	}

	public void testOnCreateEdit() {
		// Mock Content Provider
		String filenamePrefix = "test.";
		RenamingDelegatingContext targetContextWrapper = new RenamingDelegatingContext(
				getInstrumentation().getTargetContext(), // The context that most methods are delegated to
				getInstrumentation().getTargetContext(), // The context that file methods are delegated to
				filenamePrefix);

		setActivityContext(targetContextWrapper);

		// TaskModel einfügen
		TaskModel mdl = TasksContentProviderTest._insert(targetContextWrapper.getContentResolver());

		// Intent
		Intent editIntent = new Intent(getInstrumentation().getTargetContext(), EditTaskActivity.class);
		Uri url = Uri.withAppendedPath(TasksContentProvider.CONTENT_URI, "/"+mdl.getId());
		editIntent.setData(url);

		// start activity
		startActivity(editIntent, null, null);

		// input felder validieren
		EditText name = (EditText) getActivity().findViewById(R.id.edit_task_name);
		assertEquals(mdl.getName(), name.getText().toString());
	}
}
