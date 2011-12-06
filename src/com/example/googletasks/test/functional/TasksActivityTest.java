package com.example.googletasks.test.functional;

import android.test.ActivityInstrumentationTestCase2;

import com.example.googletasks.activities.EditTaskActivity;
import com.example.googletasks.activities.TasksActivity;
import com.jayway.android.robotium.solo.Solo;

public class TasksActivityTest extends ActivityInstrumentationTestCase2<TasksActivity> {
	private Solo solo;

	public TasksActivityTest() {
		super("com.example.googletasks", TasksActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testNewTask() {
		solo.clickOnMenuItem("New");
		solo.assertCurrentActivity("Expected EditTaskActivity", EditTaskActivity.class);
		assertTrue(solo.searchButton("Save"));
	}
}
