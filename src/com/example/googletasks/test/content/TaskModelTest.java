package com.example.googletasks.test.content;

import java.util.Calendar;

import android.database.MatrixCursor;
import android.test.AndroidTestCase;

import com.example.googletasks.content.TaskModel;

public class TaskModelTest extends AndroidTestCase {
	public void testIsOverdue() {
		MatrixCursor cursor = new MatrixCursor(
				new String[] {
						TaskModel.COLUMN__ID
						, TaskModel.COLUMN_DONE
						, TaskModel.COLUMN_DUE_DATE
						, TaskModel.COLUMN_NAME
						, TaskModel.COLUMN_NOTES
				}
		);
		TaskModel mdl;
		long _id = 1;
		int done = 1;
		String name = "der Test";
		String notes = "testen testen testen";
		String dueDate;

		// ohne due date
		dueDate = null;
		cursor.addRow(new Object[] {_id, done, dueDate, name, notes});
		cursor.moveToLast();
		mdl = TaskModel.parse(cursor);
		assertFalse(mdl.isOverdue());

		// mit due date in der zukunft
		Calendar now = Calendar.getInstance();
		// heute +1 tag
		now.add(Calendar.HOUR_OF_DAY, 24);
		dueDate = now.get(Calendar.YEAR)+"-"
			+ (now.get(Calendar.MONTH)<9?"0":"") + (now.get(Calendar.MONTH)+1)
			+ "-" + (now.get(Calendar.DAY_OF_MONTH)<10?"0":"") + now.get(Calendar.DAY_OF_MONTH);
		cursor.addRow(new Object[] {_id, done, dueDate, name, notes});
		cursor.moveToLast();
		mdl = TaskModel.parse(cursor);
		assertFalse(mdl.isOverdue());

		// mit due date in der vergangenheit
		dueDate = "2011-01-01";
		cursor.addRow(new Object[] {_id, done, dueDate, name, notes});
		cursor.moveToLast();
		mdl = TaskModel.parse(cursor);
		assertTrue(mdl.isOverdue());
	}

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
