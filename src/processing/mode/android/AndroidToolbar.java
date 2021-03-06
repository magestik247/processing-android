/*

  Part of the Processing project - http://processing.org

  Copyright (c) 2011-12 Ben Fry and Casey Reas

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License version 2
  as published by the Free Software Foundation.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software Foundation,
  Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package processing.mode.android;

import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import processing.app.Base;
import processing.app.Editor;
import processing.app.EditorToolbar;


@SuppressWarnings("serial")
public class AndroidToolbar extends EditorToolbar {
  static protected final int RUN    = 0;
  static protected final int STOP   = 1;

  static protected final int NEW    = 2;
  static protected final int OPEN   = 3;
  static protected final int SAVE   = 4;
  static protected final int EXPORT = 5;


  public AndroidToolbar(Editor editor, Base base) {
    super(editor, base);
  }


  public void init() {
    Image[][] images = loadImages();
    for (int i = 0; i < 6; i++) {
      addButton(getTitle(i, false), getTitle(i, true), images[i], i == NEW);
    }
  }


  static public String getTitle(int index, boolean shift) {
    switch (index) {
    case RUN:    return !shift ? "Run on Device" : "Run in Emulator";
    case STOP:   return "Stop";
    case NEW:    return "New";
    case OPEN:   return "Open";
    case SAVE:   return "Save";
    case EXPORT: return !shift ? "Export Signed Package" : "Export Android Project";
    }
    return null;
  }


  public void handlePressed(MouseEvent e, int sel) {
    boolean shift = e.isShiftDown();
    AndroidEditor aeditor = (AndroidEditor) editor;

    switch (sel) {
    case RUN:
      if (!shift) {
        aeditor.handleRunDevice();
      } else {
        aeditor.handleRunEmulator();
      }
      break;

    case STOP:
      aeditor.handleStop();
      break;

    case OPEN:
      // TODO I think we need a longer chain of accessors here.
      JPopupMenu popup = editor.getMode().getToolbarMenu().getPopupMenu();
      popup.show(this, e.getX(), e.getY());
      break;

    case NEW:
//      if (shift) {
      base.handleNew();
//      } else {
//        base.handleNewReplace();
//      }
      break;

    case SAVE:
      aeditor.handleSave(false);
      break;

    case EXPORT:
      if (!shift) {
        aeditor.handleExportPackage();
      } else {
        aeditor.handleExportProject();
      }
      break;
    }
  }
}