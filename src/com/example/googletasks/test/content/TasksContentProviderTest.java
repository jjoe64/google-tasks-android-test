package com.example.googletasks.test.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

import com.example.googletasks.content.TaskModel;
import com.example.googletasks.content.TasksContentProvider;

public class TasksContentProviderTest extends ProviderTestCase2<TasksContentProvider> {
	private static final String TEST_NAME = "der Test";
	private static final String TEST_NOTES = "bla blub";
	private static final String TEST_DUE_DATE = "2011-11-30";

	public TasksContentProviderTest() {
		super(TasksContentProvider.class, "com.example.googletasks.contentprovider");
	}

	public TaskModel _insert() {
		int done = 1;

		ContentValues values = new ContentValues(4);
		values.put(TaskModel.COLUMN_DONE, done);
		values.put(TaskModel.COLUMN_NAME, TEST_NAME);
		values.put(TaskModel.COLUMN_NOTES, TEST_NOTES);
		values.put(TaskModel.COLUMN_DUE_DATE, TEST_DUE_DATE);

		Uri uri = getMockContentResolver().insert(TasksContentProvider.CONTENT_URI, values);
		Cursor c = getMockContentResolver().query(uri, null, null, null, null);
		c.moveToFirst();
		TaskModel mdl = TaskModel.parse(c);
		c.close();

		return mdl;
	}

	public void testDelete() {
		TaskModel mdl = _insert();

		// anzahl vorher
		Cursor cursor = getMockContentResolver().query(
				TasksContentProvider.CONTENT_URI
				, null, null, null, null
		);
		int nVorher = cursor.getCount();
		cursor.close();

		// löschen
		getMockContentResolver().delete(Uri.withAppendedPath(TasksContentProvider.CONTENT_URI, String.valueOf(mdl.getId())), null, null);

		// anzahl danach
		cursor = getMockContentResolver().query(
				TasksContentProvider.CONTENT_URI
				, null, null, null, null
		);
		assertEquals(nVorher-1, cursor.getCount());
		cursor.close();
	}

	public void testInsert() {
		// anzahl vorher
		Cursor cursor = getMockContentResolver().query(
				TasksContentProvider.CONTENT_URI
				, null, null, null, null
		);
		int nVorher = cursor.getCount();
		cursor.close();

		TaskModel mdl = _insert();

		// anzahl nachher / differenz
		cursor = getMockContentResolver().query(
				TasksContentProvider.CONTENT_URI
				, null, null, null, null
		);
		assertEquals(1, cursor.getCount()-nVorher);
		cursor.close();

		// daten prüfen
		assertTrue(mdl.isDone());
		assertEquals(TEST_DUE_DATE, mdl.getDueDate());
		assertEquals(TEST_NAME, mdl.getName());
		assertEquals(TEST_NOTES, mdl.getNotes());
	}

	public void testUpdate() {
		TaskModel mdl = _insert();
		long id = mdl.getId();

		Uri uri = Uri.withAppendedPath(TasksContentProvider.CONTENT_URI, String.valueOf(id));
		ContentValues values = new ContentValues(2);
		values.put(TaskModel.COLUMN_NAME, "neuer Name");
		values.put(TaskModel.COLUMN_NOTES, "neue Notizen");

		getMockContentResolver().update(uri, values, null, null);

		// requery
		Cursor c = getMockContentResolver().query(uri, null, null, null, null);
		c.moveToFirst();
		mdl = TaskModel.parse(c);
		c.close();

		// assert
		assertEquals("neuer Name", mdl.getName());
		assertEquals("neue Notizen", mdl.getNotes());
	}
}
