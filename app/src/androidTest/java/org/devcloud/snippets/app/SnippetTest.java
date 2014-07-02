package org.devcloud.snippets.app;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.ArrayList;

public class SnippetTest extends AndroidTestCase {
  final static String TEST_USER = "fake@fake.com";

  public void setUp() throws Exception {
    super.setUp();
  }

  public void testGetArrayListForAll() throws Exception {
    ArrayList<Snippet> snippets = Snippet.getArrayListForAll(this.getContext());
    assertEquals(new ArrayList<Snippet>(), snippets);
  }

  public void testFindByUUID() throws Exception {

  }

  public void testGetJsonArrayForAll() throws Exception {

  }

  public void testGetUuid() throws Exception {

  }

  public void testSave() throws Exception {
    DatabaseHelper mDbHelper = new DatabaseHelper(getContext());
    SQLiteDatabase db = mDbHelper.getReadableDatabase();
    Snippet s = new Snippet("blah blah blah.", TEST_USER);
    long id = s.save(getContext());
    s.setId(id);
    Snippet get = Snippet.findByID(id, getContext());

    assertTrue(id > 0);
    assertEquals(s, get);
  }
}
