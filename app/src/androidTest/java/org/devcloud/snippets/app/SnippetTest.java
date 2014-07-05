package org.devcloud.snippets.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SnippetTest extends ApplicationTestCase<Application> {
  final static String TEST_USER = "fake@fake.com";
  Context context;

  public SnippetTest(Class<Application> applicationClass) {
    super(applicationClass);
  }

  public void setUp() throws Exception {
    super.setUp();
    context = getSystemContext();
  }

  public void testGetArrayListForAll() throws Exception {
    ArrayList<Snippet> snippets = Snippet.getArrayListForAll(context);
    assertEquals(new ArrayList<Snippet>(), snippets);
  }

  public void testFindByUUID() throws Exception {

  }

  public void testGetJsonArrayForAll() throws Exception {
    Snippet s = new Snippet("test json1", TEST_USER);
    s.save(context);
    s = new Snippet("test json2", TEST_USER);
    s.save(context);
    s = new Snippet("test json3", TEST_USER);
    s.save(context);

    ArrayList<Snippet> parsed1 = Snippet.parseJson(Snippet.getJsonArrayForAll(context));
    ArrayList<Snippet> parsed2 = Snippet.parseJson(Snippet.getJsonArrayForAll(context));

    assertEquals(parsed1, parsed2);
  }

  public void testGetUuid() throws Exception {
    Snippet s = new Snippet("test getUUID", TEST_USER);
    String gen_uuid = s.getUuid();
    s.save(context);

    assertEquals(gen_uuid, s.getUuid());

    ArrayList<Snippet> snippets = Snippet.getArrayListForAll(context);

    assertEquals(1, snippets.size());
    assertEquals(gen_uuid, snippets.get(0).getUuid());
  }

  public void testSave() throws Exception {
    DatabaseHelper mDbHelper = new DatabaseHelper(context);
    SQLiteDatabase db = mDbHelper.getReadableDatabase();
    Snippet s = new Snippet("blah blah blah.", TEST_USER);
    long id = s.save(context);
    s.setId(id);
    Snippet get = Snippet.findByID(id, context);

    assertTrue(id > 0);
    assertEquals(s, get);
  }
}
