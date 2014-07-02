package org.devcloud.snippets.app;

import android.test.AndroidTestCase;

import java.util.ArrayList;

public class SnippetTest extends AndroidTestCase {

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

  }
}