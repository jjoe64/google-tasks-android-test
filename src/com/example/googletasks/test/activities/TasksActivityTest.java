package com.example.googletasks.test.activities;

import org.easymock.EasyMock;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.MenuItem;

import com.example.googletasks.R;
import com.example.googletasks.activities.EditTaskActivity;
import com.example.googletasks.activities.TasksActivity;

public class TasksActivityTest extends ActivityUnitTestCase<TasksActivity> {
	public TasksActivityTest() {
		super(TasksActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// start tasks activity
		Intent tasksIntent = new Intent(getInstrumentation().getTargetContext(), TasksActivity.class);
		startActivity(tasksIntent, null, null);
	}

	public void testOnMenuItemSelected() {
		// neuen Task erstellen
		MenuItem item = EasyMock.createMock(MenuItem.class);
		EasyMock.expect(item.getItemId()).andReturn(R.id.menuitem_new);
		EasyMock.replay(item); // mock aktivieren

		// menu item
		getActivity().onMenuItemSelected(0, item);

		// edit task activity muss geöffnet werden
		Intent startedIntent = getStartedActivityIntent();
		assertNotNull(startedIntent);
		assertEquals(EditTaskActivity.class.getName(), startedIntent.getComponent().getClassName());
	}
}
