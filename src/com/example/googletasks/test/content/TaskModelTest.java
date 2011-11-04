package com.example.googletasks.test.content;

import junit.framework.TestCase;
import android.database.MatrixCursor;

import com.example.googletasks.content.TaskModel;

public class TaskModelTest extends TestCase {
	public void testParse() {
		MatrixCursor cursor = new MatrixCursor(
				new String[] {
						TaskModel.COLUMN__ID
						, TaskModel.COLUMN_DONE
						, TaskModel.COLUMN_DUE_DATE
						, TaskModel.COLUMN_NAME
						, TaskModel.COLUMN_NOTES
				}
		);

		long _id = 1;
		int done = 1;
		String dueDate = "2011-11-25";
		String name = "der Test";
		String notes = "testen testen testen";

		cursor.addRow(new Object[] {_id, done, dueDate, name, notes});
		cursor.moveToFirst();

		TaskModel mdl = TaskModel.parse(cursor);

		assertEquals(_id, mdl.getId());
		assertTrue(mdl.isDone());
		assertEquals(dueDate, mdl.getDueDate());
		assertEquals(name, mdl.getName());
		assertEquals(notes, mdl.getNotes());
	}
}
