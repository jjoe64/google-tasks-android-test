package com.example.googletasks.test.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.ProviderTestCase2;

import com.example.googletasks.content.TaskModel;
import com.example.googletasks.content.TasksContentProvider;

public class TasksContentProviderTest extends ProviderTestCase2<TasksContentProvider> {
	public TasksContentProviderTest() {
		super(TasksContentProvider.class, "com.example.googletasks.contentprovider");
	}

	public void testInsert() {
		// anzahl vorher
		Cursor cursor = getMockContentResolver().query(
				TasksContentProvider.CONTENT_URI
				, null, null, null, null
		);
		int nVorher = cursor.getCount();

		int done = 1;
		String name ="der Test";
		String notes = "bla blub";
		String dueDate = "2011-11-30";

		ContentValues values = new ContentValues(4);
		values.put(TaskModel.COLUMN_DONE, done);
		values.put(TaskModel.COLUMN_NAME, name);
		values.put(TaskModel.COLUMN_NOTES, notes);
		values.put(TaskModel.COLUMN_DUE_DATE, dueDate);

		getMockContentResolver().insert(TasksContentProvider.CONTENT_URI, values);

		// anzahl nachher / differenz
		cursor = getMockContentResolver().query(
				TasksContentProvider.CONTENT_URI
				, null, null, null, null
		);
		assertEquals(1, cursor.getCount()-nVorher);

		// notified?
	}
}
